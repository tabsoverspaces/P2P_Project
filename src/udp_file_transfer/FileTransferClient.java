package udp_file_transfer;

import udp_chat.Peer;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

public class FileTransferClient extends Thread {

    private Peer mainPeer;
    private DatagramSocket socket;

    private boolean runningFlag;

    public FileTransferClient(Peer peer) {

        this.mainPeer = peer;

        try {
            socket = new DatagramSocket();
            System.out.println("File transfer : Client socket successfully created");


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

    public void startClient() {

        this.runningFlag = true;

        Thread runningClient = new Thread(this);

        runningClient.start();
    }

    @Override
    public void run() {

        System.out.println("File transfer : Client is running\n");

        while (this.runningFlag) {

            // pause
            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

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

    public void sendMessage(DatagramPacket p) {
        p.setPort(9696);
        try {
            this.socket.send(p);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
