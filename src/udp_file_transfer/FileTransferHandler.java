package udp_file_transfer;

import org.apache.commons.io.FileUtils;
import udp_chat.Launcher;
import udp_chat.Peer;
import utility.ByteUtils;

import java.io.File;
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.net.UnknownHostException;

public class FileTransferHandler {
    private final int DATA_SIZE = 1024;
    private final int EFFECTIVE_DATA_BYTES = DATA_SIZE - this.getPacketInfoSize();

    private Peer mainPeer;
    private File selectedFile;
    private String selectedAddress;

    private File pendingFile;
    private String pendingTarget;
    private int pendingPort;

    private byte[] buffer = new byte[DATA_SIZE];

    /**
     * Main class constructor
     *
     * @param peer
     */
    public FileTransferHandler(Peer peer) {
        this.mainPeer = peer;
    }

    public void trySendingFile(File file, String target, int portNumber) {
        System.out.println("Trying to get transfer confirmation...");

        // setup pending
        this.pendingFile = file;
        this.pendingTarget = target;
        this.pendingPort = portNumber;

        // send message to target
        this.sendRequestMessage(this.pendingFile, this.pendingTarget, this.pendingPort);

    }


    public void startFileTransfer() throws IOException {

        System.out.println("Starting file tranfer...");
        System.out.println("File name : " + this.pendingFile.getName());
        System.out.println("File size : " + this.pendingFile.length());
        System.out.println("Target : " + pendingTarget);
        //
        String filename = pendingFile.getName();

        long fileSize = pendingFile.length();
        long totalPacketNumber = (long) Math.ceil(fileSize / EFFECTIVE_DATA_BYTES);

        // open file and read it
        byte[] fileData = FileUtils.readFileToByteArray(pendingFile);

        // packet arrays
        boolean[] sentPackets = new boolean[(int) totalPacketNumber];
        boolean[] receivedPackets = new boolean[(int) totalPacketNumber];

        this.setAllFalse(sentPackets);
        this.setAllFalse(receivedPackets);

        /**
         *
         */

        DatagramPacket p;
        byte[] currentLoopData;

        // start thread listening for packet acks


        for (int i = 0; i < totalPacketNumber; i++) {

            // get data
            int startIndex = (i * EFFECTIVE_DATA_BYTES);
            currentLoopData = this.copyByteArray(fileData, startIndex);

            // create packets

            p = this.createFilePacket(currentLoopData, i, totalPacketNumber, filename,
                    pendingTarget, pendingPort);

            // each packet has ack listeners

            // send packet
            this.mainPeer.getFileTransferClient().getSocket().send(p);

            // set packet as true
            sentPackets[i] = true;

            System.out.println("Sent packet nr. " + i);
        }

        System.out.println("File sent.");

    }

    /**
     * Method used to structurize a packet for the socket to send
     *
     * @param data
     * @param packetNumber
     * @param packetTotal
     * @param filename
     * @return
     */
    private DatagramPacket createFilePacket(byte[] data, long packetNumber, long packetTotal
            , String filename, String target, int portNumber) throws UnknownHostException {
        byte[] actualData = new byte[DATA_SIZE];

        byte[] packetdesc = "!PACKET!".getBytes();
        int initLong = packetdesc.length;

        int start = initLong;
        int packetNrBytes = 8;
        int packetTotalBytes = 8;


        byte[] packetNumberByteArray = ByteUtils.longToBytes(packetNumber);
        byte[] packetTotalNumberByteArray = ByteUtils.longToBytes(packetTotal);
        byte[] filenameByteArray = filename.getBytes();

        // start packet number
        int packetNrStart = start;
        int packetNrEnd = packetNrStart + packetNrBytes;

        // start total packets
        int packetTotalStart = packetNrStart + packetNrBytes;
        int packetTotalEnd = packetTotalStart + packetTotalBytes;

        // start filename
        int filenameStart = packetTotalStart + packetTotalBytes;
        int filenameEnd = filenameStart + filenameByteArray.length;

        int dataStart = filenameStart + filenameByteArray.length;
        int dataBytes = DATA_SIZE - (packetNrBytes + packetTotalBytes + filenameByteArray.length);
        int dataEnd = dataStart + dataBytes;

        int c = 0;

        // add packet desc
        for (int i = 0; i < initLong; i++) {
            actualData[i] = packetdesc[i];
        }

        // add packetNumberByes
        for (int i = packetNrStart; i < packetNrEnd; i++) {
            actualData[i] = packetNumberByteArray[c];
            c++;
        }

        c = 0;
        // add packetTotal byyes
        for (int i = packetTotalStart; i < packetTotalEnd; i++) {
            actualData[i] = packetTotalNumberByteArray[c];
            c++;
        }

        c = 0;
        // add filename byes
        for (int i = filenameStart; i < filenameEnd; i++) {
            actualData[i] = filenameByteArray[c];
            c++;
        }

        c = 0;
        // append real data to byte array
        for (int i = dataStart; i < Math.min(dataEnd, data.length); i++) {
            actualData[i] = data[c];
            c++;
        }

        DatagramPacket p =
                new DatagramPacket(actualData,
                        actualData.length,
                        InetAddress.getByName(target.substring(1)),
                        portNumber);

        return p;

    }

    private int getPacketInfoSize() {
        int packetNumber = 64;
        int packetTotal = 64;
        int filename = 64;

        return packetNumber + packetTotal + filename;
    }

    private byte[] copyByteArray(byte[] original, int startIndex) {
        byte[] toReturn = new byte[EFFECTIVE_DATA_BYTES];

        int c = 0;
        for (int i = startIndex; i < startIndex + EFFECTIVE_DATA_BYTES; i++) {
            toReturn[c] = original[i];
            c++;
        }

        return toReturn;

    }

    private void setAllFalse(boolean[] array) {
        for (int i = 0; i < array.length; i++) {
            array[i] = false;
        }
    }

    private void sendRequestMessage(File file, String target, int port) {

        DatagramPacket p = new DatagramPacket(buffer, buffer.length);

        try {
            p.setAddress(InetAddress.getByName(target.substring(1)));
            p.setPort(port);

            p.setData
                    (("!FileTransfer!" + Launcher.ipadr.getLocalIPAddress() + " wants to send file. "
                            + "Filename : file.getName() "
                            + " File size : "
                            + file.length()
                            + " bytes.").getBytes());

            this.mainPeer.getFileTransferClient().sendMessage(p);
        } catch (UnknownHostException e) {
            e.printStackTrace();
        }
    }


}
