package maze;

import java.awt.Color;
import java.awt.Graphics;

public class Celle {

    public int row;
    public int coll;
    public int celleSize;
    public int x;
    public int y;
    Boolean[] walls = { true, true, true, true };
    public boolean visisted;
    public boolean partSolution = false;

    public Celle(int row, int coll, int celleSize) {
        this.coll = coll;
        this.row = row;
        this.celleSize = celleSize;
        this.y = this.row * this.celleSize;
        this.x = this.coll * this.celleSize;
        this.visisted = false;
    }

    public void render(Graphics g) {
        drawSquare(g);
        // g.drawRect(this.coll * this.celleSize, this.row * this.celleSize,
        // this.celleSize, this.celleSize);
    }

    public void drawSquare(Graphics g) {
        // visited
        if (this.visisted) {
            g.setColor(Color.BLACK);
            if (this.partSolution) {
                g.setColor(Color.BLUE);
            }
            g.fillRect(this.x, this.y, this.celleSize, this.celleSize);
        }
        g.setColor(Color.WHITE);
        // top
        if (this.walls[0]) {
            g.drawLine(this.x, this.y, this.x + this.celleSize, this.y);
        }
        // right
        if (this.walls[1]) {
            g.drawLine(this.x + this.celleSize, this.y, this.x + this.celleSize, this.y + this.celleSize);
        }
        // bottom
        if (this.walls[2]) {
            g.drawLine(this.x, this.y + this.celleSize, this.x + this.celleSize, this.y + this.celleSize);
        }
        // left
        if (this.walls[3]) {
            g.drawLine(this.x, this.y, this.x, this.y + this.celleSize);
        }
    }

}
