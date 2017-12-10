package gui;

import javax.swing.*;

public class ContentLayeredPane extends JLayeredPane implements CreatableComponents {

    private ChatPanel chatPanel;
    private PeersPanel peersPanel;
    private FileTransferPanel fileTransferPanel;

    public ContentLayeredPane() {

        this.setLayout(null);
        this.setBounds(0, 60, 815, 540);
    }

    public FileTransferPanel getFileTransferPanel() {
        return fileTransferPanel;
    }

    public void setFileTransferPanel(FileTransferPanel fileTransferPanel) {
        this.fileTransferPanel = fileTransferPanel;
    }

    @Override
    public void createComponents() {

        this.chatPanel = new ChatPanel();
        this.peersPanel = new PeersPanel();
        this.fileTransferPanel = new FileTransferPanel();

        this.chatPanel.createComponents();
        this.peersPanel.createComponents();
        this.fileTransferPanel.createComponents();

        this.add(this.chatPanel, new Integer(0));
        this.add(this.peersPanel, new Integer(1));
        this.add(this.fileTransferPanel, new Integer(2));

        this.chatPanel.setVisible(true);
        this.peersPanel.setVisible(false);
        this.fileTransferPanel.setVisible(false);
    }

    public void showChatPanel() {
        this.setLayer(this.chatPanel, 10);
        this.setLayer(this.peersPanel, 0);
        this.setLayer(this.fileTransferPanel, 0);

        this.chatPanel.setVisible(true);
        this.peersPanel.setVisible(false);
        this.fileTransferPanel.setVisible(false);
    }

    public void showPeersPanel() {
        this.setLayer(this.chatPanel, 0);
        this.setLayer(this.peersPanel, 10);
        this.setLayer(this.fileTransferPanel, 0);

        this.chatPanel.setVisible(false);
        this.peersPanel.setVisible(true);
        this.fileTransferPanel.setVisible(false);
    }

    public void showFileTransferPanel() {
        this.setLayer(this.chatPanel, 0);
        this.setLayer(this.peersPanel, 0);
        this.setLayer(this.fileTransferPanel, 10);

        this.chatPanel.setVisible(false);
        this.peersPanel.setVisible(false);
        this.fileTransferPanel.setVisible(true);
    }

    public ChatPanel getChatPanel() {
        return chatPanel;
    }

    public void setChatPanel(ChatPanel chatPanel) {
        this.chatPanel = chatPanel;
    }

    public PeersPanel getPeersPanel() {
        return peersPanel;
    }

    public void setPeersPanel(PeersPanel peersPanel) {
        this.peersPanel = peersPanel;
    }
}
