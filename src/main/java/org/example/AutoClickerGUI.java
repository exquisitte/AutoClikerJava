import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AutoClickerGUI extends JFrame {

    private Robot robot;
    private boolean clicking = false;

    public AutoClickerGUI() {
        try {
            robot = new Robot();
        } catch (AWTException e) {
            e.printStackTrace();
        }

        setTitle("AutoClicker");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel mainPanel = new JPanel();
        mainPanel.setLayout(new BorderLayout());

        JButton startButton = new JButton("Start");
        JButton stopButton = new JButton("Stop");

        startButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clicking = true;
                autoClick();
            }
        });

        stopButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                clicking = false;
            }
        });

        mainPanel.add(startButton, BorderLayout.WEST);
        mainPanel.add(stopButton, BorderLayout.EAST);

        add(mainPanel);

        // Add a KeyEventDispatcher to capture F6 globally
        KeyboardFocusManager.getCurrentKeyboardFocusManager().addKeyEventDispatcher(new KeyEventDispatcher() {
            @Override
            public boolean dispatchKeyEvent(KeyEvent e) {
                if (e.getID() == KeyEvent.KEY_PRESSED && e.getKeyCode() == KeyEvent.VK_F6) {
                    clicking = !clicking;
                    if (clicking) {
                        new Thread(() -> autoClick()).start();
                    }
                }
                return false;
            }
        });

        pack();
        setLocationRelativeTo(null);
        setVisible(true);
    }

    private void autoClick() {
        while (clicking) {
            robot.mousePress(InputEvent.BUTTON1_DOWN_MASK);
            robot.mouseRelease(InputEvent.BUTTON1_DOWN_MASK);
            try {
                Thread.sleep(100); // Adjust the delay between clicks as needed
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new AutoClickerGUI());
    }
}
