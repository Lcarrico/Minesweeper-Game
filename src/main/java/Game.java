import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.Toolkit;
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

	int boardWidth;
	int boardHeight;
	int numBombs;

	int blockWidth = 50;
	Board board;
	
	//ArrayList<Rectangle2D.Double> circles = new ArrayList<Rectangle2D.Double>();

	public static void main(String[] args) {

		Game g = new Game();
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		windowWidth = (int) screenSize.getWidth();
		windowHeight = (int) screenSize.getHeight();
		g.setExtendedState(MAXIMIZED_BOTH);
		g.setUndecorated(true);
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

		boardWidth = 20;
		boardHeight = 20;
		numBombs = 30;

		board = new Board(boardWidth, boardHeight, numBombs);

	}


	void update() {

		if (input.isKeyDown(KeyEvent.VK_ESCAPE)){
			System.exit(0);
		}

		// If R is pressed, Then Restart
		if (input.isKeyDown(KeyEvent.VK_R)){
			restart();
		}

		if (input.wasClicked()){
			float mouseX = input.MouseX();
			float mouseY = input.MouseY();

			board.click(mouseX, mouseY, blockWidth);

			input.resetClicks();
		}

	}

	void draw(Graphics g) {
		g = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowWidth, windowHeight);

		g.setColor(Color.WHITE);

		board.printGrid();
		board.draw((Graphics2D) g, blockWidth);
	}

	void restart(){
		init();
	}

}