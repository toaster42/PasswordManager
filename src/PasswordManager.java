import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class PasswordManager extends JDialog {
    private JPanel contentPane;
    private JButton buttonNew;
    private JButton buttonClose;
    private JComboBox comboBoxCategories;
    private JTextField textField1;
    private JTextField textField2;
    private JTextArea textArea1;
    private JButton buttonSave;
    private JTextField textField3;
    private JComboBox comboBoxAccounts;
    List<String> categories = new ArrayList<String>();
    List<String> accounts = new ArrayList<String>();



    public PasswordManager() {
        setContentPane(contentPane);
        setModal(true);
        getRootPane().setDefaultButton(buttonNew);

        loadCatagories();
        loadAccounts();

        buttonNew.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onNew();
            }
        });

        buttonSave.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onSave();
            }
        });

        buttonClose.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        });

// call onCancel() when cross is clicked
        setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
        addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                onClose();
            }
        });

// call onCancel() on ESCAPE
        contentPane.registerKeyboardAction(new ActionListener() {
            public void actionPerformed(ActionEvent e) {
                onClose();
            }
        }, KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), JComponent.WHEN_ANCESTOR_OF_FOCUSED_COMPONENT);

        comboBoxCategories.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                onCategorySelection();
            }
        });
    }

    private void onCategorySelection() {
        System.out.println(comboBoxCategories.getSelectedItem().toString());

    }

    private void loadAccounts() {
        accounts.add("");
        Collections.sort(accounts);
        for(accounts.)
        for(int x=0;x<=accounts.toArray().length-1;x++){
            comboBoxAccounts.addItem(accounts.toArray()[x]);
        }
    }

    private void loadCatagories() {
        categories.add("All");
        categories.add("Computer");
        categories.add("Website");
        categories.add("Club");

        Collections.sort(categories);
        for(int x=0;x<=categories.toArray().length-1;x++){
            comboBoxCategories.addItem(categories.toArray()[x]);
        }
    }

    private void onNew() {


    }

    private void onSave() {
// add your code here if necessary
        dispose();
    }

    private void onClose() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {

        PasswordManager dialog = new PasswordManager();

        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
