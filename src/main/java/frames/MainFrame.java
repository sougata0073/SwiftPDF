package frames;

import cards.Card;
import tools.deletePages.DeletePagesMainFrame;
import tools.extractPages.ExtractPagesMainFrame;
import tools.merge.MergePdfMainFrame;
import tools.protectPdf.ProtectPdfMainFrame;
import tools.split.SplitPdfMainFrame;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.GridLayout;

public class MainFrame extends JFrame {
    private JPanel mainPanel;

    public MainFrame() {
        setTitle("Swift PDF");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 800);
        setLocationRelativeTo(null);
        setIconImage(new ImageIcon(getClass().getResource("/images/main_icon.png")).getImage());

        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(2, 3));

        Card merge = new Card("Merge PDF", "Merge two or more PDFs", getClass().getResource("/images/merge_pdf.png"), new MergePdfMainFrame());
        Card split = new Card("Split PDF", "Split PDF into several PDFs", getClass().getResource("/images/split_pdf.png"), new SplitPdfMainFrame());
        Card extract = new Card("Extract Pages", "Extract pages from PDF", getClass().getResource("/images/extract_page.png"), new ExtractPagesMainFrame());
        Card delete = new Card("Delete Pages", "Delete pages from PDF", getClass().getResource("/images/delete_page.png"), new DeletePagesMainFrame());
        Card protect = new Card("Protect PDF", "Add password to PDF", getClass().getResource("/images/protect_pdf.png"), new ProtectPdfMainFrame());
//        Card merge3 = new Card("Merge PDF", "Merge two or more PDFs", getClass().getResource("/images/merge_pdf.png"), new MergePdfMainFrame());

        mainPanel.add(cardWithMargin(merge));
        mainPanel.add(cardWithMargin(split));
        mainPanel.add(cardWithMargin(extract));
        mainPanel.add(cardWithMargin(delete));
        mainPanel.add(cardWithMargin(protect));
//        mainPanel.add(cardWithMargin(merge3));

        add(mainPanel);
    }

    private JPanel cardWithMargin(Card card) {
        JPanel newPanel = new JPanel();
        newPanel.setLayout(new BorderLayout());
        newPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        newPanel.add(card, BorderLayout.CENTER);
        return newPanel;
    }

    public void start() {
        setVisible(true);
    }
}
