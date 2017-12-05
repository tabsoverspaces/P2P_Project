package gui.custom_components;

import utility.SystemOutListener;

import javax.swing.*;

public class JTextAreaCustom extends JTextArea implements SystemOutListener.ISystemOutObserver {
    @Override
    public void HandleSystemOut(String message) {
        this.append(message + "\n");
    }
}
