package tools.merge;

import org.apache.pdfbox.io.MemoryUsageSetting;
import org.apache.pdfbox.multipdf.PDFMergerUtility;
import utility.Fonts;
import utility.UtilityMethods;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

public class MergePdfMainFrame extends JFrame {
    private String pdfNames = "";
    private final ArrayList<File> filesList;

    public MergePdfMainFrame() {
        setTitle("Merge PDFs");
        setLayout(new GridBagLayout());
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setSize(400, 400);
        setLocationRelativeTo(null);

        this.filesList = new ArrayList<>();

        JLabel selectedPdfNameLabel = new JLabel("<html><center>" + "No pdf is selected" + "</center></html>");
        selectedPdfNameLabel.setFont(Fonts.font2());

        JButton addBtn = new JButton("Add PDF");
        addBtn.setFont(Fonts.font1());
        addBtn.addActionListener(e -> {

            File file = UtilityMethods.selectFile();

            if (file != null) {
                this.filesList.add(file);
                this.pdfNames += file.getName() + "<br>";
                selectedPdfNameLabel.setText("<html><center>" + this.pdfNames + "</center></html>");
            }
        });

        JButton mergeBtn = new JButton("Merge");
        mergeBtn.setFont(Fonts.font1());
        mergeBtn.addActionListener(e -> {
            if (this.filesList.size() < 2) {
                JOptionPane.showMessageDialog(this, "Select at least two PDF file", "Wrong number of PDF", JOptionPane.ERROR_MESSAGE);
                return;
            }

            merge();

            this.filesList.clear();
            selectedPdfNameLabel.setText("<html><center>" + "No pdf is selected" + "</center></html>");
            this.pdfNames = "";
            dispose();
        });

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(20, 0, 20, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(selectedPdfNameLabel, gbc);

        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.gridy = 1;
        add(addBtn, gbc);
        gbc.gridy = 2;
        add(mergeBtn, gbc);
    }

    private void merge() {
        String destinationPath = UtilityMethods.getDestinationPath();

        if (destinationPath == null) return;

        PDFMergerUtility pdfMergerUtility = new PDFMergerUtility();
        pdfMergerUtility.setDestinationFileName(destinationPath);

        try {
            for (File file : this.filesList) pdfMergerUtility.addSource(file);

            pdfMergerUtility.mergeDocuments(MemoryUsageSetting.setupMainMemoryOnly());
        } catch (IOException ignored) {
        }
    }
}
