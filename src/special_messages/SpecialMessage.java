package special_messages;

import udp_classes.Peer;

import java.net.DatagramPacket;

/**
 * This class is used to handle all special message that might be sent through the system.
 * For starters, there will be one, which will be teh STOP message.
 * <p>
 * Other messages might be implemented later on.
 */
public abstract class SpecialMessage {

    // class members
    private String trigger;

    public SpecialMessage() {
    }

    /**
     * This method is used to check if a message that has been sent
     * is equal to the trigger of this class instance
     *
     * @param currentPeer
     * @param p
     */
    public void checkMessage(Peer currentPeer, DatagramPacket p) {
        if (this.checkCondition(p)) {
            this.applyMessage(currentPeer, p);
        }
    }

    /**
     * This method encapsulates the checking of the condition
     * of the message for executing the effect it would have
     *
     * @return boolean stating whether condition is satisfied
     */
    private boolean checkCondition(DatagramPacket p) {

        String compareString = (p.getData().toString());

        if ((compareString.equals(this.trigger))) {
            return true;
        } else {
            return false;
        }

    }

    /**
     * This method encapsulates the behaviour of the message's effect
     *
     * @param currentPeer
     * @param p
     */
    public abstract void applyMessage(Peer currentPeer, DatagramPacket p);

    /**
     * This method needs to be overriden in each extending subclass
     * and it needs to return a string object
     *
     * @return String object
     */
    public abstract String setTrigger();
}

