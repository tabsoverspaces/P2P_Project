package utility;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.UnknownHostException;

/**
 * Class used to get the ip address as well as the host name
 * of the machine that is running
 */
public class IPAddressRetriever {

    public IPAddressRetriever() {

    }

    public String getExternalIPAddress() throws MalformedURLException {
        URL whatismyip = new URL("http://checkip.amazonaws.com");
        BufferedReader in = null;
        try {
            in = new BufferedReader(new InputStreamReader(
                    whatismyip.openStream()));
            String ip = in.readLine();
            return ip;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (in != null) {
                try {
                    in.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }


        return null;
    }

    /**
     * This method returns the local ip address in string format
     *
     * @return
     */
    public String getLocalIPAddress() {
        InetAddress ip;

        try {
            ip = InetAddress.getLocalHost();

            return this.shortenIp(ip.toString());

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "Error";
    }

    /**
     * This method returns the hostname in string format
     *
     * @return
     */
    public String getHostname() {
        InetAddress ip;
        String hostname;

        try {
            ip = InetAddress.getLocalHost();
            hostname = ip.getHostName();

            return hostname.toString();

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        return "Error";
    }

    /**
     * This method is used to shorten the ip address that is initially returned
     * The getLocalHost() method returns the ip in format 'hostname/ip.address'
     * This method shortens that to ip.address only.
     *
     * @param ip
     * @return
     */
    private String shortenIp(String ip) {
        int index = 0;

        while (ip.charAt(index) != '/') {
            index++;
        }
        index++;

        return ip.substring(index);
    }
}
