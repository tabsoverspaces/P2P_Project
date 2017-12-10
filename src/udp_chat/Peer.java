package udp_chat;

import gui.MainFrame;
import special_messages.EndListenModeMessage;
import special_messages.SpecialMessage;
import special_messages.StartListenModeMessage;
import utility.PeerHandler;

import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashSet;

public class Peer {

//    public static int portNumber = 6969;

    // the path where the peer file is stored
    private static final String savefile = "Data/peer_list.txt";
    private static PeerHandler peerHandler = new PeerHandler(savefile);

    private MainFrame mainFrame;
    private String name;
    private ArrayList<Peer> listOfConnectedPeers;
    private InetAddress ipAddress;
    private HashSet<InetAddress> addresses;
    private UDPServer serverThread;
    private UDPClient clientThread;
    private ArrayList<DatagramSocket> listOfSockets;


    // list of special messages
    private ArrayList<SpecialMessage> listOfSpecialMessages;

    // special flags
    private boolean canSendMessage;

    /**
     * Initializing constructor, used in all subsequent constructors
     * Purpose : initialize all class members
     */
    public Peer() {
        this.listOfConnectedPeers = new ArrayList<>();
        this.listOfSockets = new ArrayList<>();
        this.addresses = new HashSet<>();
        this.listOfSpecialMessages = new ArrayList<>();


        // stuff
        this.canSendMessage = true;


        // add special messages to list
        this.addSpecialMessages();

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

    public ArrayList<SpecialMessage> getListOfSpecialMessages() {
        return listOfSpecialMessages;
    }

    public void setListOfSpecialMessages(ArrayList<SpecialMessage> listOfSpecialMessages) {
        this.listOfSpecialMessages = listOfSpecialMessages;
    }

    public boolean canSendMessage() {
        return canSendMessage;
    }

    public void setCanSendMessage(boolean canSendMessage) {
        this.canSendMessage = canSendMessage;
    }

    /**
     * Method used to send a message to all known peers
     *
     * @param message
     */
    public void sendMessage(String message) {
        for (InetAddress addr : this.addresses) {

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
     * Method used to add a peer to the list of known list of the
     * current(main) peer
     *
     * @param address
     */
    public void addPeer(String address) {
        try {
            InetAddress addr = InetAddress.getByName(address);
            //make sure current peer doesnt add himself
            if (!this.addresses.contains(addr) && !(address.equals(this.serverThread.getIpAddress()))) {

                this.addresses.add(addr);

                this.getPeerHandler().storePeer(addr);
                System.out.println("Successfully added peer : " + addr.toString());
            }

        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
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

    public HashSet<InetAddress> getAddresses() {
        return addresses;
    }

    public void setAddresses(HashSet<InetAddress> addresses) {
        this.addresses = addresses;
    }

    public PeerHandler getPeerHandler() {
        return peerHandler;

    }

    /**
     * Method used to add an instance of special message subclass
     * to the main list upon which condition checking is done
     */
    private void addSpecialMessages() {
        // create messages here
        StartListenModeMessage startMessage = new StartListenModeMessage();
        EndListenModeMessage endMessage = new EndListenModeMessage();

        this.listOfSpecialMessages.add(startMessage);
        this.listOfSpecialMessages.add(endMessage);
    }

}


