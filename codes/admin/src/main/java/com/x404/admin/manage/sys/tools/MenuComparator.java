package com.x404.admin.manage.sys.tools;

import com.x404.admin.manage.sys.entity.Menu;

import java.io.Serializable;
import java.util.Comparator;

public class MenuComparator implements Comparator<Menu>, Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    public int compare(Menu o1, Menu o2) {
        if (o1 == null) {
//			System.out.println(o1);
        }
//		int l1 = o1.getLevel();
//		int l2 = o2.getLevel();
//		if(l1 != l2){
//			return l1 - l2;
//		}
//		else{
        return o1.getSort() - o2.getSort();
//		}
    }
}
