package special_messages;

import udp_classes.Peer;

import java.net.DatagramPacket;

public class EndListenModeMessage extends SpecialMessage {
    @Override
    public void applyMessage(Peer currentPeer, DatagramPacket p) {
        currentPeer.setCanSendMessage(true);

        // turn off listen mode

        currentPeer.sendMessage("LISTEN-MODE OFF");
    }

    @Override
    public String setTrigger() {
        return "END";
    }
}