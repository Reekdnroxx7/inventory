/*
 *  Licensed to the Apache Software Foundation (ASF) under one
 *  or more contributor license agreements.  See the NOTICE file
 *  distributed with this work for additional information
 *  regarding copyright ownership.  The ASF licenses this file
 *  to you under the Apache License, Version 2.0 (the
 *  "License"); you may not use this file except in compliance
 *  with the License.  You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing,
 *  software distributed under the License is distributed on an
 *  "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY
 *  KIND, either express or implied.  See the License for the
 *  specific language governing permissions and limitations
 *  under the License.
 *
 */
package com.x404.admin.core.util;

import org.apache.commons.collections.keyvalue.DefaultMapEntry;

import java.util.Collection;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Collectors;


/**
 * A map with expiration.
 *
 * @author The Apache Directory Project (mina-dev@directory.apache.org)
 */
public class ExpiringMap<K, V> implements Map<K, V> {
    public static final int DEFAULT_TIME_TO_LIVE = 60;

    public static final int DEFAULT_EXPIRATION_INTERVAL = 1;

    private static volatile int expirerCount = 1;

    private final ConcurrentHashMap<K, ExpiringObject> delegate;

    private final CopyOnWriteArrayList expirationListeners;

    private final Expirer expirer;

    public ExpiringMap() {
        this(DEFAULT_TIME_TO_LIVE, DEFAULT_EXPIRATION_INTERVAL);
    }

    public ExpiringMap(int timeToLive) {
        this(timeToLive, DEFAULT_EXPIRATION_INTERVAL);
    }

    public ExpiringMap(int timeToLive, int expirationInterval) {
        this(new ConcurrentHashMap(), new CopyOnWriteArrayList(), timeToLive,
                expirationInterval);
    }

    private ExpiringMap(ConcurrentHashMap delegate,
                        CopyOnWriteArrayList expirationListeners, int timeToLive,
                        int expirationInterval) {
        this.delegate = delegate;
        this.expirationListeners = expirationListeners;

        this.expirer = new Expirer();
        expirer.setTimeToLive(timeToLive);
        expirer.setExpirationInterval(expirationInterval);
    }

    @Override
    public V put(K key, V value) {
        ExpiringObject put = delegate.put(key, new ExpiringObject(key, value, System
                .currentTimeMillis()));
        if (put != null) {
            return put.getValue();
        }
        return null;
    }

    @Override
    public V get(Object key) {
        ExpiringObject object = delegate.get(key);

        if (object != null) {
            ExpiringObject expObject = (ExpiringObject) object;
            expObject.setLastAccessTime(System.currentTimeMillis());

            return expObject.getValue();
        }

        return null;
    }

    @Override
    public V remove(Object key) {
        return delegate.remove(key).getValue();
    }

    @Override
    public boolean containsKey(Object key) {
        return delegate.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return delegate.containsValue(value);
    }

    @Override
    public int size() {
        return delegate.size();
    }

    @Override
    public boolean isEmpty() {
        return delegate.isEmpty();
    }

    @Override
    public void clear() {
        delegate.clear();
    }

    @Override
    public int hashCode() {
        return delegate.hashCode();
    }

    @Override
    public Set keySet() {
        return delegate.keySet();
    }

    @Override
    public boolean equals(Object obj) {
        return delegate.equals(obj);
    }

    @Override
    public void putAll(Map<? extends K, ? extends V> inMap) {
        synchronized (inMap) {
            Iterator<? extends K> inMapKeysIt = inMap.keySet().iterator();

            while (inMapKeysIt.hasNext()) {
                K key = inMapKeysIt.next();
                V value = inMap.get(key);

                delegate.put(key, new ExpiringObject(key, value, System.currentTimeMillis()));
            }
        }
    }

