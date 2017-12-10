package udp_file_transfer;

import udp_chat.Peer;
import utility.FileInformation;

public class FileTransferRequest {

    private String targetAddress;
    private Peer sender;
    private FileInformation information;

    public FileTransferRequest(String target, Peer sender, FileInformation information) {
        this.targetAddress = target;
        this.sender = sender;
        this.information = information;
    }
}
