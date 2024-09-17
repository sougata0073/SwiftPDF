package tools.protectPdf;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.encryption.AccessPermission;
import org.apache.pdfbox.pdmodel.encryption.StandardProtectionPolicy;
import utility.Fonts;
import utility.UtilityMethods;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

public class ProtectPdfMainFrame extends JFrame {

    private File selectedFile;

    public ProtectPdfMainFrame(){
        setTitle("Merge PDFs");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        JLabel selectedPdfNameLabel = new JLabel("<html><center>No PDF is selected</center></html>");
        selectedPdfNameLabel.setFont(Fonts.font2());

        JTextField ownerPasswordTf = new JTextField(10);
        ownerPasswordTf.setFont(Fonts.font2());

        JTextField userPasswordTf = new JTextField(10);
        userPasswordTf.setFont(Fonts.font2());

        JButton selectPdfBtn = new JButton("Select PDF");
        selectPdfBtn.setFont(Fonts.font1());
        selectPdfBtn.addActionListener(e -> {

            this.selectedFile = UtilityMethods.selectFile();

            if (this.selectedFile == null) return;

            selectedPdfNameLabel.setText("<html><center>" + this.selectedFile.getName() + "</center></html>");
        });

        JButton protectPdfBtn = new JButton("Protect");
        protectPdfBtn.setFont(Fonts.font2());
        protectPdfBtn.addActionListener(e -> {
            if (this.selectedFile == null){
                UtilityMethods.showNoFileSelected(this);
                return;
            }

            protect(ownerPasswordTf.getText(), userPasswordTf.getText());

            this.selectedFile = null;
            ownerPasswordTf.setText("");
            userPasswordTf.setText("");
            selectedPdfNameLabel.setText("<html><center>No PDF is selected</center></html>");
            dispose();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(selectedPdfNameLabel, gbc);

        gbc.gridy = 1;
        add(ownerPasswordTf, gbc);

        gbc.gridy = 2;
        add(userPasswordTf, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.gridy = 3;
        add(selectPdfBtn, gbc);

        gbc.gridy = 4;
        add(protectPdfBtn, gbc);
    }

    private void protect(String ownerPassword, String userPassword){
        String destinationPath = UtilityMethods.getDestinationPath();

        if (destinationPath == null) return;

        AccessPermission accessPermission = new AccessPermission();

        StandardProtectionPolicy standardProtectionPolicy = new StandardProtectionPolicy(ownerPassword, userPassword, accessPermission);
        standardProtectionPolicy.setEncryptionKeyLength(128);

        try (PDDocument doc = PDDocument.load(this.selectedFile)){

            doc.protect(standardProtectionPolicy);
            doc.save(destinationPath);

        } catch (IOException ignored) {}
    }
}
