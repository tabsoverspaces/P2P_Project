package udp_classes;

import java.net.DatagramSocket;

public class UDPServer extends Thread {


    // main server socket
    private DatagramSocket socket;
    // data buffer
    private byte[] buffer;

    private int receivingPort;
    private boolean runningFlag;

    public UDPServer() {

    }

    /**
     * Main server logic method
     * All the behaviour is encapsulated in the method
     */
    @Override
    public void run() {

    }


    /**
     * Method used to set the running flag to true,
     * i.e., the server will start running
     */
    private void resumeServer() {
        this.runningFlag = true;
    }

    /**
     * Method used to set the running flag to false,
     * i.e., the server will stop running
     */
    private void pauseServer() {
        this.runningFlag = false;
    }


}
