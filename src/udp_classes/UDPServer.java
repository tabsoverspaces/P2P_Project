package udp_classes;

import gui.MainFrame;
import special_messages.SpecialMessage;

import java.io.IOException;
import java.net.*;

public class UDPServer extends Thread {

    private final int portNumber = 6969;
    private MainFrame mainFrame;
    private Peer mainPeer;
    // main server socket
    private DatagramSocket socket;
    private DatagramPacket packet;
    // data buffer
    private byte[] buffer = new byte[1024];
    private int receivingPort;
    private boolean runningFlag;

    /**
     * Base constructor
     *
     * @param peer
     */
    public UDPServer(Peer peer) {

        this.mainPeer = peer;

        try {
            this.socket = new DatagramSocket(6969,
                    InetAddress.getByName(Launcher.ipadr.getLocalIPAddress()));
            System.out.println("Server socket successfully created");


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    /**
     * Main server logic method
     * All the behaviour is encapsulated in the method
     */
    @Override
    public void run() {

        System.out.println("Server is running");
        System.out.println("Server address " + this.socket.getLocalAddress() + "\n");


        while (this.runningFlag) {

            buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);

            try {
                this.socket.receive(this.packet);
                // System.out.println("Packet received"); // put this in the logger

                // check to see if sending address is known
                this.checkPeerList(this.packet.getAddress());

                // analysing received message
                this.analyzeMessage(this.packet);

                System.out.println(this.formatChatMessage(packet));


            } catch (IOException e) {
                e.printStackTrace();
            }


            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        System.out.println(this.runningFlag);
    }

    public void startServer() {
        this.runningFlag = true;

        // start new thread for this server
        Thread runningThread = new Thread(this);

        runningThread.start();
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

    private String formatChatMessage(DatagramPacket p) {
        StringBuilder sb = new StringBuilder();

        sb.append("Message sent by : " + p.getAddress().toString() + "\n");
        sb.append("Message content : " + new String(p.getData()) + "\n");

        return sb.toString();
    }

    private void checkPeerList(InetAddress address) {
        if (!this.mainPeer.getAddresses().contains(address)) {

            // add address to address list
            this.mainPeer.getAddresses().add(address);

            // invoke peerhandler add address method
            this.mainPeer.getPeerHandler().storePeer(address);

        }
    }

    public String getIpAddress() {
        return Launcher.ipadr.getLocalIPAddress();
    }

    private void analyzeMessage(DatagramPacket p) {
        // check for all special messages
        for (SpecialMessage m : this.mainPeer.getListOfSpecialMessages()) {
            m.checkMessage(this.mainPeer, p);
        }
    }

}
