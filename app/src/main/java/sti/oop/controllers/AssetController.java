package sti.oop.controllers;

import java.util.List;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.Asset;
import sti.oop.models.Land;
import sti.oop.models.NPCArea;
import sti.oop.models.Player;
import sti.oop.models.Teleporter;
import sti.oop.models.NPC.Dasco;
import sti.oop.models.NPC.NPC;
import sti.oop.utils.Constants;

public class AssetController {
  Map<MapName,List<Asset>> mapOfListOfAssets;
  List<Asset> currentAssets;
  Player player;

  public AssetController(Player player) {
    mapOfListOfAssets = Map.ofEntries(
      Map.entry(MapName.FARM, List.of(
        new Teleporter(16 * Constants.TILE_SIZE + Constants.TILE_SIZE/2, 17 * Constants.TILE_SIZE, MapName.HOUSE),
        new Land(14 * Constants.TILE_SIZE, 17 * Constants.TILE_SIZE),
        // new Asset(20 * Constants.TILE_SIZE, 20 * Constants.TILE_SIZE, "/images/monyet.jpg", true),
        new NPCArea(new Dasco())
      )),
      Map.entry(MapName.HOUSE, List.of(
        new Asset(10 * Constants.TILE_SIZE, 10 * Constants.TILE_SIZE, "/images/monyet.jpg", true),
        new Teleporter(12 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, Constants.TILE_SIZE, MapName.FARM)
      ))
    );
    currentAssets = mapOfListOfAssets.get(MapName.FARM);
    this.player = player;
  }

  public List<Asset> getAssets() {
    return currentAssets;
  }

  public void setAssets(MapName mapName) {
    currentAssets = mapOfListOfAssets.get(mapName);
  }

  public void render(GraphicsContext gc) {
    Canvas canvas = gc.getCanvas();
    double canvasWidth = canvas.getWidth();
    double canvasHeight = canvas.getHeight();
    int playerScreenX = (int) (canvasWidth / 2) - (Constants.TILE_SIZE / 2);
    int playerScreenY = (int) (canvasHeight / 2) - (Constants.TILE_SIZE / 2);
    int assetScreenX;
    int assetScreenY;
    for (Asset asset : currentAssets) {
      assetScreenX = asset.getX() - player.getX() + playerScreenX;
      assetScreenY = asset.getY() - player.getY() + playerScreenY;
      asset.updateSolidArea();
      if (asset.getImage() != null) {
        gc.drawImage(asset.getImage(), assetScreenX, assetScreenY, asset.getSolidArea().getWidth(), asset.getSolidArea().getHeight());
      } else {
      }
    }
  }
}