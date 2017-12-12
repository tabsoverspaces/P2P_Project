package udp_file_transfer;

import udp_chat.Peer;

import java.io.File;

public class Packet {

    // final settings
    private final int ARRAY_SIZE = 1024;

    // basic packet properties
    private int id;
    private String fileIdentifier;
    private byte[] data = new byte[ARRAY_SIZE];

    // sender and target
    private Peer sender;
    private String target;

    /**
     * Main class constructor
     *
     * @param sender   peer object that is sending the packet
     * @param target   IP of the target that is receiving it
     * @param origin   the original file that the sender is trying to send
     * @param packetID the id of the packet that is to be created
     */
    public Packet(Peer sender, String target, File origin, byte[] data, int packetID) {
        this.id = packetID;
        this.sender = sender;
        this.target = target;
        this.id = packetID;
        this.fileIdentifier = origin.getName();

        // create byte array


    }

}
