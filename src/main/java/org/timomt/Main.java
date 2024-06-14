package org.timomt;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

/**
 * Main:
 * entry to this application.
 */
public abstract class Main {
    /**
     * main:
     * default constructor.
     * @param args unused.
     */
    public static void main(String[] args) {
        FlatLightLaf.setup(new FlatMacDarkLaf());
        new ImageLabGUI();
    }
}