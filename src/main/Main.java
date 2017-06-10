package main;

import br.ol.frogger.FroggerScene;
import br.ol.ge.core.Display;
import br.ol.ge.core.Scene;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

/**
 * Main class.
 * 
 * @author Leonardo Ono (ono.leo@gmail.com)
 */
public class Main {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Scene scene = new FroggerScene();
                Display display = new Display(scene);
                JFrame frame = new JFrame();
                frame.setTitle("Frogger");
                frame.getContentPane().add(display);
                frame.pack();
                frame.setLocationRelativeTo(null);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setResizable(false);
                frame.setVisible(true);
                display.start();
                display.requestFocus();
            }
        });
    }    
    
}
