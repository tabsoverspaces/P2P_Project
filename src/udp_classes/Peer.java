package udp_classes;

import gui.MainFrame;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.net.UnknownHostException;
import java.util.ArrayList;

public class Peer {

//    public static int portNumber = 6969;

    private MainFrame mainFrame;

    private String name;
    private ArrayList<Peer> listOfConnectedPeers;
    private InetAddress ipAddress;
    private ArrayList<InetAddress> addresses;
    private UDPServer serverThread;
    private UDPClient clientThread;
    private ArrayList<DatagramSocket> listOfSockets;
    private int outgoingPort;
    private int incomingPort;

    /**
     * Initializing constructor, used in all subsequent constructors
     * Purpose : initialize all class members
     */
    public Peer() {
        this.listOfConnectedPeers = new ArrayList<>();
        this.listOfSockets = new ArrayList<>();
        this.addresses = new ArrayList<>();

        // add target peers

    }

    /**
     * This constructor takes the IP address of the peer's machine
     * and the port and creates the instance
     *
     * @param address
     */
    public Peer(InetAddress address) {
        this(); // calling the base constructor

        this.ipAddress = address;
    }

    /**
     * This method does the same thing as the previous one, it just takes a string
     * instead of a InetAddress object
     * It creates the object inside its body
     *
     * @param address
     */
    public Peer(String address) {
        this();

        try {
            this.ipAddress = InetAddress.getByName(address);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method uses the previous constructor to create the peer IP address,
     * but it takes also a string for the name of the peer
     *
     * @param name
     * @param address
     */
    public Peer(String name, String address) {
        this(address);

        this.name = name;
    }

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public UDPClient getClientThread() {
        return clientThread;
    }

    public void setClientThread(UDPClient clientThread) {
        this.clientThread = clientThread;
    }

    public void sendMessage(String message) {
        for (InetAddress addr : this.addresses) {

            System.out.println("Sending message to " + addr.toString());

            this.clientThread.sendMessage(message, addr, 6969);
        }
    }

    /**
     * Method used to create and instantiante the client side
     * of the peer
     */
    public void createClient() {
        try {
            this.clientThread = new UDPClient(this);
            this.clientThread.startClient();
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }


    }

    /**
     * Method used to create and instantiate the server side
     * of the peer
     */
    public void createServer() {
        this.serverThread = new UDPServer(this);

        this.serverThread.startServer();
    }

    /**
     * Method used to add a peer to  this peer's list
     * and also create a socket for it
     *
     * @param peer
     */
    private void addPeer(Peer peer, int portNumber) {
        try {
            DatagramSocket s = new DatagramSocket(portNumber, peer.getIpAddress());

            this.listOfConnectedPeers.add(peer);
            this.listOfSockets.add(s);

        } catch (SocketException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Compared to the previous method, this one only takes a string as a address
     * , then creates a peer instance and only then it invokes the previous method
     *
     * @param address
     */
    public void addPeer(String address, int portNumber) {

        // create peer from address
        Peer p = new Peer(address);

        // then use previous method
        this.addPeer(p, portNumber);

        // print info
        System.out.println("New peer successfully added");
        System.out.println("Peer info : ");
        p.printPeerData();
    }

    public void addPeer(String address) {
        try {
            InetAddress addr = InetAddress.getByName(address);
            this.addresses.add(addr);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }

        this.addingSuccessful();
    }

    /**
     * Method used to start the peer
     * i.e. start both client and server parts of the peer
     */
    public void goOnline() {
        this.serverThread.startServer();
        this.clientThread.startClient();
    }

    /**
     * Method used to remove a peer from this peer's list
     *
     * @param peer
     */
    public void removePeer(Peer peer) {
        this.listOfConnectedPeers.remove(peer);
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }


    public InetAddress getIpAddress() {
        return this.ipAddress;
    }

    public void printPeerData() {
        System.out.println("Peer name : " + this.name);
        System.out.println("Peer address : " + this.ipAddress.toString());
    }

    private void addingSuccessful() {
        System.out.println("Peer address added successfully\n");
    }

}
