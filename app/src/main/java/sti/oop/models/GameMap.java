package sti.oop.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javafx.scene.image.Image;

public class GameMap {
  private List<List<Integer>> matrixOfGid = new ArrayList<>();
  private Image tileSet;
  private int maxTileHorizonal;
  private int maxTileVertical;
  private int tileSize;
  private int numberOfTilesInTileSetRow;
  private int playerStartingPositionX;
  private int playerStartingPositionY;

  
  public GameMap(String tileSetSrc, String mapSrc, int tileSize, int playerStartingPositionX, int playerStartingPositionY) {
    this.tileSize = tileSize;
    BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(mapSrc)));
    String line;
    try {
      line = br.readLine();
      while (line != null) {
        matrixOfGid.add(Arrays.stream(line.split(" ")).map(Integer::parseInt).collect(Collectors.toList()));
        line = br.readLine();
      }
    } catch(IOException e) {
      e.printStackTrace();
    }
    maxTileHorizonal = matrixOfGid.getFirst().size();
    maxTileVertical = matrixOfGid.size();
    this.tileSet = new Image(getClass().getResourceAsStream(tileSetSrc));
    numberOfTilesInTileSetRow = (int) (tileSet.getWidth() / tileSize);
    this.playerStartingPositionX = playerStartingPositionX;
    this.playerStartingPositionY = playerStartingPositionY;
  }
  
  public List<List<Integer>> getMatrixOfGid() {
    return matrixOfGid;
  }
  
  public int getMaxTileHorizonal() {
    return maxTileHorizonal;
  }
  
  public int getMaxTileVertical() {
    return maxTileVertical;
  }
  
  public int getTileSize() {
    return tileSize;
  }
  
  public int getNumberOfTilesInTileSetRow() {
    return numberOfTilesInTileSetRow;
  }
  
  public Image getTileSet() {
    return tileSet;
  }

  public int getPlayerStartingPositionX() {
    return playerStartingPositionX;
  }

  public int getPlayerStartingPositionY() {
    return playerStartingPositionY;
  }
  
}