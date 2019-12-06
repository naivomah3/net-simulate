/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mg.entraide.tools;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author RANAIVOSON
 */
public class IpCheckerTools {

    public static boolean isValidIP(String ipAddr, boolean isMask) {
        int i;
        Pattern pattern = Pattern.compile("^(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})\\.(\\d{1,3})$");
        Matcher match = pattern.matcher(ipAddr);
        if (match.find()) {
            String tabIp[] = ipAddr.split("\\.");
            int intTabIp[] = new int[tabIp.length];
            for (i = 0; i < intTabIp.length; i++) {
                intTabIp[i] = Integer.parseInt(tabIp[i]);
                if (intTabIp[i] < 0 || intTabIp[i] > 255) {
                    return false;
                } 
            }
            if (!isMask) {
                if (intTabIp[0] > 223) {
                    return false;
                }
            }
            if (intTabIp[0] == 0) {
                return false;
            }
            return true;
        }
        return false;
    }

    public static String getNetmask(String ip) {
        if (!isValidIP(ip, false)) {
            return null;
        }
        String tabIp[] = ip.split("\\.");
        int intIp = Integer.parseInt(tabIp[0]);
        if (intIp < 128) {
            return "255.0.0.0";
        } else if (intIp < 192) {
            return "255.255.0.0";
        } else if (intIp < 224) {
            return "255.255.255.0";
        } 
        return null;
    }

    public static String getNetwork(String ip, String mask) {
        int i;
        if (!isValidIP(ip, false)) {
            return null;
        }
        String tabIp[] = ip.split("\\."), tabMask[] = mask.split("\\.");
        int tabNet[] = new int[4];
        for (i = 0; i < tabIp.length; i++) {
            tabNet[i] = Integer.parseInt(tabIp[i]) & Integer.parseInt(tabMask[i]);
        }
        return tabNet[0] + "." + tabNet[1] + "." + tabNet[2] + "." + tabNet[3];
    }
    
//    public static boolean isValidConfig (String ipAdress, String netmask, String gateway) {
//        if (gateway.equalsIgnoreCase("")) {
//            return isValidIP(ipAdress) || isValidIP(netmask);
//        } else {
//            return isValidIP(ipAdress) || isValidIP(netmask) || isValidIP(gateway);
//        }
//    }
    
}
