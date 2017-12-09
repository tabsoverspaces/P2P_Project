package utility;

import java.io.*;
import java.net.InetAddress;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashSet;

/**
 * This utility class is used to handle the known(and unknown) peers of
 * the current user and persist the data.
 * <p>
 * Once a user as been added as a known once, the peer handler will store it
 * on the hard drive
 * <p>
 * Upon starting the peer, the peer handler will load all known peers into the
 * peer list of the current running peer
 * <p>
 * A hash set will make sure that one peer is not added to the list more than once
 * But all the verification is done in the server and client classes respectively
 * This one has methods that do the job and nothing more.
 */
public class PeerHandler {

    // class instance has static buffered writer
    private BufferedWriter w;
    private BufferedReader r;

    // path-related class members
    private Path currentRelativePath = Paths.get("");
    private String pathString = currentRelativePath.toAbsolutePath().toString();
    private String savefilePath = pathString + "/Data/peers.txt";


    /**
     * @param savefilePath
     */
    public PeerHandler(String savefilePath) {
        System.out.println(this.savefilePath);

        try {
            // the filewriter constructor creates a filewriter at the given path
            // and the true flag stands for appending
            // if theres a false argument, or no second argument whatsover,
            // opening the filewriter will overwrite everything
            this.w = new BufferedWriter(new FileWriter(this.savefilePath, true));

        } catch (IOException e) {

            e.printStackTrace();
        }
    }

    /**
     * This method is used to return an array of all the peers
     * that the peer handler object has persisted on the disk
     *
     * @return
     */
    public HashSet<InetAddress> loadPeers() {
        System.out.println("___________________________________");
        System.out.println("Initiating peer loading...\n");
        HashSet<InetAddress> addressSet = new HashSet<>();

        String line;
        InetAddress addr;

        // path
        try {
            this.r = new BufferedReader(new FileReader(this.savefilePath));


            while ((line = this.r.readLine()) != null) {
                if (line.isEmpty()) {
                    break;
                }

                // create new inet
                addr = InetAddress.getByName(new String(line));

                // add it to the set
                addressSet.add(addr);

                System.out.println("Found & added peer : " + addr.getHostAddress().toString());
                System.out.println("");
            }


            // close reader
            this.r.close();

        } catch (FileNotFoundException e) {
        } catch (IOException e) {
        }


        System.out.println("Peer loading complete.");
        System.out.println("___________________________________\n");
        return addressSet;
    }

    /**
     * This method is used to save a peer address data
     * to the hard drive
     *
     * @param address
     */
    public void storePeer(InetAddress address) {

        try {
            // flush the buffer first
            this.w.flush();

            // then write
            this.w.append(address.toString().substring(1));
            this.w.newLine();

            this.w.flush();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


}
