package cards;

import utility.Fonts;

import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import java.awt.Color;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Image;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.net.URL;

public class Card extends JPanel {
    private String title;
    private String desc;
    private URL iconPath;
    private JFrame frameToBeOpened;

    public Card(String title, String desc, URL iconPath, JFrame frameToBeOpened){

        this.title = title;
        this.desc = desc;
        this.iconPath = iconPath;
        this.frameToBeOpened = frameToBeOpened;

        setLayout(new GridBagLayout());
        setBorder(BorderFactory.createLineBorder(Color.WHITE));

        ImageIcon icon = createScaledIcon(this.iconPath);
        JLabel iconLabel = new JLabel(icon);
        iconLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel titleLabel = new JLabel(this.title);
        titleLabel.setFont(Fonts.font1());
        titleLabel.setHorizontalAlignment(SwingConstants.CENTER);

        JLabel descLabel = new JLabel(this.desc);
        descLabel.setFont(Fonts.font2());
        descLabel.setHorizontalAlignment(SwingConstants.CENTER);

        MouseListener mouseListener = getMouseListener();

        JButton button = new JButton("Open");
        button.setFont(Fonts.font1());
        button.setHorizontalAlignment(SwingConstants.CENTER);
        button.addActionListener(e -> this.frameToBeOpened.setVisible(true));
        button.addMouseListener(mouseListener);

        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(10, 0, 10, 0);

        gbc.gridx = 0;
        gbc.gridy = 0;
        add(iconLabel, gbc);
        gbc.gridy = 1;
        add(titleLabel, gbc);
        gbc.gridy = 2;
        add(descLabel, gbc);
        gbc.gridy = 3;
        add(button, gbc);

        addMouseListener(mouseListener);
    }
    private ImageIcon createScaledIcon(URL imagePath) {
        ImageIcon imageIcon = new ImageIcon(imagePath);
        Image image = imageIcon.getImage();
        Image scaledImage = image.getScaledInstance(100, 100, Image.SCALE_SMOOTH);
        return new ImageIcon(scaledImage);
    }
    private MouseListener getMouseListener(){
        Color defaultBgColor = getBackground();

        return new MouseListener() {
            @Override
            public void mouseClicked(MouseEvent e) {

            }

            @Override
            public void mousePressed(MouseEvent e) {

            }

            @Override
            public void mouseReleased(MouseEvent e) {

            }

            @Override
            public void mouseEntered(MouseEvent e) {
                setBackground(defaultBgColor.darker());
                setBounds(getX() - 2, getY() - 2, getWidth() + 4, getHeight() + 4);
            }

            @Override
            public void mouseExited(MouseEvent e) {
                setBackground(defaultBgColor);
                setBounds(getX() + 2, getY() + 2, getWidth() - 4, getHeight() - 4);
            }
        };
    }
}
