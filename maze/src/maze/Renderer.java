package maze;

import java.awt.Graphics;

import javax.swing.JPanel;

/**
 * Renderer
 */
public class Renderer extends JPanel {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Override
    protected void paintComponent(Graphics g) {
        // TODO Auto-generated method stub
        super.paintComponent(g);

        Grid.grid.render(g);
    }

}