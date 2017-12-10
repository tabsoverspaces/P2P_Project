package gui;

import gui.custom_components.PeerChooser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class FileTransferPanel extends JPanel implements CreatableComponents {

    private JFileChooser fileChooser;
    private PeerChooser peerChooser;
    private JButton startTransferButton;
    private JProgressBar progressBar;
    private JTextArea statusArea;
    private JButton selectFileButton;
    private JButton selectPeerButton;
    //
    private JInternalFrame fileSelectionFrame;
    private JInternalFrame peerSelectionFrame;
    //
    // cancelation contorl buttons
    private JButton resetView;


    public FileTransferPanel() {
        this.setLayout(null);

        this.setBounds(10, 0, 780, 540);
    }


    public JButton getSelectPeerButton() {
        return selectPeerButton;
    }

    public void setSelectPeerButton(JButton selectPeerButton) {
        this.selectPeerButton = selectPeerButton;
    }

    @Override
    public void createComponents() {

        this.fileChooser = new JFileChooser();
        this.peerChooser = new PeerChooser();
        this.startTransferButton = new JButton("Start file transfer");
        this.selectFileButton = new JButton("Select file");
        this.selectPeerButton = new JButton("Select peer");
        this.progressBar = new JProgressBar();
        this.statusArea = new JTextArea();
        // internal frame
        this.fileSelectionFrame = new JInternalFrame();
        this.peerSelectionFrame = new JInternalFrame();
        //
        this.resetView = new JButton("Hide");

        this.peerChooser.setBounds(300, 50, 150, 50);
        this.startTransferButton.setBounds(215, 150, 150, 30);
        this.progressBar.setBounds(20, 300, 740, 40);
        this.selectFileButton.setBounds(20, 50, 150, 30);
        this.selectPeerButton.setBounds(300, 50, 150, 30);
        this.resetView.setBounds(500, 50, 100, 30);
        // internal frame
        this.fileSelectionFrame.setBounds(0, 0, 500, 500);
        this.fileSelectionFrame.getContentPane().add(this.fileChooser);
        this.peerSelectionFrame.setBounds(0, 0, 500, 500);
        this.peerSelectionFrame.getContentPane().add(this.peerChooser);

//        this.add(this.peerChooser);
        this.add(this.startTransferButton);
        this.add(this.progressBar);
        this.add(this.statusArea);
        this.add(this.selectFileButton);
        this.add(this.selectPeerButton);
        this.add(resetView);

        this.add(this.fileSelectionFrame);
        this.add(this.peerSelectionFrame);

        // action listeners
        this.addActionListenerToButtons();
        this.addActionListenerToSelectors();
    }

    /**
     * Method used to encapsulate all actionlistener-assignments
     */
    private void addActionListenerToButtons() {

        this.selectFileButton.addActionListener((ActionEvent e) -> {

            this.hideAllComponents();
            FileTransferPanel.this.fileSelectionFrame.setVisible(true);
        });
        this.selectPeerButton.addActionListener((ActionEvent e) -> {

            this.hideAllComponents();
            FileTransferPanel.this.peerSelectionFrame.setVisible(true);
        });

        this.resetView.addActionListener((ActionEvent e) -> {
            this.resetViewAction();
        });
    }

    /**
     * Method used to hide all components that have been added to the file transfer panel
     */
    private void hideAllComponents() {

        for (Component c : this.getComponents()) {
            c.setVisible(false);
        }

        // reset buttons shown at all times
        this.resetView.setVisible(true);
    }

    /**
     * Method used to show all components that have been added to the file transfer panel
     */
    private void showAllComponents() {
        this.startTransferButton.setVisible(true);
        this.progressBar.setVisible(true);
        this.selectFileButton.setVisible(true);
        this.selectPeerButton.setVisible(true);


    }

    private void addActionListenerToSelectors() {
        this.fileChooser.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent evt) {
                fileChooserActionPerformed(evt);
            }

            private void fileChooserActionPerformed(ActionEvent e) {
                if (e.getActionCommand().equals(javax.swing.JFileChooser.APPROVE_SELECTION)) {

                } else if (e.getActionCommand().equals(javax.swing.JFileChooser.CANCEL_SELECTION)) {
                    FileTransferPanel.this.showAllComponents();
                    FileTransferPanel.this.fileSelectionFrame.setVisible(false);

                }
            }

        });
    }

    public JFileChooser getFileChooser() {
        return fileChooser;
    }

    public void setFileChooser(JFileChooser fileChooser) {
        this.fileChooser = fileChooser;
    }

    public PeerChooser getPeerChooser() {
        return peerChooser;
    }

    public void setPeerChooser(PeerChooser peerChooser) {
        this.peerChooser = peerChooser;
    }

    public JButton getStartTransferButton() {
        return startTransferButton;
    }

    public void setStartTransferButton(JButton startTransferButton) {
        this.startTransferButton = startTransferButton;
    }

    public JProgressBar getProgressBar() {
        return progressBar;
    }

    public void setProgressBar(JProgressBar progressBar) {
        this.progressBar = progressBar;
    }

    public JTextArea getStatusArea() {
        return statusArea;
    }

    public void setStatusArea(JTextArea statusArea) {
        this.statusArea = statusArea;
    }

    public JButton getSelectFileButton() {
        return selectFileButton;
    }

    public void setSelectFileButton(JButton selectFileButton) {
        this.selectFileButton = selectFileButton;
    }

    public JInternalFrame getFileSelectionFrame() {
        return fileSelectionFrame;
    }

    public void setFileSelectionFrame(JInternalFrame fileSelectionFrame) {
        this.fileSelectionFrame = fileSelectionFrame;
    }

    public JInternalFrame getPeerSelectionFrame() {
        return peerSelectionFrame;
    }

    public void setPeerSelectionFrame(JInternalFrame peerSelectionFrame) {
        this.peerSelectionFrame = peerSelectionFrame;
    }

    public void resetViewAction() {

        // hide internal panels
        FileTransferPanel.this.peerSelectionFrame.setVisible(false);
        FileTransferPanel.this.fileSelectionFrame.setVisible(false);

        this.showAllComponents();
    }
}
