package udp_chat;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class UDPClient extends Thread {

    private Peer mainPeer;

    // class members
    private DatagramSocket socket;

    private boolean runningFlag;

    public UDPClient(Peer peer) {
        this.mainPeer = peer;

        try {
            socket = new DatagramSocket();
            System.out.println("Chat application : Client socket successfully created");


        } catch (SocketException e) {
            e.printStackTrace();
        }
    }

    public DatagramSocket getSocket() {
        return socket;
    }

    public void setSocket(DatagramSocket socket) {
        this.socket = socket;
    }

    @Override
    public void run() {

        System.out.println("Chat application : Client is running\n");

        // load peers here
        this.mainPeer.setAddresses(this.mainPeer.getPeerHandler().loadPeers());

        while (this.runningFlag) {

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
        if (this.mainPeer.isCanSendMessage()) {
            DatagramPacket packet = new DatagramPacket(message.getBytes(),
                    message.getBytes().length,
                    address, portNumber);

            try {
                this.socket.send(packet);

            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            System.out.println("LISTEN-MODE ENABLED. Cannot send messages");
        }
    }

}
