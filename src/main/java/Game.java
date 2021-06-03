import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Rectangle2D;
import java.awt.image.BufferedImage;
import java.util.ArrayList;

public class Game extends GameEngine {

	static final int UP = 0;
	static final int RIGHT = 1;
	static final int DOWN = 2;
	static final int LEFT = 3;
	int size = 20;
	int dir = RIGHT;
	int speed = 5;
	int gravity = 6;
	int xVel = 0;
	int yVel = 0;
	int translateBoardX = 0;
	int translateBoardY = 0;

	int boardWidth;
	int boardHeight;
	int numBombs;

	int blockWidth = 50;
	Board board;

	boolean gameOver;
	boolean started;

	public static void main(String[] args) {
		Game g = new Game();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
//		windowWidth = (int) screenSize.getWidth();
//		windowHeight = (int) screenSize.getHeight();

		windowWidth = 1200;
		windowHeight = 800;

//		g.setExtendedState(MAXIMIZED_BOTH);
//		g.setUndecorated(true);
		g.setVisible(true);
		g.init();
		g.run();
		System.exit(0);
	}

	void init() {
//		// Creates new 16 by 16 blank image
//		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
//
//		// Create a new blank cursor.
//		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");
//
//		// Set the blank cursor to the JFrame.
//		getContentPane().setCursor(blankCursor);

		translateBoardX = 120;
		translateBoardY = 180;

		boardWidth = 10;
		boardHeight = 10;
		numBombs = 5;

		board = new Board(boardWidth, boardHeight, numBombs);
		gameOver = false;

		started = false;
	}


	void update() {
		if (input.isKeyDown(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}

		// If R is pressed, Then Restart
		if (input.isKeyDown(KeyEvent.VK_R)){
			restart();
		}

		if (gameOver || board.isCleared()){
			return;
		}

		if (input.wasClicked()){

			float mouseX = input.MouseX() - insets.left - translateBoardX;
			float mouseY = input.MouseY() - insets.top - translateBoardY;

			if (input.wasLeftClick()){
				Block clickedBlock = board.click(mouseX, mouseY, blockWidth);

				if (clickedBlock != null && clickedBlock.isBomb()){
					gameOver = true;

				}
			}

			if (input.wasRightClick()){
				System.out.println("Right was clicked.");
				board.rightClick(mouseX, mouseY, blockWidth);
			}
			started = true;
			input.resetClicks();
		}

	}

	void draw(Graphics g) {
		g = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowWidth, windowHeight);



//		board.printGrid();
		g.setColor(Color.WHITE);
		g.setFont(new Font("TimesRoman", Font.PLAIN, 128));
		g.drawString("Minesweeper", windowWidth*8/32, windowHeight*5/32);

		g.translate(translateBoardX, translateBoardY);
		board.draw((Graphics2D) g, blockWidth);
		g.translate(-translateBoardX, -translateBoardY);

		if (board.isCleared() && !gameOver){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 84));
			g.setColor(Color.WHITE);
			g.drawString("Game Won!", windowWidth*18/32, windowHeight*18/32);

		}
		else if (gameOver){
			g.setFont(new Font("TimesRoman", Font.PLAIN, 84));
			g.setColor(Color.WHITE);
			g.drawString("Game Over", windowWidth*18/32, windowHeight*18/32);

		}
		else{
			g.setColor(Color.LIGHT_GRAY);
			g.setFont(new Font("TimesRoman", Font.PLAIN, 32));
			g.drawString("Number of remaining Mines: " + String.valueOf(board.getNumMines() - board.getNumFlags()), windowWidth*18/32, windowHeight*18/32);
		}
	}

	void restart(){
		init();
	}

}