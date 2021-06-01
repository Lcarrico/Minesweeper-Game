import java.awt.*;
import java.awt.geom.Rectangle2D;
import java.util.ArrayList;
import java.util.Random;

/**
 * Minesweeper Command line game
 * @author Brooskiey
 * @version 0.0
 * @date 11/15/2020
 */
public class Board {

    /** The length of the grid */
    private final int numRows;
    /**
     * The height of t he grid
     */
    private final int numCols;
    /**
     * Number of mines on the grid
     */
    private final int numMines;
    /**
     * The entire grid
     */
    private final Block[][] grid;

    /** Constructor for Minesweeper */
    public Board(int numRows, int numCols, int numMines) {
        this.numRows = numRows;
        this.numCols = numCols;
        grid = new Block[numRows][numCols];

        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                grid[j][i] = new Block(0, j, i);
            }
        }

        this.numMines = numMines;
        make_grid();
    }

    /** Make the grid and put the mines of the grid. A value of 100 will signify a mine.
     *  ____________________________________________
     *  |  (x-1)(y-1)  |  (x)(y-1)  |  (x+1)(y-1)  |
     *  --------------------------------------------
     *  |   (x-1)(y)   |    (xy)    |   (x+1)(y)   |
     *  --------------------------------------------
     *  |  (x-1)(y+1)  |  (x)(y+1)  |  (x+1)(y+1)  |
     *  --------------------------------------------
     */
    private void make_grid() {
        int mines = numMines;
        Random random = new Random();

        //populate board with mines
        while(mines != 0){
            int x = random.nextInt(numRows);
            int y = random.nextInt(numCols);

            // make sure a mine isn't already there
            while(grid[x][y].getValue() == 100){
                x = random.nextInt(numRows);
                y = random.nextInt(numCols);
            }


            // cover top left corner case
            if (x == 0 && y == 0) {
                grid[x][y + 1].setValue(grid[x][y + 1].getValue() + 1); //south
                grid[x + 1][y].setValue(grid[x + 1][y].getValue() + 1); //east
                grid[x + 1][y + 1].setValue(grid[x + 1][y + 1].getValue() + 1); //southeast

                // cover bottom right corner case
            } else if (x == numRows - 1 && y == 0) {
                grid[x][y + 1].setValue(grid[x][y + 1].getValue() + 1); //south
                grid[x - 1][y].setValue(grid[x - 1][y].getValue() + 1); //west
                grid[x - 1][y + 1].setValue(grid[x - 1][y + 1].getValue() + 1); //southwest

                // cover top left corner case
            } else if (x == 0 && y == numCols - 1) {
                grid[x][y - 1].setValue(grid[x][y - 1].getValue() + 1); //north
                grid[x + 1][y].setValue(grid[x + 1][y].getValue() + 1); //east
                grid[x + 1][y - 1].setValue(grid[x + 1][y - 1].getValue() + 1); //northeast

                // cover bottom right corner case
            } else if (x == numRows - 1 && y == numCols - 1) {
                grid[x][y - 1].setValue(grid[x][y - 1].getValue() + 1); //north
                grid[x - 1][y].setValue(grid[x - 1][y].getValue() + 1);  //west
                grid[x - 1][y - 1].setValue(grid[x - 1][y - 1].getValue() + 1); //northwest

                // cover left column case
            } else if (x == 0 && y < numCols - 1) {
                grid[x][y - 1].setValue(grid[x][y - 1].getValue() + 1); //north
                grid[x][y + 1].setValue(grid[x][y + 1].getValue() + 1); //south
                grid[x + 1][y].setValue(grid[x + 1][y].getValue() + 1); //east
                grid[x + 1][y - 1].setValue(grid[x + 1][y - 1].getValue() + 1); //northeast
                grid[x + 1][y + 1].setValue(grid[x + 1][y + 1].getValue() + 1); //southeast

                // cover top row case
            } else if (y == 0 && x < numRows - 1) {
                grid[x][y + 1].setValue(grid[x][y + 1].getValue() + 1); //south
                grid[x + 1][y].setValue(grid[x + 1][y].getValue() + 1); //east
                grid[x - 1][y].setValue(grid[x - 1][y].getValue() + 1); //west
                grid[x - 1][y + 1].setValue(grid[x - 1][y + 1].getValue() + 1); //southwest
                grid[x + 1][y + 1].setValue(grid[x + 1][y + 1].getValue() + 1); //southeast

                // cover bottom row case
            } else if (y == numCols - 1 && x > 0 && x < numRows - 1) {
                grid[x][y - 1].setValue(grid[x][y - 1].getValue() + 1); //north
                grid[x + 1][y].setValue(grid[x + 1][y].getValue() + 1); //east
                grid[x - 1][y].setValue(grid[x - 1][y].getValue() + 1); //west
                grid[x + 1][y - 1].setValue(grid[x + 1][y - 1].getValue() + 1); //northeast
                grid[x - 1][y - 1].setValue(grid[x - 1][y - 1].getValue() + 1); //northwest

                // cover right column case
                // stop before y == 0
            } else if (x == numRows - 1 && y > 0 && y < numCols - 1) {
                grid[x][y - 1].setValue(grid[x][y - 1].getValue() + 1); //north
                grid[x][y + 1].setValue(grid[x][y + 1].getValue() + 1); //south
                grid[x - 1][y].setValue(grid[x - 1][y].getValue() + 1); //west
                grid[x - 1][y - 1].setValue(grid[x - 1][y - 1].getValue() + 1); //northwest
                grid[x - 1][y + 1].setValue(grid[x - 1][y + 1].getValue() + 1); //southwest

                // middle cases
            } else {
                grid[x][y - 1].setValue(grid[x][y - 1].getValue() + 1); //north
                grid[x][y + 1].setValue(grid[x][y + 1].getValue() + 1); //south
                grid[x + 1][y].setValue(grid[x + 1][y].getValue() + 1); //east
                grid[x - 1][y].setValue(grid[x - 1][y].getValue() + 1); //west
                grid[x + 1][y - 1].setValue(grid[x + 1][y - 1].getValue() + 1); //northeast
                grid[x - 1][y - 1].setValue(grid[x - 1][y - 1].getValue() + 1); //northwest
                grid[x - 1][y + 1].setValue(grid[x - 1][y + 1].getValue() + 1); //southwest
                grid[x + 1][y + 1].setValue(grid[x + 1][y + 1].getValue() + 1); //southeast
            }
            mines--;
            grid[x][y].setValue(100);
        }
    }

    public void printGrid() {
        int x = 0;
        int y = 0;
        String row = "";
        System.out.println("----------------------------------------");
        while (x < numRows) {
            while (y < numCols) {
                row += "|\t" + grid[x][y].getValue() + "\t";
                y++;
            }
            System.out.println(row + "|");
            System.out.println("----------------------------------------");
            row = "";
            x++;
            y = 0;
        }
    }

    public void draw(Graphics2D g, int blockWidth){

        g.setFont(new Font("TimesRoman", Font.PLAIN, 32));

        Rectangle2D.Double tempBlock;
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){

                tempBlock = new Rectangle2D.Double(j * blockWidth, i*blockWidth, blockWidth, blockWidth);
                grid[i][j].draw(g, tempBlock);

            }
        }
    }

    public void click(float x, float y, int blockWidth){
        int row = (int)x / blockWidth;
        int col = (int)y / blockWidth;

        if (row < numRows && row >= 0 && col < numCols && numCols >= 0){
            click(grid[col][row]);
        }


    }


    public void click(Block block){
        if (block.isClicked())
            return;

        block.click();

        if (block.isBlank()){
            revealNear(block);
        }

        if (block.isBomb()){
            reveal();
        }


    }

    public void revealNear(Block block){
        for (int i = block.getX() - 1; i <= block.getX() + 1; i++){
            for (int j = block.getY() - 1; j <= block.getY() + 1; j++){

                if (i > 0 && i < numRows && j > 0 && j < numCols){
                    grid[i][j].click();
                }

            }
        }

    }

    public void reveal(){
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                grid[i][j].click();
            }
        }
    }


    /**
     * Main method for the Minesweeper game
     * @param args command line arguments
     */
    public static void main(String[] args){
        Random rand = new Random();
        int randBombs = 1 + rand.nextInt(4);
        Board board = new Board(5, 5, randBombs);

        board.make_grid();
        board.printGrid();
    }

    public Block[][] getGrid(){
        return this.grid.clone();
    }


}