package sti.oop.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;

public class MapController {
  Image tileSheet;
  InputStream is;
  GraphicsContext gc;
  int maxTileHorizonal;
  int maxTileVertical;
  int tileSize = 64;

  public MapController(GraphicsContext gc, String tileSheetSrc, String mapSrc) {
    this.tileSheet = new Image(getClass().getResourceAsStream(tileSheetSrc));
    this.is = getClass().getResourceAsStream(mapSrc);
    if (tileSheet == null) {
      System.out.println("Map file NOT FOUND!");
    } else {
      System.out.println("Map file loaded successfully.");
    }
    this.gc = gc;
    BufferedReader br = new BufferedReader(new InputStreamReader(is));
    String line;
    int ctr = 0;
    try {
      line = br.readLine();
      while (line != null) {
        ctr++;
        line = br.readLine();
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
    maxTileHorizonal = ctr;
    maxTileVertical = ctr;
  }

  public void render() {
    InputStream is2 = getClass().getResourceAsStream("/tileSheet/farm/map.txt");
    BufferedReader br2 = new BufferedReader(new InputStreamReader(is2));
    List<Integer> currentLineGid;
    String line;
    for (int row = 0; row < maxTileHorizonal; row++) {
      try {
        line = br2.readLine();
        currentLineGid = Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(Collectors.toList());
        for (int col = 0; col < maxTileVertical; col++) {
          drawTile(currentLineGid.get(col), col, row);
        }
      } catch(IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void drawTile(int gid, int x, int y) {
    int sourceX = (gid % 8) * tileSize;
    int sourceY = (gid / 8) * tileSize;
    gc.drawImage(tileSheet, sourceX, sourceY, tileSize, tileSize, x*128, y*128, 128, 128);
  }

}
