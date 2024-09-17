package utility;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;
import java.awt.Font;
import java.io.File;
import java.util.HashSet;
import java.util.Iterator;

public class UtilityMethods {

    public UtilityMethods() {

    }

    public static File selectFile() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setPreferredSize(new Dimension(500, 500));

        FileNameExtensionFilter pdfFilter = new FileNameExtensionFilter("PDF Files", "pdf");
        fileChooser.setFileFilter(pdfFilter);

        int response = fileChooser.showOpenDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            return fileChooser.getSelectedFile();
        }
        return null;
    }

    public static String getDestinationPath(){
        String destinationPath;

        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setPreferredSize(new Dimension(500, 500));
        fileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

        int response = fileChooser.showSaveDialog(null);

        if (response == JFileChooser.APPROVE_OPTION) {
            File selectedDirectory = fileChooser.getSelectedFile();

            JTextField pdfNameTf = new JTextField();
            pdfNameTf.setFont(new Font("Calibri", Font.PLAIN, 18));

            int option = JOptionPane.showConfirmDialog(null, pdfNameTf, "Enter the merged PDF name", JOptionPane.OK_CANCEL_OPTION);

            if (option == JOptionPane.OK_OPTION) {
                destinationPath = selectedDirectory.getAbsolutePath().replace("\\", "\\\\") + "\\\\" + pdfNameTf.getText() + ".pdf";
            } else {
                return null;
            }
        } else {
            return null;
        }

        return destinationPath;
    }

    public static int[] getPageNumbers(String s) throws WrongInputException, NumberFormatException {
        String[] numStringArr = s.split("\\s*,\\s*");
        HashSet<Integer> tempList = new HashSet<>();
        for (String str : numStringArr) {
            if (str.contains("-")) {
                String[] tempArr = str.split("\\s*-\\s*");

                if (tempArr.length != 2) throw new WrongInputException();

                int a = Integer.parseInt(tempArr[0]);
                int b = Integer.parseInt(tempArr[1]);

                if (a > b) throw new WrongInputException();

                for (int i = a; i <= b; i++) tempList.add(i);

            } else {
                tempList.add(Integer.parseInt(str));
            }
        }

        int[] numArr = new int[tempList.size()];

        Iterator<Integer> iterator = tempList.iterator();

        int i = 0;

        while (iterator.hasNext()) {
            numArr[i] = iterator.next() - 1;
            i++;
        }

        return numArr;
    }

    public static void showNoFileSelected(JFrame parent) {
        JOptionPane.showMessageDialog(parent, "Select a PDF first", "PDF not selected", JOptionPane.ERROR_MESSAGE);
    }
}
