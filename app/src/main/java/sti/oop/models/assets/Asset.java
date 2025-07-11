package sti.oop.models.assets;

import javafx.scene.image.Image;
import javafx.scene.shape.Rectangle;
import sti.oop.utils.Constants;

public class Asset {
  private int x,y;
  private Rectangle solidArea;
  private Image image;
  private boolean collision;

  public Asset(int x, int y, int w, int h, boolean collision) {
    this.x = x;
    this.y = y;
    solidArea = new Rectangle(x, y, w, h);
    this.collision = collision;
  }

  public Asset(int x, int y, boolean collision) {
    this.x = x;
    this.y = y;
    solidArea = new Rectangle(x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);
    this.collision = collision;
  }
  public Asset(int x, int y, String imageSrc, boolean collision) {
    this.x = x;
    this.y = y;
    image = new Image(getClass().getResourceAsStream(imageSrc));
    solidArea = new Rectangle(x, y, Constants.TILE_SIZE * 3, Constants.TILE_SIZE * 3);
    this.collision = collision;
  }

  public Asset(int x, int y, int w, int h, String imageSrc, boolean collision) {
    this.x = x;
    this.y = y;
    image = new Image(getClass().getResourceAsStream(imageSrc));
    solidArea = new Rectangle(x,y,w,h);
    this.collision = collision;
  }

  public Asset(int x, int y, String imageSrc, int w, int h, boolean collision) {
    this.x = x;
    this.y = y;
    image = new Image(getClass().getResourceAsStream(imageSrc));
    solidArea = new Rectangle(x,y,w,h);
    this.collision = collision;
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

  public boolean isCollisionOn() {
    return collision;
  }

  public void setImage(Image image) {
    this.image = image;
  }
  
}
