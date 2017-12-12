package udp_file_transfer;

import udp_chat.Launcher;
import udp_chat.Peer;

import java.io.IOException;
import java.net.*;

public class FileTransferServer extends Thread {
    public static final int PORT_NUMBER = 9696;
    private Peer mainPeer;

    // main server socket
    private DatagramSocket socket;
    private DatagramPacket packet;
    // data buffer
    private byte[] buffer = new byte[1024];
    private boolean runningFlag;

    public FileTransferServer(Peer peer) {

        this.mainPeer = peer;
        try {
            this.socket = new DatagramSocket(PORT_NUMBER,
                    InetAddress.getByName(Launcher.ipadr.getLocalIPAddress()));
            System.out.println("File transfer : Server socket successfully created");


        } catch (SocketException e) {
            e.printStackTrace();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    public void startServer() {
        this.runningFlag = true;

        // start new thread for this server
        Thread runningThread = new Thread(this);

        runningThread.start();
    }

    @Override
    public void run() {

        System.out.println("File transfer : Server is running");
        System.out.println("File transfer : Server address " + this.socket.getLocalAddress() + "\n");


        while (this.runningFlag) {

            buffer = new byte[1024];
            packet = new DatagramPacket(buffer, buffer.length);

            try {
                this.socket.receive(this.packet);

                //check to see if sending address is known
                this.checkPeerList(this.packet.getAddress());

                // analysing received message
                this.analyzeMessage(this.packet);


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

    private void analyzeMessage(DatagramPacket p) {

        if (new String(p.getData()).contains("!FileTransfer!")) {

            System.out.println("New pending file!");

            this.mainPeer.setPendingFileTransfer(p);

        } else if (new String(p.getData()).contains("!TransferConfirmation!")) {
            try {
                this.mainPeer.getFileTransferHandler().startFileTransfer();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else if (new String(p.getData()).substring(0, 8).equals("!PACKET!")) {

            // send packet receipt

        } else {

            System.out.println(new String(this.packet.getData()));
        }
    }

    public void sendConfirmationMessage(InetAddress target, int portNumber) {
        DatagramPacket p = new DatagramPacket(buffer, buffer.length);

        p.setAddress((target));
        p.setPort(portNumber);

        p.setData("!TransferConfirmation!".getBytes());

        this.mainPeer.getFileTransferClient().sendMessage(p);

    }


    private void checkPeerList(InetAddress address) {
        if (!this.mainPeer.getAddresses().contains(address)) {

            // add address to address list
            this.mainPeer.getAddresses().add(address);

            // invoke peerhandler add address method
            this.mainPeer.getPeerHandler().storePeer(address);

        }
    }
}
