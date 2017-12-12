package special_messages;

import udp_chat.Peer;

import java.net.DatagramPacket;

public class StartListenModeMessage extends SpecialMessage {

    @Override
    public void applyMessage(Peer currentPeer, DatagramPacket p) {
        currentPeer.setCanSendMessage(false);

        System.out.println("LISTEN-MODE ENABLED BY " + p.getAddress().toString());
    }

    @Override
    public String getTrigger() {
        return "STOP";
    }
}
