package sti.oop.utils;

public class Constants {
  public static final int TILE_ORIGINAL_SIZE = 64;
  public static final double SCALE = 2;
  public static final int TILE_SIZE =(int) (TILE_ORIGINAL_SIZE * SCALE);
  public static final int SCREEN_WIDTH = 896;
  public static final int SCREEN_HEIGHT = 896;
  public static final int PLAYER_SCREEN_X = SCREEN_WIDTH / 2 - TILE_SIZE / 2;
  public static final int PLAYER_SCREEN_Y = SCREEN_HEIGHT / 2 - TILE_SIZE / 2;
}