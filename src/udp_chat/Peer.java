package udp_chat;

import gui.MainFrame;
import special_messages.EndListenModeMessage;
import special_messages.SpecialMessage;
import special_messages.StartListenModeMessage;
import udp_file_transfer.FileTransferClient;
import udp_file_transfer.FileTransferHandler;
import udp_file_transfer.FileTransferServer;
import utility.PeerHandler;

import java.io.File;
import java.net.DatagramPacket;
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
    private FileTransferHandler fileTransferHandler;
    private ArrayList<DatagramSocket> listOfSockets;
    private boolean canSendFile;
    private FileTransferClient fileTransferClient;
    private FileTransferServer fileTransferServer;


    // list of special messages
    private ArrayList<SpecialMessage> listOfSpecialMessages;

    // special flags
    private boolean canSendMessage;
    private boolean hasPendingFileTransfer;
    private InetAddress pendingSender;
    private int pendingPort;
    private File pendingFile;

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
        this.canSendFile = false;


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

    public static String getSavefile() {
        return savefile;
    }

    public boolean isCanSendFile() {
        return canSendFile;
    }

    public void setCanSendFile(boolean v) {
        this.canSendFile = v;
    }

    public boolean isHasPendingFileTransfer() {
        return hasPendingFileTransfer;
    }

    public void setHasPendingFileTransfer(boolean hasPendingFileTransfer) {
        this.hasPendingFileTransfer = hasPendingFileTransfer;
    }

    public InetAddress getPendingSender() {
        return pendingSender;
    }

    public void setPendingSender(InetAddress pendingSender) {
        this.pendingSender = pendingSender;
    }

    public int getPendingPort() {
        return pendingPort;
    }

    public void setPendingPort(int pendingPort) {
        this.pendingPort = pendingPort;
    }

    public File getPendingFile() {
        return pendingFile;
    }

    public void setPendingFile(File pendingFile) {
        this.pendingFile = pendingFile;
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

    public void sendMessage(String message, InetAddress addr) throws UnknownHostException {
        this.clientThread.sendMessage(message, addr, 6969);
    }

    /**
     * Method used to create and instantiante the client side
     * of the peer
     */
    public void createClient() {
        this.clientThread = new UDPClient(this);
        this.clientThread.startClient();
    }

    /**
     * Method used to create and instantiate the server side
     * of the peer
     */
    public void createServer() {
        this.serverThread = new UDPServer(this);

        this.serverThread.startServer();
    }

    public void createFileTransferServer() {
        this.fileTransferServer = new FileTransferServer(this);
        this.fileTransferServer.startServer();
    }

    public void createFileTransferClient() {
        this.fileTransferClient = new FileTransferClient(this);
        this.fileTransferClient.startClient();
    }

    public void createFileTransferHandler() {
        this.fileTransferHandler = new FileTransferHandler(this);
    }

    /**
     * Method used to add a peer to the list of known list of the
     * current(main) peer
     *
     * @param address
     */
    public void addPeer(String address) {

        // test for validity
        if (!Launcher.fv.verifyIPAddress(address)) {
            System.out.println("Peer address format not valid! Only input IPv4 addresses.");
            return;
        }

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

    public void printAllPeers() {

        System.out.println("Currently available peers : ");

        int count = 0;
        for (InetAddress addr : this.addresses) {
            System.out.println(count + ". " + addr.toString());
            count++;
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

    public void setIpAddress(InetAddress ipAddress) {
        this.ipAddress = ipAddress;
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

    public static void setPeerHandler(PeerHandler peerHandler) {
        Peer.peerHandler = peerHandler;
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

    public MainFrame getMainFrame() {
        return mainFrame;
    }

    public void setMainFrame(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }

    public ArrayList<Peer> getListOfConnectedPeers() {
        return listOfConnectedPeers;
    }

    public void setListOfConnectedPeers(ArrayList<Peer> listOfConnectedPeers) {
        this.listOfConnectedPeers = listOfConnectedPeers;
    }

    public UDPServer getServerThread() {
        return serverThread;
    }

    public void setServerThread(UDPServer serverThread) {
        this.serverThread = serverThread;
    }

    public UDPClient getClientThread() {
        return clientThread;
    }

    public void setClientThread(UDPClient clientThread) {
        this.clientThread = clientThread;
    }

    public FileTransferHandler getFileTransferHandler() {
        return fileTransferHandler;
    }

    public void setFileTransferHandler(FileTransferHandler fileTransferHandler) {
        this.fileTransferHandler = fileTransferHandler;
    }

    public ArrayList<DatagramSocket> getListOfSockets() {
        return listOfSockets;
    }

    public void setListOfSockets(ArrayList<DatagramSocket> listOfSockets) {
        this.listOfSockets = listOfSockets;
    }

    public boolean isCanSendMessage() {
        return canSendMessage;
    }

    public void setCanSendMessage(boolean canSendMessage) {
        this.canSendMessage = canSendMessage;
    }

    public FileTransferClient getFileTransferClient() {
        return fileTransferClient;
    }

    public void setFileTransferClient(FileTransferClient fileTransferClient) {
        this.fileTransferClient = fileTransferClient;
    }

    public FileTransferServer getFileTransferServer() {
        return fileTransferServer;
    }

    public void setFileTransferServer(FileTransferServer fileTransferServer) {
        this.fileTransferServer = fileTransferServer;
    }

    public int[] getPeerIndices() {
        int size = this.addresses.size();
        int[] indices = new int[size];

        int count = 0;
        int index = 0;
        for (InetAddress s : this.addresses) {
            indices[count] = index;
            index++;
        }

        return indices;
    }

    public boolean checkPeerIndex(int index) {

        int[] array = this.getPeerIndices();

        for (int i = 0; i < array.length; i++) {
            if (array[i] == index) {
                return true;
            }
        }

        return false;
    }

    public String getPeerAt(int index) {
        int count = 0;
        for (InetAddress s : this.addresses) {
            if (index == count) {
                return s.toString();
            } else {
                count++;
            }
        }

        return null;
    }

    public void setPendingFileTransfer(DatagramPacket p) {
        this.hasPendingFileTransfer = true;

        this.pendingSender = p.getAddress();
        this.pendingPort = 9696;


    }

}