    public Collection<V> values() {
        return delegate.values().stream().map(value -> value.getValue()).collect(Collectors.toList());
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {

        return delegate.entrySet().stream().map(entry -> (Map.Entry<K, V>) new DefaultMapEntry(entry.getKey(), entry.getValue().getValue())).collect(Collectors.toSet());
    }

    public void addExpirationListener(ExpirationListener listener) {
        expirationListeners.add(listener);
    }

    public void removeExpirationListener(ExpirationListener listener) {
        expirationListeners.remove(listener);
    }

    public Expirer getExpirer() {
        return expirer;
    }

    public int getExpirationInterval() {
        return expirer.getExpirationInterval();
    }

    public int getTimeToLive() {
        return expirer.getTimeToLive();
    }

    public void setExpirationInterval(int expirationInterval) {
        expirer.setExpirationInterval(expirationInterval);
    }

    public void setTimeToLive(int timeToLive) {
        expirer.setTimeToLive(timeToLive);
    }

    private class ExpiringObject {
        private K key;

        private V value;

        private long lastAccessTime;

        private ReadWriteLock lastAccessTimeLock = new ReentrantReadWriteLock();

        public ExpiringObject(K key, V value, long lastAccessTime) {
            if (value == null) {
                throw new IllegalArgumentException(
                        "An expiring object cannot be null.");
            }

            this.key = key;
            this.value = value;
            this.lastAccessTime = lastAccessTime;
        }

        public long getLastAccessTime() {
            lastAccessTimeLock.readLock().lock();

            try {
                return lastAccessTime;
            } finally {
                lastAccessTimeLock.readLock().unlock();
            }
        }

        public void setLastAccessTime(long lastAccessTime) {
            lastAccessTimeLock.writeLock().lock();

            try {
                this.lastAccessTime = lastAccessTime;
            } finally {
                lastAccessTimeLock.writeLock().unlock();
            }
        }

        public K getKey() {
            return key;
        }

        public V getValue() {
            return value;
        }

        public boolean equals(Object obj) {
            return value.equals(obj);
        }

        public int hashCode() {
            return value.hashCode();
        }
    }

    public class Expirer implements Runnable {
        private ReadWriteLock stateLock = new ReentrantReadWriteLock();

        private long timeToLiveMillis;

        private long expirationIntervalMillis;

        private boolean running = false;

        private final Thread expirerThread;

        public Expirer() {
            expirerThread = new Thread(this, "ExpiringMapExpirer-"
                    + (expirerCount++));
            expirerThread.setDaemon(true);
        }

        public void run() {
            while (running) {
                processExpires();

                try {
                    Thread.sleep(expirationIntervalMillis);
                } catch (InterruptedException e) {
                }
            }
        }

        private void processExpires() {
            long timeNow = System.currentTimeMillis();

            Iterator expiringObjectsIterator = delegate.values().iterator();

            while (expiringObjectsIterator.hasNext()) {
                ExpiringObject expObject = (ExpiringObject) expiringObjectsIterator
                        .next();

                if (timeToLiveMillis <= 0)
                    continue;

                long timeIdle = timeNow - expObject.getLastAccessTime();

                if (timeIdle >= timeToLiveMillis) {
                    delegate.remove(expObject.getKey());

                    Iterator listenerIterator = expirationListeners.iterator();

                    while (listenerIterator.hasNext()) {
                        ExpirationListener listener = (ExpirationListener) listenerIterator
                                .next();

                        listener.expired(expObject.getValue());
                    }
                }
            }
        }

        public void startExpiring() {
            stateLock.writeLock().lock();

            try {
                if (!running) {
                    running = true;
                    expirerThread.start();
                }
            } finally {
                stateLock.writeLock().unlock();
            }
        }

        public void startExpiringIfNotStarted() {
            stateLock.readLock().lock();
            try {
                if (running) {
                    return;
                }
            } finally {
                stateLock.readLock().unlock();
            }

            stateLock.writeLock().lock();
            try {
                if (!running) {
                    running = true;
                    expirerThread.start();
                }
            } finally {
                stateLock.writeLock().unlock();
            }
        }

        public void stopExpiring() {
            stateLock.writeLock().lock();

            try {
                if (running) {
                    running = false;
                    expirerThread.interrupt();
                }
            } finally {
                stateLock.writeLock().unlock();
            }
        }

        public boolean isRunning() {
            stateLock.readLock().lock();

            try {
                return running;
            } finally {
                stateLock.readLock().unlock();
            }
        }

        public int getTimeToLive() {
            stateLock.readLock().lock();

            try {
                return (int) timeToLiveMillis / 1000;
            } finally {
                stateLock.readLock().unlock();
            }
        }

        public void setTimeToLive(long timeToLive) {
            stateLock.writeLock().lock();

            try {
                this.timeToLiveMillis = timeToLive * 1000;
            } finally {
                stateLock.writeLock().unlock();
            }
        }

        public int getExpirationInterval() {
            stateLock.readLock().lock();

            try {
                return (int) expirationIntervalMillis / 1000;
            } finally {
                stateLock.readLock().unlock();
            }
        }

        public void setExpirationInterval(long expirationInterval) {
            stateLock.writeLock().lock();

            try {
                this.expirationIntervalMillis = expirationInterval * 1000;
            } finally {
                stateLock.writeLock().unlock();
            }
        }
    }

    /**
     * Created by chaox on 4/17/2017.
     */
    public interface ExpirationListener<V> {
        public void expired(V value);
    }
}
