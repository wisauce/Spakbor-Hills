package sti.oop.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sti.oop.models.Player;
import sti.oop.utils.Constants;

public class MapController {
  String tileSetSrc;
  String mapSrc;

  Image tileSet;
  int maxTileHorizonal;
  int maxTileVertical;
  int tileSize;
  int tileSetWidth;
  List<List<Integer>> matrixOfGid = new ArrayList<>();
  Player player;


  public MapController(Player player, String tileSetSrc, String mapSrc, int tileSize) {
    this.tileSetSrc= tileSetSrc;
    this.player = player;
    this.mapSrc = mapSrc;
    this.tileSize = tileSize;
    BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(mapSrc)));
    String line;
    int ctr = 0;
    try {
      line = br.readLine();
      while (line != null) {
        matrixOfGid.add(Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(Collectors.toList()));
        ctr++;
        line = br.readLine();
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
    maxTileHorizonal = ctr;
    maxTileVertical = ctr;
    InputStream streamTile = getClass().getResourceAsStream(tileSetSrc);
    if (streamTile == null) {
      System.err.println("FATAL ERROR: MapController - TileSet resource NOT FOUND: " + tileSetSrc);
      // It's crucial to stop here or throw, as 'tileSet' will be invalid.
      throw new RuntimeException("Failed to load TileSet resource. Path: " + tileSetSrc);
    }
    this.tileSet = new Image(streamTile);
    if (this.tileSet.isError()) {
        System.err.println("FATAL ERROR: MapController - Failed to load TileSet image from: " + tileSetSrc);
        Exception imageException = this.tileSet.getException();
        if (imageException != null) {
            System.err.println("Image loading exception:");
            imageException.printStackTrace();
        }
        // Again, crucial to stop or throw
        throw new RuntimeException("Error occurred while loading TileSet image. Path: " + tileSetSrc, imageException);
    } else {
        System.out.println("MapController: TileSet loaded successfully - " + tileSetSrc + " (Width: " + this.tileSet.getWidth() + ")");
    }
    tileSetWidth = (int) (tileSet.getWidth() / tileSize);
  }

  public void render(GraphicsContext gc) {
    int minRow = Math.max((player.getY() - Constants.PLAYER_SCREEN_Y) / Constants.TILE_SIZE,0);
    int maxRow = Math.min((player.getY() + Constants.PLAYER_SCREEN_Y + Constants.TILE_SIZE*2) / Constants.TILE_SIZE, maxTileHorizonal);
    int minCol = Math.max((player.getX() - Constants.PLAYER_SCREEN_X) / Constants.TILE_SIZE, 0);
    int maxCol = Math.min((player.getX() + Constants.PLAYER_SCREEN_X + Constants.TILE_SIZE*2) / Constants.TILE_SIZE, maxTileVertical);
    for (int row = minRow; row < maxRow; row++) {
        for (int col = minCol; col < maxCol; col++) {
          drawTile(matrixOfGid.get(row).get(col), col * Constants.TILE_SIZE - player.getX() + Constants.PLAYER_SCREEN_X, row * Constants.TILE_SIZE - player.getY() + Constants.PLAYER_SCREEN_Y, gc);          
        }
    }
  }

  public void drawTile(int gid, int x, int y, GraphicsContext gc) {
    int sourceX = (gid % tileSetWidth) * tileSize;
    int sourceY = (gid / tileSetWidth) * tileSize;
    gc.drawImage(tileSet, sourceX, sourceY, tileSize, tileSize, x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);
    // gc.fillText(String.valueOf(gid), x + 4, y + 12); UNCOMMENT BUAT DEBUGGING TILES
  }

}
