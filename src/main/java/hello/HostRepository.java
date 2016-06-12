package hello;


import io.spring.guides.gs_producing_web_service.Host;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Mindaugas Vidmantas on 2016-06-06.
 * email: minvidm@gmail.com, minvidm@ktu.lt
 */

@Component
public class HostRepository {
    private static final List<Host> hosts = new ArrayList<Host>();
    public static List<Integer> portList = new ArrayList<Integer>();
    private static final Logger log = LoggerFactory.getLogger(HostRepository.class);


    @PostConstruct
    public void initData() {
        //Create Popular Port List
        portList.add(80);
        portList.add(443);
        portList.add(22);
        portList.add(21);
        portList.add(23);
        portList.add(8080);
    }


    /**
     * MOST POPULAR TCP ports:
     * http    80/tcp  0.484143    # World Wide Web HTTP
     * http    8080/tcp  0.484143    # World Wide Web HTTP
     * telnet  23/tcp  0.221265
     * https   443/tcp 0.208669    # secure http (SSL)
     * ftp 21/tcp  0.197667    # File Transfer [Control]
     * ssh 22/tcp  0.182286    # Secure Shell Login
     *
     * MOST POPULAR UDP ports:
     * ipp 631/udp 0.450281    # Internet Printing Protocol
     * snmp    161/udp 0.433467    # Simple Net Mgmt Proto
     * netbios-ns  137/udp 0.365163    # NETBIOS Name Service
     * ntp 123/udp 0.330879    # Network Time Protocol
     * netbios-dgm 138/udp 0.297830    # NETBIOS Datagram Service
     * ms-sql-m    1434/udp    0.293184    # Microsoft-SQL-Monitor
     * microsoft-ds    445/udp 0.253118
     * msrpc   135/udp 0.244452    # Microsoft RPC services
     * dhcps   67/udp  0.228010    # DHCP/Bootstrap Protocol Server
     * domain  53/udp  0.213496    # Domain Name Server
     * netbios-ssn 139/udp 0.193726    # NETBIOS Session Service
     * */

    /**
    * Get response time from remote IP or WebSite
    *
    **/
    public Host findHost(String urlName) {

        Host result = new Host();

        int timeOutMillis = 10000;

        if (!urlName.equals(null)) {
            for (int j = 0; j < portList.size(); j++) {
                long startTime = System.currentTimeMillis();
                if (isSocketReachable(urlName, portList.get(j), timeOutMillis)) {

                    long endTime = System.currentTimeMillis();
                    long totalTime = endTime - startTime;

                    log.info("Total time " + urlName + " port: " + portList.get(j) + " :  " + totalTime);

                    result.setTimeout(totalTime);
                    result.setPort(portList.get(j));
                    result.setName(urlName);
                    break;
                }
            }
        }
        else
            return null;

        return result;
    }

    /**
     *
     * isSocketReachable
     *
     * **/
    private static boolean isSocketReachable(String addr, int openPort, int timeOutMillis) {
        // Any Open port on other machine
        // openPort =  22 - ssh, 80 or 443 - webserver, 25 - mailserver etc.
        try {
            try (Socket soc = new Socket()) {
                soc.connect(new InetSocketAddress(addr, openPort), timeOutMillis);
                //InetAddress.getByName(hostname);
            }
            return true;
        } catch (IOException ex) {
            return false;
        }
    }


}