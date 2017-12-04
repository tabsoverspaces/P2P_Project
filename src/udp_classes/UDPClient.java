package udp_classes;

import java.io.IOException;
import java.net.*;

public class UDPClient extends Thread {

    byte[] buffer = new byte[256];
    private Peer mainPeer;

    // class members
    private int port = 7000;
    //    private ArrayList<InetAddress> listOfAddresses;
    private DatagramSocket socket;
    private InetAddress address = InetAddress.getByName("");
    private DatagramPacket packet = null;

    private boolean runningFlag;

    public UDPClient(Peer peer) throws UnknownHostException {
        this.mainPeer = peer;
        this.port = 7000;

        try {
            socket = new DatagramSocket();
            System.out.println("Client socket successfully created");


        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void run() {
        System.out.println("Client is running");

        while (this.runningFlag) {
//            System.out.println("WHY CLIENT THREAD RUN METHOD");

            // pause
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }

    public void startClient() {

        this.runningFlag = true;

        Thread runningClient = new Thread(this);

        runningClient.start();
    }

    public void sendMessage(String message, InetAddress address, int portNumber) {
        DatagramPacket packet = new DatagramPacket(message.getBytes(),
                message.getBytes().length,
                address, portNumber);

        try {
            this.socket.send(packet);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
