package org.example;

import frames.MainFrame;

import javax.swing.UIManager;

public class App
{
    public static void main( String[] args )
    {
        try {
            UIManager.setLookAndFeel("com.formdev.flatlaf.themes.FlatMacDarkLaf");
        } catch (Exception ignored) {}

        MainFrame mainFrame = new MainFrame();
        mainFrame.start();
    }
}
