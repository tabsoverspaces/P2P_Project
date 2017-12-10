package udp_chat;

import gui.MainFrame;
import utility.FormatVerifier;
import utility.IPAddressRetriever;
import utility.RandomStringGenerator;

import java.util.Scanner;

public class Launcher {

    public static Scanner sc = new Scanner(System.in);
    public static FormatVerifier fv = new FormatVerifier();
    public static IPAddressRetriever ipadr = new IPAddressRetriever();
    public static RandomStringGenerator rsg = new RandomStringGenerator();

    public static void main(String[] args) {

        // create peers here :/ UGLY DESIGN I KNOW
        // get the name of the peer
        String peerName = rsg.getRandomString();

        // create the main peer of the app, the one that this user uses
        // with his ip address
        // the port number is already hard-coded into the app
        Peer peer = new Peer(peerName, new IPAddressRetriever().getLocalIPAddress());
        MainFrame frame = new MainFrame(peer);

        // create client and server
        peer.createServer();
        peer.createClient();


//        peer.printPeerData();

    }
}
