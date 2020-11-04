package langton_ant.model;

import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;

public class Cell {
    boolean isActive;
    Rectangle rec;
    Circle ant;

    public Cell() {
        isActive = false;
        rec = new Rectangle();
        ant = new Circle();
    }

    public void draw(double posX, double posY, double width, double height){

        ant = new Circle((posX + posX + width) / 2, (posY + posY + height) / 2, width / 4);
        rec = new Rectangle(posX, posY, width, height);
        ant.setFill(Color.WHITE);
        setColor(Color.WHITE);
    }

    public void setColor(Color color) {
        rec.setFill(color);
        ant.setFill(color);
    }

    Circle getAnt() {
        return ant;
    }

    Rectangle getRect() {
        return rec;
    }

    public void activate() {
        isActive = true;
        setColor(Color.GRAY);
    }

    public void deactivate() {
        isActive = false;
        setColor(Color.WHITE);
    }
    public void setAnt(boolean antcon){
        if (antcon){
            ant.setFill(Color.BLACK);
        } else {
            ant.setFill(rec.getFill());
        }
    }

    boolean isClickedOn(double x, double y) {
        double xstart = rec.getX();
        double xEnd = rec.getX() + rec.getWidth();
        double yStart = rec.getY();
        double yEnd = rec.getY() + rec.getHeight();
        if ((xstart <= x && x < xEnd) && (yStart <= y && y < yEnd)) {
            return true;
        } else {
            return false;
        }
    }
}
