package maze;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.Random;
import java.util.Stack;

import javax.swing.JFrame;
import javax.swing.Timer;

public class Grid implements ActionListener {

    public static Grid grid;
    private int size;
    private Renderer renderer;
    ArrayList<Celle> cells;
    Stack<Celle> stack;
    // the answer
    Stack<?> solution;
    Celle currentCelle;
    private boolean first = true;
    private boolean finnished = false;
    private int startX;
    private int startY;
    private int sqrt;

    public Grid(int numCells, int celleSize, int startX, int startY) {
        Timer timer = new Timer(5, this);
        this.startX = startX;
        this.startY = startY;
        this.size = numCells * celleSize;
        this.cells = new ArrayList<>();
        this.stack = new Stack<>();
        for (int i = 0; i < numCells; i++) {
            for (int j = 0; j < numCells; j++) {
                cells.add(new Celle(i, j, celleSize));
            }
        }
        this.currentCelle = cells.get(getStartIndex(startX, startY));
        this.currentCelle.visisted = true;
        this.sqrt = (int) Math.sqrt(cells.size());
        JFrame jframe = new JFrame();
        renderer = new Renderer();
        jframe.add(renderer);
        jframe.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jframe.setSize(this.size + 1, this.size + 23);
        jframe.setLocationRelativeTo(null);
        jframe.setVisible(true);
        timer.start();

    }

    public void update() {
        makeMaze();
    }

    public void render(Graphics g) {
        g.setColor(Color.GRAY);
        g.fillRect(0, 0, this.size, this.size);
        // shows the answer
        if (!first && finnished) {
            g.setColor(Color.red);
            for (Celle c : (Stack<Celle>) solution) {
                c.partSolution = true;
            }
        }
        this.cells.forEach(p -> p.render(g));
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // TODO Auto-generated method stub
        update();
        renderer.repaint();

    }

    public int getStartIndex(int x, int y) {
        return getIndex(x, y);
    }

    public int getIndex(int x, int y) {
        int num = (int) Math.sqrt(this.cells.size());
        if ((x + y * num) >= this.cells.size() || (x + y * num) < 0) {
        }
        return x + y * num;
    }

    public Celle checkNeighburs() {
        ArrayList<Celle> neighburs = new ArrayList<>();
        Celle top;
        Celle right;
        Celle bottom;
        Celle left;
        int x = this.currentCelle.coll;
        int y = this.currentCelle.row;

        if (getIndex(x, y - 1) >= this.cells.size() || getIndex(x, y - 1) < 0) {
            top = null;
        } else {
            top = this.cells.get(getIndex(x, y - 1));
        }
        if (getIndex(x + 1, y) >= this.cells.size() || getIndex(x + 1, y) < 0
                || this.currentCelle.coll == (int) Math.sqrt(this.cells.size()) - 1) {
            right = null;
        } else {
            right = this.cells.get(getIndex(x + 1, y));
        }
        if (getIndex(x, y + 1) >= this.cells.size() || getIndex(x, y + 1) < 0) {
            bottom = null;
        } else {
            bottom = this.cells.get(getIndex(x, y + 1));
        }
        if (getIndex(x - 1, y) >= this.cells.size() || getIndex(x - 1, y) < 0 || this.currentCelle.coll == 0) {
            left = null;
        } else {
            left = this.cells.get(getIndex(x - 1, y));
        }

        if (top != null && !top.visisted) {
            neighburs.add(top);
        }
        if (right != null && !right.visisted) {
            neighburs.add(right);
        }
        if (bottom != null && !bottom.visisted) {
            neighburs.add(bottom);
        }
        if (left != null && !left.visisted) {
            neighburs.add(left);
        }

        if (neighburs.size() > 0) {
            Random random = new Random();
            Celle nextCelle = neighburs.get(random.nextInt(neighburs.size()));
            nextCelle.visisted = true;
            return nextCelle;
        }
        return null;

    }

    public void removeWall(Celle nextCelle) {
        switch (this.currentCelle.coll - nextCelle.coll) {
            case -1:
                this.currentCelle.walls[1] = false;
                nextCelle.walls[3] = false;
                break;
            case 1:
                this.currentCelle.walls[3] = false;
                nextCelle.walls[1] = false;
                break;
        }
        switch (this.currentCelle.row - nextCelle.row) {
            case -1:
                this.currentCelle.walls[2] = false;
                nextCelle.walls[0] = false;
                break;
            case 1:
                this.currentCelle.walls[0] = false;
                nextCelle.walls[2] = false;
                break;
        }
    }

    public void makeMaze() {
        Celle nextCelle = checkNeighburs();
        if (nextCelle != null) {
            nextCelle.visisted = true;
            stack.push(this.currentCelle);
            // saves the answer
            removeWall(nextCelle);
            if (first && (this.currentCelle.coll == (sqrt - 1) && this.currentCelle.row == (sqrt - 1))) {
                this.first = false;
                this.solution = (Stack<?>) stack.clone();
                System.out.println("jepp");
            }
            this.currentCelle = nextCelle;
        } else {
            if (first && (this.currentCelle.coll == (sqrt - 1) && this.currentCelle.row == (sqrt - 1))) {
                this.first = false;
                this.solution = (Stack<?>) stack.clone();
            }
            if (!stack.empty()) {
                this.currentCelle = this.stack.pop();
            }
        }
        // shows the answer
        if (this.currentCelle.coll == startX && this.currentCelle.row == startY) {
            finnished = true;
        }
    }
    // Grid(a,b,c,d) a = number of cells, b = the size of the cells, c = the start x-pos, d = the start y-pos
    // end pos is always lower right corner
    public static void main(String[] args) {
        grid = new Grid(60, 12, 0, 0);
    }

}
