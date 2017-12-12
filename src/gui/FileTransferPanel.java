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
    // lbels
    private JLabel filenameLabel;
    private JLabel peerAddressLabel;
    //
    private JButton sendTransferRequestButton;
    private JLabel requestStatusLabel;
    private JLabel timeoutLabel;


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

        // init components
        this.initializeComponents();

        // set component size and placement
        this.setComponentBounds();
        // set  component properties

        this.setComponentProperties();
        // add components
        this.addComponents();

        // action listeners
        this.addActionListenerToButtons();
        this.addActionListenerToSelectors();
    }

    private void initializeComponents() {
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
        //
        this.filenameLabel = new JLabel("<html>Filename <br></html>");
        this.peerAddressLabel = new JLabel("<html>Selected peer <br> </html>");
        //
        this.sendTransferRequestButton = new JButton("Send transfer request");
        this.requestStatusLabel = new JLabel("Status : ");
        this.timeoutLabel = new JLabel("Time left : ");
    }

    private void setComponentBounds() {
        this.peerChooser.setBounds(300, 50, 150, 50);
        this.startTransferButton.setBounds(315, 250, 150, 30);
        this.progressBar.setBounds(20, 300, 740, 40);
        this.selectFileButton.setBounds(20, 50, 150, 30);
        this.selectPeerButton.setBounds(610, 50, 150, 30);
        //
        this.resetView.setBounds(690, 10, 70, 30);
        //
        this.filenameLabel.setBounds(20, 80, 150, 50);
        this.peerAddressLabel.setBounds(610, 80, 150, 50);

        // internal frame
        this.fileSelectionFrame.setBounds(0, 0, 500, 500);
        this.fileSelectionFrame.getContentPane().add(this.fileChooser);
        this.peerSelectionFrame.setBounds(0, 0, 500, 500);
        //
        this.sendTransferRequestButton.setBounds(270, 150, 250, 30);
        this.requestStatusLabel.setBounds(270, 200, 250, 30);
        this.timeoutLabel.setBounds(520, 200, 100, 30);
    }

    private void setComponentProperties() {
        this.filenameLabel.setHorizontalAlignment(JLabel.CENTER);
        this.peerAddressLabel.setHorizontalAlignment(JLabel.CENTER);
//        this.requestStatusLabel.setHorizontalAlignment(JLabel.CENTER);
//        this.timeoutLabel.setHorizontalAlignment(JLabel.CENTER);
    }

    private void addComponents() {
        this.peerSelectionFrame.getContentPane().add(this.peerChooser);

//        this.add(this.peerChooser);
        this.add(this.startTransferButton);
        this.add(this.progressBar);
        this.add(this.statusArea);
        this.add(this.selectFileButton);
        this.add(this.selectPeerButton);
        //
        this.add(resetView);
        //
        this.add(this.filenameLabel);
        this.add(this.peerAddressLabel);

        this.add(this.fileSelectionFrame);
        this.add(this.peerSelectionFrame);
        //
        this.add(this.sendTransferRequestButton);
        this.add(this.requestStatusLabel);
        this.add(this.timeoutLabel);

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

        this.filenameLabel.setVisible(true);
        this.peerAddressLabel.setVisible(true);
        this.sendTransferRequestButton.setVisible(true);
        this.requestStatusLabel.setVisible(true);
        this.timeoutLabel.setVisible(true);


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

    public JButton getResetView() {
        return resetView;
    }

    public void setResetView(JButton resetView) {
        this.resetView = resetView;
    }

    public JLabel getFilenameLabel() {
        return filenameLabel;
    }

    public void setFilenameLabel(JLabel filenameLabel) {
        this.filenameLabel = filenameLabel;
    }

    public JLabel getPeerAddressLabel() {
        return peerAddressLabel;
    }

    public void setPeerAddressLabel(JLabel peerAddressLabel) {
        this.peerAddressLabel = peerAddressLabel;
    }

    public JButton getSendTransferRequestButton() {
        return sendTransferRequestButton;
    }

    public void setSendTransferRequestButton(JButton sendTransferRequestButton) {
        this.sendTransferRequestButton = sendTransferRequestButton;
    }

    public JLabel getRequestStatusLabel() {
        return requestStatusLabel;
    }

    public void setRequestStatusLabel(JLabel requestStatusLabel) {
        this.requestStatusLabel = requestStatusLabel;
    }

    public JLabel getTimeoutLabel() {
        return timeoutLabel;
    }

    public void setTimeoutLabel(JLabel timeoutLabel) {
        this.timeoutLabel = timeoutLabel;
    }
}
