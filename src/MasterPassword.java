import javax.swing.*;
import java.awt.event.*;
import java.io.*;

import org.jasypt.util.password.StrongPasswordEncryptor;

public class MasterPassword extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField passwordField;
    private int attemptCount = 0;

    public MasterPassword() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonOK);

        buttonOK.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onOK();
            }
        });

        buttonCancel.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onCancel();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onCancel();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);
    }

    private void onOK() {
// add your code here
        StrongPasswordEncryptor passwordEncryptor = new StrongPasswordEncryptor();
        String encryptedPassword = ""; //passwordEncryptor.encryptPassword("123456");
        FileInputStream fstream = null;
        try {
            fstream = new FileInputStream("data/password");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        BufferedReader br = new BufferedReader(new InputStreamReader(fstream));

        try {
            String strLine = br.readLine();
            encryptedPassword = strLine;
        } catch (IOException e) {
            e.printStackTrace();
        }

        String inputPassword = passwordField.getText().toString();

        System.out.println(inputPassword);
        if (passwordEncryptor.checkPassword(inputPassword, encryptedPassword)) {
            System.out.println("correct!");
            dispose();
        } else {
            System.out.println("bad login!");
            attemptCount++;
            JOptionPane.showMessageDialog(null, "Incorrect Password");
            if (attemptCount>=5) {
                System.out.println("5 Bad attempts. Exiting.");
                System.exit(0);
            }
        }

    }

    private void onCancel() {
// add your code here if necessary
        System.exit(0);
    }

    public static void main(String[] args) {
        MasterPassword dialog = new MasterPassword();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
