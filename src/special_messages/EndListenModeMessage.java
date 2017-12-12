package special_messages;

import udp_chat.Peer;

import java.net.DatagramPacket;

public class EndListenModeMessage extends SpecialMessage {
    @Override
    public void applyMessage(Peer currentPeer, DatagramPacket p) {
        currentPeer.setCanSendMessage(true);

        System.out.println("LISTEN-MODE DISABLED");
    }

    @Override
    public String getTrigger() {
        return "END";
    }
}
