import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Ghost extends Circle {
    private int xPosition;
    private int yPosition;

    public Ghost(int radius, int xPosition, int yPosition, String url) {
        super(radius);
        this.xPosition = xPosition;
        this.yPosition = yPosition;
        ImagePattern pattern = new ImagePattern
                (new Image(url), 20, 20, 40, 40, false);
        this.setFill(pattern);
    }

    public void moveGhosts(Cell[][] mm) throws InterruptedException {
        int move = Maze.getMaze().checkMove(xPosition, yPosition, mm);
        if (move == 1) {


            if (mm[xPosition][yPosition - 1].getType().equals("1")) {
                return;
            }
            this.setTranslateX(this.getTranslateX() - 50);
            yPosition -= 1;
        }
        if (move == 2) {

            if (mm[xPosition - 1][yPosition].getType().equals("1")) {
                return;
            }
            this.setTranslateY(this.getTranslateY() - 50);
            xPosition -= 1;
        }
        if (move == 3) {
            if (mm[xPosition][yPosition + 1].getType().equals("1")) {
                return;
            }
            this.setTranslateX(this.getTranslateX() + 50);
            yPosition += 1;
        }
        if (move == 4) {
            if (mm[xPosition + 1][yPosition].getType().equals("1")) {
                return;
            }
            this.setTranslateY(this.getTranslateY() + 50);
            xPosition += 1;
        }

    }

    public int getxPosition() {
        return xPosition;
    }

    public void setxPosition(int xPosition) {
        this.xPosition = xPosition;
    }

    public int getyPosition() {
        return yPosition;
    }

    public void setyPosition(int yPosition) {
        this.yPosition = yPosition;
    }
}
