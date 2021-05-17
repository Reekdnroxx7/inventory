package com.x404.beat.manage.sys.tools;

import java.util.Comparator;


public class ClientSort implements Comparator<UserInfo> {


    public int compare(UserInfo prev, UserInfo now) {
        return (int) (now.getLogindatetime().getTime() - prev.getLogindatetime().getTime());
    }

}
