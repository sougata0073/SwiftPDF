package tools.deletePages;

import org.apache.pdfbox.pdmodel.PDDocument;
import utility.Fonts;
import utility.UtilityMethods;
import utility.WrongInputException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;

public class DeletePagesMainFrame extends JFrame {
    private File selectedFile;
    private int[] pageNums;

    public DeletePagesMainFrame() {
        setTitle("Delete Pages");
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLayout(new GridBagLayout());
        setLocationRelativeTo(null);

        JLabel selectedPdfNameLabel = new JLabel("<html><center>No pdf is selected</center></html>");
        selectedPdfNameLabel.setFont(Fonts.font1());

        JLabel hintLabel = new JLabel("<html><center>Enter page numbers<br>Example: 1,2,3,8-12,15-20,25,28</center></html>");
        hintLabel.setFont(Fonts.font1());

        JTextField pageNumsTf = new JTextField(10);
        pageNumsTf.setFont(Fonts.font2());

        JButton selectPdfBtn = new JButton("Select PDF");
        selectPdfBtn.setFont(Fonts.font1());

        selectPdfBtn.addActionListener(e -> {
            this.selectedFile = UtilityMethods.selectFile();

            if (this.selectedFile == null) return;

            selectedPdfNameLabel.setText("<html><center>" + this.selectedFile.getName() + "</center></html>");
            System.out.println();
        });

        JButton deleteBtn = new JButton("Delete");
        deleteBtn.setFont(Fonts.font1());

        deleteBtn.addActionListener(e -> {
            if (this.selectedFile == null) {
                UtilityMethods.showNoFileSelected(this);
                return;
            }

            try {
                this.pageNums = UtilityMethods.getPageNumbers(pageNumsTf.getText());
            } catch (WrongInputException exp) {
                JOptionPane.showMessageDialog(this, "Invalid page range", "Invalid input", JOptionPane.ERROR_MESSAGE);
                return;
            } catch (NumberFormatException exp) {
                JOptionPane.showMessageDialog(this, "Invalid page number", "Invalid input", JOptionPane.ERROR_MESSAGE);
                return;
            }

            delete();

            this.pageNums = null;
            selectedFile = null;
            selectedPdfNameLabel.setText("<html><center>No PDF is selected</center></html>");
            pageNumsTf.setText("");
            dispose();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(selectedPdfNameLabel, gbc);

        gbc.insets.bottom = 10;

        gbc.gridy = 1;
        add(hintLabel, gbc);

        gbc.insets.top = 10;
        gbc.insets.bottom = 20;

        gbc.gridy = 2;
        add(pageNumsTf, gbc);

        gbc.insets.bottom = 10;

        gbc.gridy = 3;
        add(selectPdfBtn, gbc);

        gbc.gridy = 4;
        add(deleteBtn, gbc);
    }

    private void delete() {
        String destinationPath = UtilityMethods.getDestinationPath();

        if (destinationPath == null) return;

        try (PDDocument newDoc = new PDDocument();
             PDDocument oldDoc = PDDocument.load(this.selectedFile)) {

            for (int i = 0; i < oldDoc.getNumberOfPages(); i++)
                if (!isPresent(this.pageNums, i))
                    newDoc.addPage(oldDoc.getPage(i));

            newDoc.save(destinationPath);

        } catch (IOException ignored) {
        }
    }

    private boolean isPresent(int[] arr, int key) {
        for (int i : arr) if (i == key) return true;
        return false;
    }
}
