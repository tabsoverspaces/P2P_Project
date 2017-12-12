package udp_chat;

import udp_file_transfer.FileTransferServer;
import utility.FormatVerifier;
import utility.IPAddressRetriever;
import utility.RandomStringGenerator;

import java.io.File;
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
//        MainFrame frame = new MainFrame(peer);

        // create client and server
        peer.createServer();
        peer.createClient();
        peer.createFileTransferHandler();
        peer.createFileTransferServer();
        peer.createFileTransferClient();

        /**
         * ****************************
         * ****************************
         * ****************************
         */
        // start logic loop

        try {
            Thread.sleep(500);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        String choice = " ";
        String messageInput = "";
        String peerInput = "";

        while (!choice.equals("0")) {
            // print options
            System.out.println("\nWhat would you like to do ?");
            System.out.println("1. Send message");
            System.out.println("2. Add new peer");
            System.out.println("3. Show available peers");
            System.out.println("4. Send file");
            System.out.println("5. Accept pending file transfers");

            System.out.println("\n0. Exit app\n");

            // take input
            choice = sc.nextLine();

            if (choice.equals("0")) {
                System.exit(0);
                return;
            }

            if (choice.equals("1")) {
                // take message
                System.out.println("Enter your message : ");
                messageInput = sc.nextLine();

                // send message
                peer.sendMessage(messageInput);

            } else if (choice.equals("2")) {
                {
                    System.out.println("Enter peer address : ");
                    peerInput = sc.nextLine();

                    peer.addPeer(peerInput);

                }
            } else if (choice.equals("3")) {
                // print peers
                peer.printAllPeers();
            } else if (choice.equals("4")) {

                System.out.println("Enter file path : ");

                String filepath;
                // enter file path
                filepath = sc.nextLine();

                File file = new File(filepath);

                System.out.println("Select peer : ");
                // print peers
                peer.printAllPeers();
                // input
                int peerIndexInput = sc.nextInt();

                // check input
                if (peer.checkPeerIndex(peerIndexInput)) {
                    // validate
                    System.out.println("Peer selected : " + peer.getPeerAt(peerIndexInput));

                    // continue
                    String targetAddress = peer.getPeerAt(peerIndexInput);
                    int portNumber = FileTransferServer.PORT_NUMBER;

                    System.out.println("Starting file transfer protocol");
                    peer.getFileTransferHandler().trySendingFile(file, targetAddress, portNumber);
                }

            } else if (choice.equals("5")) {
                System.out.println("Pending sender : " + peer.getPendingSender());

                System.out.println("Do you want to accept the file?");
                int input = sc.nextInt();

                if (input == 1) {
                    System.out.println("Accepting transfer.");
                    peer.getFileTransferServer().sendConfirmationMessage
                            (peer.getPendingSender(), peer.getPendingPort());
                } else {
                    System.out.println("File transfer canceled");
                }

            }

            System.out.println("");
        }
    }
}
