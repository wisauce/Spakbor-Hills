package sti.oop.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import sti.oop.models.Constants;

public class MapController {
  String tileSetSrc;
  String mapSrc;
  Image tileSet;
  GraphicsContext gc;
  int maxTileHorizonal;
  int maxTileVertical;
  int tileSize;
  int tileSetWidth;
  List<List<Integer>> matrixOfGid = new ArrayList<>();

  public MapController(GraphicsContext gc, String tileSetSrc, String mapSrc, int tileSize) {
    this.tileSetSrc= tileSetSrc;
    this.mapSrc = mapSrc;
    this.gc = gc;
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
    this.tileSet = new Image(getClass().getResourceAsStream(tileSetSrc));
    tileSetWidth = (int) (tileSet.getWidth() / tileSize);
  }

  public void render(int playerX, int playerY, int playerScreenX, int playerScreenY) {
    int minRow = Math.max((playerY - playerScreenY) / Constants.TILE_SIZE,0);
    int maxRow = Math.min((playerY + playerScreenY + Constants.TILE_SIZE*2) / Constants.TILE_SIZE, maxTileHorizonal);
    int minCol = Math.max((playerX - playerScreenX) / Constants.TILE_SIZE, 0);
    int maxCol = Math.min((playerX + playerScreenX + Constants.TILE_SIZE*2) / Constants.TILE_SIZE, maxTileVertical);
    for (int row = minRow; row < maxRow; row++) {
        for (int col = minCol; col < maxCol; col++) {
          drawTile(matrixOfGid.get(row).get(col), col * Constants.TILE_SIZE - playerX + playerScreenX, row * Constants.TILE_SIZE - playerY + playerScreenY);          
        }
    }
  }

  public void drawTile(int gid, int x, int y) {
    int sourceX = (gid % tileSetWidth) * tileSize;
    int sourceY = (gid / tileSetWidth) * tileSize;
    gc.drawImage(tileSet, sourceX, sourceY, tileSize, tileSize, x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);
    // gc.fillText(String.valueOf(gid), x + 4, y + 12); UNCOMMENT BUAT DEBUGGING TILES
  }

}
