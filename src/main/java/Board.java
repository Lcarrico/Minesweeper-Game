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
     * The height of the grid
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
    /**
     * If first move has been made yet
     */
    private boolean started;

    private int undiscoveredBlocks;

    private int numFlags;

    /** Constructor for Minesweeper */
    public Board(int numRows, int numCols, int numMines) {
        this.numRows = numRows;
        this.numCols = numCols;
        this.numMines = numMines;
        this.started = false;
        this.undiscoveredBlocks = numRows * numCols - numMines;
        this.numFlags = 0;

        grid = new Block[numRows][numCols];
        generateBoard(numRows, numCols);
    }

    private void generateBoard(int numRows, int numCols){
        for (int i = 0; i < numRows; i++){
            for (int j = 0; j < numCols; j++){
                grid[j][i] = new Block(0, j, i);
            }
        }
        generateMines();
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
    private void generateMines() {
        int mines = numMines;
        Random random = new Random();

        //populate board with mines
        while(mines != 0){
            int x = random.nextInt(numRows);
            int y = random.nextInt(numCols);

            // make sure a mine isn't already there
            while(grid[x][y].getValue() == 100){ // and bomb location does not does mouseX and mouseY
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

    public boolean isCleared(){
        return undiscoveredBlocks == 0;
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

    public Block click(float x, float y, int blockWidth){
        int row = (int)x / blockWidth;
        int col = (int)y / blockWidth;

        if (!started){
            System.out.println(grid[row][col].getValue());
            while (grid[row][col].getValue() != 0){
                generateBoard(numRows, numCols);
                System.out.println(grid[row][col].getValue());
            }
            System.out.println("starting!");
            started = true;
        }
        if (grid[col][row].status == Block.Status.FLAG){
            return null;
        }

        if (row < numRows && row >= 0 && col < numCols && numCols >= 0){
            click(grid[col][row]);

            return grid[col][row];
        }
        return null;
    }

    public int getNumFlags() {
        return numFlags;
    }

    public Block rightClick(float x, float y, int blockWidth){
        int row = (int)x / blockWidth;
        int col = (int)y / blockWidth;

        if (row < numRows && row >= 0 && col < numCols && numCols >= 0){
            rightClick(grid[col][row]);

            return grid[col][row];
        }

        return null;
    }

    public void rightClick(Block block){
        switch (block.status){
            case BLANK -> this.numFlags++;
            case FLAG -> this.numFlags--;
        }
        block.toggleStatus();

    }

    public boolean click(Block block){
        if (block.isClicked())
            return false;

        block.click();

        if (block.isBlank()){
            revealNear(block);
        }

        if (block.isBomb()){
            reveal();
        }

        this.undiscoveredBlocks--;

        return true;
    }

    public void revealNear(Block block){
        for (int i = block.getX() - 1; i <= block.getX() + 1; i++){
            for (int j = block.getY() - 1; j <= block.getY() + 1; j++){

                if (i >= 0 && i < numRows && j >= 0 && j < numCols){
                        click(grid[i][j]);
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


    public Block[][] getGrid(){
        return this.grid.clone();
    }

    public int getNumRows() {
        return numRows;
    }

    public int getNumCols() {
        return numCols;
    }

    public int getNumMines() {
        return numMines;
    }

    public int getUndiscoveredBlocks() {
        return undiscoveredBlocks;
    }
}
