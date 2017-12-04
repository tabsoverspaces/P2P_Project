package utility;

import java.util.ArrayList;

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
 */
public class PeerHandler {

    private String savefilePath;

    /**
     * This method is used to return an array of all the peers
     * that the peer handler object has persisted on the disk
     *
     * @return
     */
    public String[] loadPeers() {
        ArrayList<String> listOfAddresses = new ArrayList<>();

        // open file

        // read file

        // for each line in file

        // add to list

        return (String[]) listOfAddresses.toArray();
    }

    /**
     * This method is used to save a peer address data
     * to the hard drive
     *
     * @param address
     */
    public void storePeer(String address) {

    }
}
