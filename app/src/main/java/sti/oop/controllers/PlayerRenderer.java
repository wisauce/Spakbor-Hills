package sti.oop.controllers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sti.oop.models.Constants;
import sti.oop.models.Player;

public class PlayerRenderer {
  private GraphicsContext gc;
  private int spriteCounter = 0;
  
  private int idleCounter = 0;
  private boolean isIdle = true;
  
  private final int screenX = Constants.SCREEN_WIDTH/ 2 - Constants.TILE_SIZE/2;
  private final int screenY = Constants.SCREEN_HEIGHT/ 2 - Constants.TILE_SIZE/2;
  private final int playerFrameWidth = 256;
  private final int playerFrameHeight = 256;
  private Image playerSpriteSheet;

  private final int hitboxOffsetX = 11*4;
  private final int hitboxOffsetY = 23*4;
  private final int hitboxWidth = 10*4;
  private final int hitboxHeight = 9*4;
  // private final int hitboxTopLeftX = hitboxOffsetX;
  // private final int hitboxTopLeftY = hitboxOffsetY;
  // private final int hitboxTopRightX = hitboxOffsetX + hitboxWidth;
  // private final int hitboxTopRightY = hitboxOffsetY;
  // private final int hitboxDownLeftX = hitboxOffsetX;
  // private final int hitboxDownLeftY = hitboxOffsetY + hitboxHeight;
  // private final int hitboxDownRightX = hitboxOffsetX + hitboxWidth;
  // private final int hitboxDownRightY = hitboxOffsetY + hitboxHeight;

  /* Attributes for FPS cap */
  private double animationTimeAccumulator = 0;
  private final double HORIZONTAL_FRAME_DURATION = 0.30;
  private final double VERTICAL_FRAME_DURATION = 0.15;
  
  private int frameX = 0;
  private int frameY = 0;

  private boolean wasDiagonal = false;
  
  public void setFrameX(int frameX) {
    this.frameX = frameX;
  }

  public PlayerRenderer(GraphicsContext gc) {
    this.gc = gc;
    playerSpriteSheet = new Image(getClass().getResourceAsStream("/sprites/new.png"));
  }
  
  public int sourceX() {
    return frameX * playerFrameWidth;
  }
  
  public int sourceY() {
    return frameY * playerFrameHeight;
  }
  
  public void playerMovementHandler(Player player, boolean keyLeftPressed, boolean keyRightPressed, boolean keyUpPressed, boolean keyDownPressed, boolean keyUporDownJustPressed, double diffTime) {

    /* Attributes for switching vertical to diagonal */
    boolean wasVerticalOnly = !isIdle && !wasDiagonal && (frameX == 2 || frameX == 3);
    boolean isHorizontal = keyLeftPressed || keyRightPressed;
    boolean isVertical = keyUpPressed || keyDownPressed;
    boolean isDiagonal = isHorizontal && isVertical;
    boolean wasDiagonal = isDiagonal;
    
    if (keyLeftPressed || keyRightPressed || keyUpPressed || keyDownPressed) {
        animationTimeAccumulator += diffTime;
        
        double typeFrameDuration = (keyLeftPressed || keyRightPressed) ? HORIZONTAL_FRAME_DURATION : VERTICAL_FRAME_DURATION;

        if (animationTimeAccumulator >= typeFrameDuration) {
            if (isDiagonal && wasVerticalOnly) {
                frameX = 0;
            }
            else if (keyUpPressed || keyDownPressed) {
                if (!keyLeftPressed && !keyRightPressed) {
                  if (frameX == 2) {
                    frameX = 3;
                  }
                  else{
                    frameX = 2;
                  }

                } 
                else {
                    frameX = (frameX + 1) % 2;
                }
            }
            else frameX = (frameX + 1) % 2;

            animationTimeAccumulator = 0;
        }
        
        isIdle = false;
    } else {
        isIdle = true;
    }
    
    if (keyLeftPressed) {
        frameY = 1;
    } 
    else if (keyRightPressed) {
        frameY = 3;
    }
    else if (keyUpPressed) {
        frameY = 2;
    }
    else if (keyDownPressed) {
        frameY = 0;
    }

    if (isIdle) {
        idleCounter = (idleCounter + 1) % 50;
        if (idleCounter == 49) {
            if (frameY == 0 || frameY == 2) {
                frameX = (frameX + 1) % 2;
            } else {
                frameX = 1;
            }
        }
    } else {
        idleCounter = 0;
    }

    if (keyUporDownJustPressed) {
        if (keyLeftPressed || keyRightPressed) {
            frameX = 0; 
        } else {
            frameX = 2; 
        }
    }
    
    if (!keyUporDownJustPressed && wasVerticalOnly && isHorizontal) {
        frameX = 0; 
    }
  }
  
  public void renderPlayer() {
    gc.drawImage(playerSpriteSheet, sourceX(), sourceY(), playerFrameWidth, playerFrameHeight, screenX, screenY, 128, 128);
  }

  public int getHitboxOffsetX() {
    return hitboxOffsetX;
  }

  public int getHitboxOffsetY() {
    return hitboxOffsetY;
  }

  public int getHitboxWidth() {
    return hitboxWidth;
  }

  public int getHitboxHeight() {
    return hitboxHeight;
  }

  public int getScreenX() {
    return screenX;
  }

  public int getScreenY() {
    return screenY;
  }
  
  
  
}
