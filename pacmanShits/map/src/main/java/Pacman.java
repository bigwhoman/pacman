import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Circle;

public class Pacman extends Circle {
    private int xPosition;
    private int yPosition;
    private int lives;
    private int radius;
    private boolean attackble;
    private Cell[][] mm;

    public Pacman(int radius, int startingX, int startingY, int lives, String url) {
        super(radius);
        attackble = true;
        this.xPosition = startingX;
        this.yPosition = startingY;
        this.lives = lives;
        ImagePattern pattern = new ImagePattern
                (new Image(url), 20, 20, 40, 40, false);
        this.setFill(pattern);
    }

    public void moveDown() {
        if (mm[xPosition + 1][yPosition].getType().equals("1")) {
            return;
        }
        this.setTranslateY(this.getTranslateY() + 50);
        xPosition += 1;
    }

    public void moveUp() {
        if (mm[xPosition - 1][yPosition].getType().equals("1")) {
            return;
        }
        this.setTranslateY(this.getTranslateY() - 50);
        xPosition -= 1;
    }

    public void moveLeft() {
        if (mm[xPosition][yPosition - 1].getType().equals("1")) {
            return;
        }
        this.setTranslateX(this.getTranslateX() - 50);
        yPosition -= 1;
    }

    public void moveRight() {
        if (mm[xPosition][yPosition + 1].getType().equals("1")) {
            return;
        }
        this.setTranslateX(this.getTranslateX() + 50);
        yPosition += 1;
    }

    public boolean isAttackble() {
        return attackble;
    }

    public void setAttackble(boolean attackble) {
        this.attackble = attackble;
    }

    public int getXPosition() {
        return xPosition;
    }

    public int getYPosition() {
        return yPosition;
    }

    public Cell[][] getMm() {
        return mm;
    }
    public void setMap(Cell[][] mm) {
        this.mm = mm;
    }
}
