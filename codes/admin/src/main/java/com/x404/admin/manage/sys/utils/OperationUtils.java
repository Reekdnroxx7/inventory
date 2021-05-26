package com.x404.admin.manage.sys.utils;

import com.x404.admin.core.util.SpringContextHolder;
import com.x404.admin.manage.sys.entity.Operation;
import com.x404.admin.manage.sys.service.IOperationService;

import java.util.*;

/**
 * @author xiechao 使用本地HashMap作为缓存。不支持分布式。
 */
public class OperationUtils {
    private static OperationUtils instance = new OperationUtils();
    private IOperationService operationService;
    private Set<Operation> allOperations;
    private HashMap<String, List<Operation>> mMap = new HashMap<String, List<Operation>>();

    private OperationUtils() {
        this.operationService = SpringContextHolder
                .getBean(IOperationService.class);
    }

    public static OperationUtils getInstance() {
        return instance;
    }

    public synchronized void refresh() {
        this.allOperations = new HashSet<Operation>();
        mMap.clear();
        List<Operation> findAll = this.operationService.findAll();
        for (Operation p : findAll) {
            this.allOperations.add(p);

            List<Operation> list = mMap.get((p.getMenuId()));
            if (list == null) {
                list = new ArrayList<Operation>();
                mMap.put(p.getMenuId(), list);
            }
            list.add(p);
        }
    }

    public Set<Operation> getOperations() {
        if (allOperations == null) {
            refresh();
        }
        return allOperations;
    }

    public boolean contains(Operation p) {
        return this.getOperations().contains(p);
    }

    public List<Operation> getMenuOperation(String menuId) {
        return mMap.get(menuId);
    }

}
