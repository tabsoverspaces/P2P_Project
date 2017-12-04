package utility;

import java.io.PrintStream;
import java.util.ArrayList;

/**
 * Tihs class is used in order to aattach a listener to the console
 * When System.out.print is called, the output is sent to all observers
 */
public class SystemOutListener {

    private static SystemOutListener singleton;
    private ArrayList<ISystemOutObserver> observersList = new ArrayList<>();

    public static SystemOutListener GetSingleton() {
        if (singleton == null) {
            singleton = new SystemOutListener();
            singleton.ReplaceStandartSystemOutPrintStream();
        }
        return singleton;
    }

    public void AttachSystemOutObserver(ISystemOutObserver observer) {
        observersList.add(observer);
    }

    public void RemoveSystemOutObserver(ISystemOutObserver observer) {
        observersList.remove(observer);
    }

    private void FireSystemOutPrintln(String message) {
        for (int i = 0; i < observersList.size(); i++) {
            java.awt.EventQueue.invokeLater(new HandleSystemOutRunner(observersList.get(i), message));
        }
    }

    private void ReplaceStandartSystemOutPrintStream() {
        System.setOut(new PrintStream(System.out) {
            @Override
            public void println(String s) {
                super.println(s);
                FireSystemOutPrintln(s);
            }
        });
    }

    public interface ISystemOutObserver {
        public void HandleSystemOut(String message);
    }

    private class HandleSystemOutRunner implements Runnable {

        private String message;
        private ISystemOutObserver target;

        public HandleSystemOutRunner(ISystemOutObserver target, String message) {
            this.message = message;
            this.target = target;
        }

        @Override
        public void run() {
            target.HandleSystemOut(message);
        }
    }
}