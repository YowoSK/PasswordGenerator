import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Random;
import java.awt.datatransfer.StringSelection;
import java.awt.Toolkit;

public class Main {
    private JFrame frame;
    private JTextField passwordField;
    private JSpinner lengthSpinner;
    private JCheckBox numberCheckBox;
    private JCheckBox uppercaseCheckBox;
    private JCheckBox specialCharCheckBox;
    private JLabel strengthLabel;

    public static void main(String[] args) {
        EventQueue.invokeLater(new Runnable() {
            public void run() {
                try {
                    // Set the Nimbus Look and Feel
                    for (UIManager.LookAndFeelInfo info : UIManager.getInstalledLookAndFeels()) {
                        if ("Nimbus".equals(info.getName())) {
                            UIManager.setLookAndFeel(info.getClassName());
                            break;
                        }
                    }

                    Main window = new Main();
                    window.frame.setVisible(true);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    public Main() {
        initialize();
    }

    private void initialize() {
        frame = new JFrame("Password Maker");
        frame.setBounds(100, 100, 450, 350); // Increase the height of the frame
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.getContentPane().setLayout(null);

        JButton btnGenerate = new JButton("Generate Password");
        btnGenerate.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                int length = (Integer) lengthSpinner.getValue();
                boolean useNumbers = numberCheckBox.isSelected();
                boolean useUppercase = uppercaseCheckBox.isSelected();
                boolean useSpecialChars = specialCharCheckBox.isSelected();
                String password = generatePassword(length, useNumbers, useUppercase, useSpecialChars);
                passwordField.setText(password);
                strengthLabel.setText("Strength: " + getPasswordStrength(password)); // Update the strength label
            }
        });
        btnGenerate.setBounds(100, 150, 200, 23);
        frame.getContentPane().add(btnGenerate);

        passwordField = new JTextField();
        passwordField.setBounds(100, 180, 200, 30);
        frame.getContentPane().add(passwordField);
        passwordField.setColumns(10);

        JLabel lengthLabel = new JLabel("Set Length:");
        lengthLabel.setBounds(100, 20, 80, 20);
        frame.getContentPane().add(lengthLabel);

        SpinnerModel lengthModel = new SpinnerNumberModel(8, 7, 64, 1);
        lengthSpinner = new JSpinner(lengthModel);
        lengthSpinner.setBounds(180, 20, 50, 30);
        frame.getContentPane().add(lengthSpinner);

        numberCheckBox = new JCheckBox("Use numbers");
        numberCheckBox.setBounds(100, 50, 120, 20);
        frame.getContentPane().add(numberCheckBox);

        uppercaseCheckBox = new JCheckBox("Use uppercase");
        uppercaseCheckBox.setBounds(100, 70, 120, 20);
        frame.getContentPane().add(uppercaseCheckBox);

        specialCharCheckBox = new JCheckBox("Use special characters");
        specialCharCheckBox.setBounds(100, 90, 180, 20);
        frame.getContentPane().add(specialCharCheckBox);

        strengthLabel = new JLabel();
        strengthLabel.setBounds(100, 210, 200, 20);
        frame.getContentPane().add(strengthLabel);

        JButton btnCopy = new JButton("Copy");
        btnCopy.addActionListener(new ActionListener() {
            public void actionPerformed(ActionEvent arg0) {
                copyToClipboard(passwordField.getText());
            }
        });
        btnCopy.setBounds(310, 180, 80, 30);
        frame.getContentPane().add(btnCopy);
    }

    private String generatePassword(int length, boolean useNumbers, boolean useUppercase, boolean useSpecialChars) {
        String chars = "abcdefghijklmnopqrstuvwxyz";
        if (useUppercase) {
            chars += "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
        }
        if (useNumbers) {
            chars += "0123456789";
        }
        if (useSpecialChars) {
            chars += "!@#$%^&*()";
        }
        Random rnd = new Random();
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            sb.append(chars.charAt(rnd.nextInt(chars.length())));
        }
        return sb.toString();
    }

    private String getPasswordStrength(String password) {
        int strengthPoints = 0;
        if (password.length() >= 8) strengthPoints++;
        if (password.matches(".*\\d.*")) strengthPoints++; // contains a number
        if (password.matches(".*[A-Z].*")) strengthPoints++; // contains an uppercase letter
        if (password.matches(".*[!@#$%^&*()].*")) strengthPoints++; // contains a special character

        switch (strengthPoints) {
            case 1: return "Weak";
            case 2: return "Medium";
            case 3: return "Strong";
            default: return "Very Strong";
        }
    }

    private void copyToClipboard(String str) {
        StringSelection stringSelection = new StringSelection(str);
        Toolkit.getDefaultToolkit().getSystemClipboard().setContents(stringSelection, null);
    }
}
