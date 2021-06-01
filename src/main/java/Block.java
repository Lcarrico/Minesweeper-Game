import java.awt.*;
import java.awt.geom.Rectangle2D;

public class Block {

    private int value;
    private boolean clicked;
    int x;
    int y;

    public boolean isBlank(){
        return value == 0;
    }

    public boolean isBomb(){
        return value >= 100;
    }

    public Block(int value, int x, int y){
        this.value = value;
        this.clicked = false;
        this.x = x;
        this.y = y;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public int getValue() {
        return value;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean clicked) {
        this.clicked = clicked;
    }

    public void click(){
        this.clicked = true;
    }

    public void draw(Graphics2D g, Rectangle2D.Double rect){

        g.setColor(Color.WHITE);
        g.draw(rect);

        String display;

        if (getValue() >= 100) {
            g.setColor(Color.RED);
            display = "B";
        } else if (getValue() == 0){
            display = " ";
        } else{
            g.setColor(Color.WHITE);
            display = String.valueOf(getValue());
        }

        g.drawString(display, (float)rect.getX() +  (float)rect.getWidth()/3, (float)rect.getY() + (float)rect.height - (float)rect.getHeight()/3);

        if (!isClicked()){
            g.setColor(Color.DARK_GRAY);

            rect.width -= 4;
            rect.height -= 4;
            rect.x++;
            rect.y++;

            g.fill(rect);
        }
    }

    @Override
    public String toString() {
        return "Block{" +
                "value=" + value +
                ", clicked=" + clicked +
                '}';
    }
}
