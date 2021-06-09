package com.x404.module.dynconfig;


import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.util.*;


public class FileMonitor {
    private static final FileMonitor instance = new FileMonitor();
    private Timer timer;
    private Map<File, FileMonitorTask> timerEntries;
    private final static Log LOG = LogFactory.getLog(FileMonitor.class);

    private FileMonitor() {
        this.timerEntries = new HashMap<File, FileMonitorTask>();
        this.timer = new Timer("FileMonitor");
    }

    public static FileMonitor getInstance() {
        return instance;
    }

    /**
     * 对某个文件实行监听
     *
     * @param listener The file listener
     */
    public void addFileChangeListener(FileChangeListener listener,

                                      File file) {
        this.removeFileChangeListener(file);
        FileMonitorTask task = new FileMonitorTask(listener, file);
        this.timerEntries.put(file, task);
        this.timer.schedule(task, 2000, 2000);
    }

    public void removeFileChangeListener(File file) {
        FileMonitorTask task = this.timerEntries
                .remove(file);

        if (task != null) {
            task.cancel();
        }
    }

    private static class FileMonitorTask extends TimerTask {
        private FileChangeListener listener;
        private File monitoredFile;
        private WatchKey watchKey;

        public FileMonitorTask(FileChangeListener listener, File file) {
            this.listener = listener;

            this.monitoredFile = file;
            String p = monitoredFile.getParent();
            Path path = Paths.get(p);
            try {
                WatchService watcher = path.getFileSystem().newWatchService();
                watchKey = path.register(watcher, StandardWatchEventKinds.ENTRY_MODIFY);
            } catch (IOException ex) {
                LOG.error(ex, ex);
            }
        }

        @Override
        public void run() {
            try {
                List<WatchEvent<?>> events = watchKey.pollEvents();
                for (@SuppressWarnings("rawtypes") WatchEvent event : events) {
                    if (event.kind() == StandardWatchEventKinds.ENTRY_MODIFY) {
                        if (monitoredFile.getName().equals(event.context().toString())) {
                            LOG.info(event.context() + " changed");
                            listener.fileChanged(monitoredFile);
                            break;
                        }
                    }
                }
            } finally {
                watchKey.reset();
            }
        }
    }
}
