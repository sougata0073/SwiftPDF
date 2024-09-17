package tools.split;

import org.apache.pdfbox.multipdf.Splitter;
import org.apache.pdfbox.pdmodel.PDDocument;
import utility.Fonts;
import utility.UtilityMethods;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SplitPdfMainFrame extends JFrame {
    private File selectedFile;

    public SplitPdfMainFrame() {
        setTitle("Split PDF");
        setSize(400, 400);
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel selectedPdfNameLabel = new JLabel("<html><center>No PDF is selected</center></html>");
        selectedPdfNameLabel.setFont(Fonts.font2());

        JCheckBox keepInSingleFolderCb = new JCheckBox("<html><center>Keep all PDFs in a single file</center></html>");
        keepInSingleFolderCb.setFont(Fonts.font2());

        JButton selectPdfBtn = new JButton("Select PDF");
        selectPdfBtn.setFont(Fonts.font1());
        selectPdfBtn.addActionListener(e -> {

            this.selectedFile = UtilityMethods.selectFile();

            if (this.selectedFile == null) return;

            selectedPdfNameLabel.setText("<html><center>" + this.selectedFile.getName() + "</center></html>");
        });

        JButton splitBtn = new JButton("Split");
        splitBtn.setFont(Fonts.font1());
        splitBtn.addActionListener(e -> {

            if (this.selectedFile == null) {
                UtilityMethods.showNoFileSelected(this);
                return;
            }

            split(keepInSingleFolderCb.isSelected());

            this.selectedFile = null;
            selectedPdfNameLabel.setText("<html><center>No PDF is selected</center></html>");
            keepInSingleFolderCb.setSelected(false);
            dispose();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(selectedPdfNameLabel, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.gridy = 1;
        add(keepInSingleFolderCb, gbc);
        gbc.gridy = 2;
        add(selectPdfBtn, gbc);
        gbc.gridy = 3;
        add(splitBtn, gbc);
    }

    private void split(Boolean inSingleFolder) {

        String destinationPath = UtilityMethods.getDestinationPath();

        if (destinationPath == null) return;

        if (inSingleFolder) {
            JTextField folderNameTf = new JTextField();
            folderNameTf.setFont(new Font("Calibri", Font.PLAIN, 18));

            int option = JOptionPane.showConfirmDialog(null, folderNameTf, "Enter the folder name to save the PDFs in it", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                destinationPath += folderNameTf.getText() + "\\\\";
                try {
                    Files.createDirectory(Paths.get(destinationPath));
                } catch (IOException ignored) {
                    System.out.println("Exception");
                }
            } else {
                return;
            }
        }

        try {
            PDDocument doc = PDDocument.load(this.selectedFile);
            int i = 1;
            for (PDDocument d : new Splitter().split(doc)) {
                d.save(destinationPath + "\\page" + i + ".pdf");
                d.close();
                i++;
            }
            doc.close();
        } catch (IOException ignored) {
            System.out.println("Exception");
        }
    }
}