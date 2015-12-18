import javax.swing.*;
import java.awt.event.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;

public class PasswordManager extends JDialog {
    private JPanel contentPane;
    private JButton buttonNew;
    private JButton buttonClose;
    private JComboBox comboBoxCategories;
    private JTextField textFieldUser;
    private JTextField textFieldPass;
    private JTextArea textAreaNote;
    private JButton buttonSave;
    private JTextField textFieldName;
    private JComboBox comboBoxAccounts;
    private JButton newCategoryButton;
    private JButton masterPasswordButton;
    private String categoriesFile = "data/categories";
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
                if (e.getStateChange()==1) {
                    onCategorySelection();
                }
            }
        });
        comboBoxAccounts.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if (e.getStateChange()==1) {
                    onAccountSelection();
                }
            }
        });
        newCategoryButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onNewCategory();
            }
        });
        masterPasswordButton.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                onMasterPassword();
            }
        });
    }

    private void onMasterPassword() {
UpdateMasterPassword updateMasterPassword = new UpdateMasterPassword();
        updateMasterPassword.pack();
        updateMasterPassword.setVisible(true);
    }

    private void onNewCategory() {
        NewCategory newCategory = new NewCategory();
        newCategory.pack();
        newCategory.setVisible(true);
        loadCatagories();
    }

    private void onAccountSelection() {
        textFieldName.setText(comboBoxAccounts.getSelectedItem().toString());
        String[] staTemp =accounts.get(comboBoxAccounts.getSelectedIndex()).split(";", 4);
        String username = staTemp[1];
        String password = staTemp[2];
        String note = staTemp[3];
        textFieldUser.setText(username);
        textFieldPass.setText(password);
        textAreaNote.setText(note);
    }

    private void onCategorySelection() {
        System.out.println(comboBoxCategories.getSelectedItem().toString());
        loadAccounts();

    }

    private void loadAccounts() {
        INET inet = new INET();
        String fileContents = "";
        String accountsFile = "data/" + comboBoxCategories.getSelectedItem().toString();
        if (inet.fileExists(accountsFile)) {
            try {
                fileContents = inet.getFromFile(accountsFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("File " + accountsFile + " does not exist.");
            }


        String[] staAccounts = fileContents.split("\r\n");
        accounts.clear();

        for(int x=0;x<=staAccounts.length-1;x++){
            accounts.add(staAccounts[x]);
        }

        Collections.sort(accounts);
        comboBoxAccounts.removeAllItems();

        for(int x=0;x<=accounts.toArray().length-1;x++){
            StringTokenizer st = new StringTokenizer(accounts.toArray()[x].toString(), ";");
            if (st.countTokens()!=0) {
                String[] staTemp = accounts.toArray()[x].toString().split(";", 2);
                comboBoxAccounts.addItem(staTemp[0]);
            }
        }
    }

    private void loadCatagories() {
        INET inet = new INET();
        String fileContents = "";
        if (inet.fileExists(categoriesFile)) {
            try {
                fileContents = inet.getFromFile(categoriesFile);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        else {
            System.out.println("File " + categoriesFile + " does not exist.");
        }
        String[] staCategories = fileContents.split("\r\n");
        categories.clear();
        for(int x=1;x<=staCategories.length-1;x++){
            categories.add(staCategories[x]);
        }

        Collections.sort(categories);
        comboBoxCategories.removeAllItems();
        for(int x=0;x<=categories.toArray().length-1;x++){
            comboBoxCategories.addItem(categories.toArray()[x]);
        }
    }

    private void onNew() {
        INET inet = new INET();
        try {
            System.out.println(("data/" + comboBoxCategories.getSelectedItem().toString()));
            System.out.println("New Entry;;;");
            inet.appendToFile("data/" + comboBoxCategories.getSelectedItem().toString(), " New Entry;;;\r\n");
            loadAccounts();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void onSave() {
// add your code here if necessary
        accounts.remove(comboBoxAccounts.getSelectedIndex());
        String nameToSave = textFieldName.getText();
        String userToSave = textFieldUser.getText();
        String passToSave = textFieldPass.getText();
        String noteToSave = textAreaNote.getText();
        String saveData = nameToSave + ";" + userToSave + ";" + passToSave + ";" + noteToSave;
        accounts.add(comboBoxAccounts.getSelectedIndex(), saveData);
        Collections.sort(accounts);
        String updatedAccounts = "";
        for(int x=0;x<=accounts.toArray().length-1;x++){
            System.out.println(accounts.toArray()[x]);
            updatedAccounts += accounts.toArray()[x]; // + "\r\n";
        }

        INET inet = new INET();
        try {
            inet.saveToFile("data/" + comboBoxCategories.getSelectedItem().toString(), updatedAccounts);
        } catch (Exception e) {
            e.printStackTrace();
        }
        loadAccounts();
        comboBoxAccounts.setSelectedIndex(accounts.indexOf(saveData));

    }

    private void onClose() {
// add your code here if necessary
        dispose();
    }

    public static void main(String[] args) {

        PasswordManager dialog = new PasswordManager();
        MasterPassword passwordEntry = new MasterPassword();
        passwordEntry.pack();
        passwordEntry.setVisible(true);
        dialog.pack();
        dialog.setVisible(true);
        System.exit(0);
    }
}
