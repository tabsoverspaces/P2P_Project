package udp_classes;

import java.net.InetAddress;
import java.util.ArrayList;

public class Peer {

    private String name;

    private ArrayList<Peer> listOfConnectedPeers;

    private InetAddress ipAddress;
    private int port;

    private UDPServer server;
    private UDPClient client;

    /**
     * Initializing constructor, used in all subsequent constructors
     * Purpose : initialize all class members
     */
    public Peer() {
        this.listOfConnectedPeers = new ArrayList<>();

        // init server

        // init client
    }

    /**
     * This constructor takes the IP address of the peer's machine
     * and the port and creates the instance
     *
     * @param address
     * @param port
     */
    public Peer(InetAddress address, int port) {
        this(); // calling the base constructor

        this.ipAddress = address;
        this.port = port;
    }

    /**
     * Method used to try and establish connection
     * between this peer and all of the peers in his list
     */
    @Deprecated
    public void connectToPeers() {

        for (Peer peer : this.listOfConnectedPeers) {
            this.connectToPeer(peer);
        }
    }

    /**
     * Method used to connect this peer to one other peer
     *
     * @param peer
     */
    private void connectToPeer(Peer peer) {
        // connecting stuff goes in this method

        // try to connect

        // if successful, add peer to connected peers list
        this.addPeer(peer);

    }

    /**
     * Method used to add a peer to this peer's list
     *
     * @param peer
     */
    public void addPeer(Peer peer) {
        this.listOfConnectedPeers.add(peer);
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

}
