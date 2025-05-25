package sti.oop.controllers;
import java.util.Map;

import javafx.scene.canvas.GraphicsContext;
import sti.oop.models.GameMap;
import sti.oop.models.Player;
import sti.oop.utils.Constants;

public class GameMapController {
  public enum MapName{
    FARM,
    HOUSE,
    WORLD
  }

  Map<MapName, GameMap> mapOfGameMaps;
  GameMap currentMap;
  Player player;


  public GameMapController(Player player) {
    this.player = player;
    mapOfGameMaps = Map.ofEntries(
      Map.entry(MapName.FARM, new GameMap("/maps/farm.png", "/maps/farm.txt", 16, 17 * Constants.TILE_SIZE, 20 * Constants.TILE_SIZE)),
      Map.entry(MapName.HOUSE, new GameMap("/maps/house.png", "/maps/house.txt",16, 32 * 12, 32 * 23))
    );
    setCurrentMap(MapName.FARM);
  }

  public void setCurrentMap(MapName mapName) {
    currentMap = mapOfGameMaps.get(mapName);
    player.setX(currentMap.getPlayerStartingPositionX());
    player.setY(currentMap.getPlayerStartingPositionY());
  }

  public void render(GraphicsContext gc) {
    int minRow = Math.max((player.getY() - Constants.PLAYER_SCREEN_Y) / Constants.TILE_SIZE,0);
    int maxRow = Math.min((player.getY() + Constants.PLAYER_SCREEN_Y + Constants.TILE_SIZE*2) / Constants.TILE_SIZE, currentMap.getMaxTileHorizonal());
    int minCol = Math.max((player.getX() - Constants.PLAYER_SCREEN_X) / Constants.TILE_SIZE, 0);
    int maxCol = Math.min((player.getX() + Constants.PLAYER_SCREEN_X + Constants.TILE_SIZE*2) / Constants.TILE_SIZE, currentMap.getMaxTileVertical());
    for (int row = minRow; row < maxRow; row++) {
        for (int col = minCol; col < maxCol; col++) {
          drawTile(currentMap.getMatrixOfGid().get(row).get(col), col * Constants.TILE_SIZE - player.getX() + Constants.PLAYER_SCREEN_X, row * Constants.TILE_SIZE - player.getY() + Constants.PLAYER_SCREEN_Y, gc);          
        }
    }
  }

  public void drawTile(int gid, int x, int y, GraphicsContext gc) {
    int sourceX = (gid % currentMap.getNumberOfTilesInTileSetRow()) * currentMap.getTileSize();
    int sourceY = (gid / currentMap.getNumberOfTilesInTileSetRow()) * currentMap.getTileSize();
    gc.drawImage(currentMap.getTileSet(), sourceX, sourceY, currentMap.getTileSize(), currentMap.getTileSize(), x, y, Constants.TILE_SIZE, Constants.TILE_SIZE);
  }
}
