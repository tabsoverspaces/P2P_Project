package special_messages;

import udp_classes.Peer;

import java.net.DatagramPacket;

public class StartListenModeMessage extends SpecialMessage {

    @Override
    public void applyMessage(Peer currentPeer, DatagramPacket p) {
        currentPeer.setCanSendMessage(false);

        // show listen-mode on screen
        // to all peers, send message showing listen mode
        currentPeer.sendMessage("LISTEN-MODE ON");
    }

    @Override
    public String setTrigger() {
        return "STOP";
    }
}
