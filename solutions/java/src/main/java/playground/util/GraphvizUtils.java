package playground.util;

import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;

import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;

public class GraphvizUtils {

    public static JFrame printerJFrame(String graphStr) {
        JFrame editorFrame = new JFrame("<<Tree>>");
        editorFrame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        BufferedImage image = Graphviz.fromString(graphStr).height(800).width(800).render(Format.PNG).toImage();
        ImageIcon imageIcon = new ImageIcon(image);
        JLabel jLabel = new JLabel();
        jLabel.setIcon(imageIcon);
        editorFrame.getContentPane().add(jLabel, BorderLayout.CENTER);

        editorFrame.pack();
        editorFrame.setLocationRelativeTo(null);
        editorFrame.setVisible(true);
        return editorFrame;
    }

    public static void show(String graphStr) {
        JFrame editorFrame = printerJFrame(graphStr);
        while (editorFrame.isShowing()) {
            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

}
