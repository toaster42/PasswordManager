import org.jasypt.util.password.StrongPasswordEncryptor;

import javax.swing.*;
import java.awt.event.*;
import java.io.*;

public class UpdateMasterPassword extends JDialog {
    private JPanel contentPane;
    private JButton buttonOK;
    private JButton buttonCancel;
    private JPasswordField passwordFieldOld;
    private JPasswordField passwordFieldNew;
    private JPasswordField passwordFieldConfirm;
    private int attemptCount = 0;

    /**
     * The purpose of this constructor is to set up the dialog and attach listeners to the buttons
     */
    public UpdateMasterPassword() {
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

    /**
     * The purpose of this method is to check the old password against the password file before encrypting
     * the new password (if the confirmation password matches) and then writing it to the password file.
     */
    private void onOK() {
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

        String inputPassword = passwordFieldOld.getText().toString();

        //System.out.println(inputPassword);
        if (passwordEncryptor.checkPassword(inputPassword, encryptedPassword)) {
            if(passwordFieldNew.getText().equals(passwordFieldConfirm.getText())) {
                String newEncryptedPassword = passwordEncryptor.encryptPassword(passwordFieldNew.getText()); //update password
                try {
                    INET inet = new INET();
                    inet.saveToFile("data/password", newEncryptedPassword);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                } catch (IOException e) {
                    e.printStackTrace();
                } catch (Exception e) {
                    e.printStackTrace();
                }


            }
            else {
                JOptionPane.showMessageDialog(null, "New Password doesn't match.");
            }

        } else {
            System.out.println("bad login!");
            attemptCount++;
            JOptionPane.showMessageDialog(null, "Incorrect Old Password");
            if (attemptCount>=5) {
                System.out.println("5 Bad attempts. Exiting.");
                System.exit(0);
            }
        }
    }

    /**
     * The purpose of this method is to close the dialog if the user selects the Cancel button
     */
    private void onCancel() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {
        UpdateMasterPassword dialog = new UpdateMasterPassword();
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
