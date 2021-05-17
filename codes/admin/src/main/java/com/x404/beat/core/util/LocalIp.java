package com.x404.beat.core.util;

import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;

/**
 * Created by chaox on 8/4/2017.
 */
public class LocalIp
{
public static String localIp;
    public static String getLocalIp(){
        if(localIp != null ){
            return localIp;
        }
        String localip = null;// 本地IP，如果没有配置外网IP则返回它
        String netip = null;// 外网IP

        Enumeration<NetworkInterface> netInterfaces ;
        try {
            netInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            boolean found = false;// 是否找到外网IP
            while (netInterfaces.hasMoreElements() && !found) {
                NetworkInterface ni = netInterfaces.nextElement();
                Enumeration<InetAddress> address = ni.getInetAddresses();
                while (address.hasMoreElements()) {
                    ip = address.nextElement();
                    if (!ip.isSiteLocalAddress() &&ip instanceof Inet4Address && !ip.isLinkLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 外网IP
                        netip = ip.getHostAddress();
                        found = true;
                        break;
                    } else if (ip.isSiteLocalAddress() && ip instanceof Inet4Address && !ip.isLinkLocalAddress()
                            && !ip.isLoopbackAddress()
                            && ip.getHostAddress().indexOf(":") == -1) {// 内网IP
                        localip = ip.getHostAddress();
                    }
                }
            }

            if (netip != null && !"".equals(netip)) {
                localIp =  netip;
            } else {
                localIp =  localip;
            }
            return localIp;
        } catch (SocketException e) {
            e.printStackTrace();
        }
        return  null;
    }

    public static void main(String[] args) {
        System.out.println(getLocalIp());
    }
}
