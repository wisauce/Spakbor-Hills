package sti.oop.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.Asset;
import sti.oop.models.Land;
import sti.oop.models.NPCArea;
import sti.oop.models.Player;
import sti.oop.models.Teleporter;
import sti.oop.models.NPC.Dasco;
import sti.oop.utils.Constants;

public class AssetController {
  Map<MapName, List<Asset>> mapOfListOfAssets;
  List<Asset> currentAssets;
  Player player;

  public AssetController(Player player) {
    mapOfListOfAssets = new HashMap<>();
    List<Asset> assetsOnFarm = new ArrayList<>();
    assetsOnFarm.add(new Teleporter(16 * Constants.TILE_SIZE + Constants.TILE_SIZE / 2, 17 * Constants.TILE_SIZE, MapName.HOUSE));
    assetsOnFarm.add(new NPCArea(new Dasco()));
    LandSetter landSetter = new LandSetter();
    assetsOnFarm.addAll(landSetter.setLandOnFarm());
    mapOfListOfAssets.put(MapName.FARM, assetsOnFarm);


    List<Asset> assetsOnHome = new ArrayList<>();
    assetsOnHome.add(new Teleporter(12 * Constants.TILE_SIZE, 23 * Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, Constants.TILE_SIZE, MapName.FARM));
    mapOfListOfAssets.put(MapName.HOUSE, assetsOnHome);
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
        gc.drawImage(asset.getImage(), assetScreenX, assetScreenY, asset.getSolidArea().getWidth(),
            asset.getSolidArea().getHeight());
      } else {
      }
    }
  }
}