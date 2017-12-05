package gui;

import gui.custom_components.JTextAreaCustom;
import utility.SystemOutListener;

import javax.swing.*;

public class ChatPanel extends JPanel implements CreatableComponents {

    private JTextAreaCustom textArea;
    private JTextField textField;
    private JButton sendButton;

    public ChatPanel() {

        this.setLayout(null);

        this.setBounds(10, 0, 780, 540);

    }

    @Override
    public void createComponents() {

        this.textArea = new JTextAreaCustom();
        this.textArea.setEditable(false);
        this.textArea.setBounds(0, 0, 780, 490);

        this.textField = new JTextField();
        this.textField.setBounds(0, 500, 700, 30);

        this.sendButton = new JButton("Send");
        this.sendButton.setBounds(710, 500, 70, 30);

        this.addComponents();

        // add system out listener
        SystemOutListener.GetSingleton().AttachSystemOutObserver(this.textArea);

    }

    /**
     * Method used to separate the adding of components to the panel
     * in order to better organise the createComponents method
     */
    private void addComponents() {
        this.add(this.textArea);
        this.add(this.textField);
        this.add(this.sendButton);
    }

    public JTextArea getTextArea() {
        return this.textArea;
    }

    public void setTextArea(JTextAreaCustom textArea) {
        this.textArea = textArea;
    }

    public JTextField getTextField() {
        return textField;
    }

    public void setTextField(JTextField textField) {
        this.textField = textField;
    }

    public JButton getSendButton() {
        return sendButton;
    }

    public void setSendButton(JButton sendButton) {
        this.sendButton = sendButton;
    }
}
