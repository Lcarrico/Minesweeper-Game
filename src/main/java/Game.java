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
	Rectangle2D.Double square;
	Ellipse2D.Double circle;
	int gravity = 6;
	int xVel = 0;
	int yVel = 0;
	
	
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
		circle = new Ellipse2D.Double(0, 0, 50, 50);
		square = new Rectangle2D.Double(30, 60, 50, 50);
		
		// Creates new 16 by 16 blank image
		BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);

		// Create a new blank cursor.
		Cursor blankCursor = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "blank cursor");

		// Set the blank cursor to the JFrame.
		getContentPane().setCursor(blankCursor);
	}
	
	boolean canMove(int xSpeed, int ySpeed) {
		Ellipse2D.Double c = new Ellipse2D.Double(circle.x + xSpeed, circle.y + ySpeed, circle.width, circle.height);
		return !c.intersects(square);
	}

	void update() {
		if (input.isKeyDown(KeyEvent.VK_D)) {
			dir = RIGHT;
		}
		
		if (dir == RIGHT && canMove(speed, 0)) {
			circle.x += speed;
		}
		
		
		if (input.isKeyDown(KeyEvent.VK_A)) {
			dir = LEFT;
		}
		
		if (dir == LEFT && canMove(-speed, 0)) {
			circle.x -= speed;
		}
		
		
		if (input.isKeyDown(KeyEvent.VK_S) && canMove(0, speed)) {
			circle.y += speed;
		}
		if (input.isKeyDown(KeyEvent.VK_W) && canMove(0, -speed)) {
			circle.y -= speed;
		}
		if (input.isKeyDown(KeyEvent.VK_ESCAPE)) {
			isRunning = false;
		}
	}

	void draw(Graphics g) {
		g = (Graphics2D) g;
		g.setColor(Color.BLACK);
		g.fillRect(0, 0, windowWidth, windowHeight);

		g.setColor(Color.WHITE);
		g.drawOval((int) circle.x, (int) circle.y, (int) circle.width, (int) circle.height);
		g.fillRect((int) square.x, (int) square.y, (int) square.width, (int) square.height);

	}

}