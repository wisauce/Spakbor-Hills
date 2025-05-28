package sti.oop.controllers;

import java.util.List;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.Asset;
import sti.oop.models.Player;
import sti.oop.models.Teleporter;
import sti.oop.models.DeployedObject.House;
import sti.oop.models.DeployedObject.Pond;
import sti.oop.models.DeployedObject.ShippingBin;
import sti.oop.utils.Constants;
import sti.oop.utils.Constants;
import sti.oop.utils.RandomizeFarm;
import sti.oop.utils.Tile;

public class AssetController {
  Map<MapName,List<Asset>> mapOfListOfAssets;
  List<Asset> currentAssets;
  Player player;

  public AssetController(Player player) {
    /* Initialize RandomizeFarm */
    RandomizeFarm farmMap = new RandomizeFarm();
    farmMap.generateMap(farmMap);

    mapOfListOfAssets = Map.ofEntries(
      Map.entry(MapName.FARM, List.of(
        new Teleporter(16 * Constants.TILE_SIZE + Constants.TILE_SIZE/2, 17 * Constants.TILE_SIZE, MapName.HOUSE),
        new Asset(20 * Constants.TILE_SIZE, 20 * Constants.TILE_SIZE, "/images/monyet.jpg", true),
        new House(farmMap),
        new Pond(farmMap),
        new ShippingBin(farmMap)
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