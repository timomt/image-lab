package org.timomt;

import com.formdev.flatlaf.FlatLightLaf;
import com.formdev.flatlaf.themes.FlatMacDarkLaf;

public class Main {
    public static void main(String[] args) {
        FlatLightLaf.setup(new FlatMacDarkLaf());
        new ImageLabGUI();
    }
}