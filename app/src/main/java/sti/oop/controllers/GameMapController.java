package sti.oop.controllers;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sti.oop.models.GameMap;
import sti.oop.models.Player;
import sti.oop.utils.Constants;

public class GameMapController {
  public enum MapName{
    FARM,
    HOUSE,
    WORLD,
    STORE,
    NPC1_HOUSE,
    NPC2_HOUSE,
    NPC3_HOUSE,
    NPC4_HOUSE,
    NPC5_HOUSE
  }

  Map<MapName, GameMap> mapOfGameMaps;
  GameMap currentMap;
  Player player;


  public GameMapController(Player player) {
    this.player = player;
    mapOfGameMaps = Map.ofEntries(
      Map.entry(MapName.FARM, new GameMap("/maps/farm.png", "/maps/farm.txt", 16, 16 * Constants.TILE_SIZE + Constants.TILE_SIZE/2, 17 * Constants.TILE_SIZE)),
      Map.entry(MapName.HOUSE, new GameMap("/maps/house.png", "/maps/housenew.txt",16, 16 * Constants.TILE_SIZE, 25 * Constants.TILE_SIZE)),
      Map.entry(MapName.WORLD, new GameMap("/maps/worldMap.png", "/maps/worldMap.txt",16, 46 * Constants.TILE_SIZE, 1 *  Constants.TILE_SIZE)),
      Map.entry(MapName.STORE, new GameMap("/maps/house.png", "/maps/store.txt", 16, 11 * Constants.TILE_SIZE, 16 *  Constants.TILE_SIZE)),
      Map.entry(MapName.NPC1_HOUSE,new GameMap("/maps/house.png", "/maps/npcHouseInside.txt",16, 5 * Constants.TILE_SIZE, 5 *  Constants.TILE_SIZE)),
      Map.entry(MapName.NPC2_HOUSE,new GameMap("/maps/house.png", "/maps/npcHouseInside.txt",16, 5 * Constants.TILE_SIZE, 5 *  Constants.TILE_SIZE)),
      Map.entry(MapName.NPC3_HOUSE,new GameMap("/maps/house.png", "/maps/npcHouseInside.txt",16, 5 * Constants.TILE_SIZE, 5 *  Constants.TILE_SIZE)),
      Map.entry(MapName.NPC4_HOUSE,new GameMap("/maps/house.png", "/maps/npcHouseInside.txt",16, 5 * Constants.TILE_SIZE, 5 *  Constants.TILE_SIZE)),
      Map.entry(MapName.NPC5_HOUSE,new GameMap("/maps/house.png", "/maps/npcHouseInside.txt",16, 5 * Constants.TILE_SIZE, 5 *  Constants.TILE_SIZE))
    );
    currentMap = mapOfGameMaps.get(MapName.FARM);
  }

  public MapName getCurrentMap() {
    for (Map.Entry<MapName, GameMap> entry : mapOfGameMaps.entrySet()) {
        if (entry.getValue() == currentMap) {
            return entry.getKey();
        }
    }
    return MapName.FARM;
  }

  public void setCurrentMap(MapName mapName) {
    currentMap = mapOfGameMaps.get(mapName);
  }

  public void render(GraphicsContext gc) {
    Canvas canvas = gc.getCanvas();
    double canvasWidth = canvas.getWidth();
    double canvasHeight = canvas.getHeight();

    int playerScreenX = (int) (canvasWidth / 2) - (Constants.TILE_SIZE / 2);
    int playerScreenY = (int) (canvasHeight / 2) - (Constants.TILE_SIZE / 2);
    int minRow = Math.max((player.getY() - playerScreenY) / Constants.TILE_SIZE,0);
    int maxRow = Math.min((player.getY() + playerScreenY + Constants.TILE_SIZE*2) / Constants.TILE_SIZE, currentMap.getMaxTileHorizonal());
    int minCol = Math.max((player.getX() - playerScreenX) / Constants.TILE_SIZE, 0);
    int maxCol = Math.min((player.getX() + playerScreenX + Constants.TILE_SIZE*2) / Constants.TILE_SIZE, currentMap.getMaxTileVertical());
    for (int row = minRow; row < maxRow; row++) {
        for (int col = minCol; col < maxCol; col++) {
          drawTile(currentMap.getMatrixOfGid().get(row).get(col), col * Constants.TILE_SIZE - player.getX() + playerScreenX, row * Constants.TILE_SIZE - player.getY() + playerScreenY, gc);          
        }
    }
  }

  public void drawTile(int gid, int x, int y, GraphicsContext gc) {
    int sourceX = (gid % currentMap.getNumberOfTilesInTileSetRow()) * currentMap.getTileSize();
    int sourceY = (gid / currentMap.getNumberOfTilesInTileSetRow()) * currentMap.getTileSize();
    gc.drawImage(currentMap.getTileSet(), sourceX, sourceY, currentMap.getTileSize(), currentMap.getTileSize(), x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);
  }
}
