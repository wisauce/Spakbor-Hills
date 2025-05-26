package sti.oop.models;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import sti.oop.utils.Constants;

public class Asset {
  private int x,y;
  private Rectangle solidArea;
  private Image image;
  private Boolean collision;

  public Asset(int x, int y, String imageSrc) {
    this.x = x;
    this.y = y;
    image = new Image(getClass().getResourceAsStream(imageSrc));
    solidArea = new Rectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
    collision = false;
  }

  public Asset(int x, int y, String imageSrc, boolean collision) {
    this.x = x;
    this.y = y;
    image = new Image(getClass().getResourceAsStream(imageSrc));
    solidArea = new Rectangle(0, 0, Constants.TILE_SIZE, Constants.TILE_SIZE);
    this.collision = collision;
  }

  public Asset(int x, int y, String imageSrc, int w, int h) {
    this.x = x;
    this.y = y;
    image = new Image(getClass().getResourceAsStream(imageSrc));
    solidArea = new Rectangle(0,0,w,h);
    collision = false;
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
  public Rectangle getSolidArea() {
    return solidArea;
  }
  
  public Image getImage() {
    return image;
  }

  public void updateSolidArea() {
    solidArea.setX(x);
    solidArea.setY(y);
  }

  public boolean isCollisonOn() {
    return collision;
  }
}
