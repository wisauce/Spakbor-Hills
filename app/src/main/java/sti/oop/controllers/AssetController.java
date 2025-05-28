package sti.oop.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.deployedObject.House;
import sti.oop.models.deployedObject.Pond;
import sti.oop.models.deployedObject.ShippingBin;
import sti.oop.models.Player;
import sti.oop.models.NPC.Dasco;
import sti.oop.models.assets.Asset;
import sti.oop.models.assets.Bed;
import sti.oop.models.assets.NPCArea;
import sti.oop.models.assets.SleepingArea;
import sti.oop.models.assets.Teleporter;
import sti.oop.utils.Constants;
import sti.oop.utils.RandomizeFarm;

public class AssetController {
  Map<MapName, List<Asset>> mapOfListOfAssets;
  List<Asset> currentAssets;
  Player player;

  public AssetController(Player player) {
    mapOfListOfAssets = new HashMap<>();

    /*Initialze farmMap */
    List<Asset> assetsOnFarm = new ArrayList<>();
    RandomizeFarm farmMap = new RandomizeFarm();
    farmMap.generateMap(farmMap);

    assetsOnFarm.add(new Teleporter(16 * Constants.TILE_SIZE + Constants.TILE_SIZE / 2, 17 * Constants.TILE_SIZE, MapName.HOUSE));
    assetsOnFarm.add(new NPCArea(new Dasco()));
    assetsOnFarm.add(new House(farmMap));
    assetsOnFarm.add(new Pond(farmMap));
    assetsOnFarm.add(new ShippingBin(farmMap));
    LandSetter landSetter = new LandSetter();
    assetsOnFarm.addAll(landSetter.setLandOnFarm());
    mapOfListOfAssets.put(MapName.FARM, assetsOnFarm);

    /*Initialze house */
    List<Asset> assetsOnHome = new ArrayList<>();
    assetsOnHome.add(new Teleporter(16 * Constants.TILE_SIZE, 25 * Constants.TILE_SIZE, 1 * Constants.TILE_SIZE, Constants.TILE_SIZE, MapName.FARM));
    assetsOnHome.add(new Bed( Constants.TILE_SIZE, 4 * Constants.TILE_SIZE));
    assetsOnHome.add(new SleepingArea(Constants.TILE_SIZE, 4 * Constants.TILE_SIZE, 3 * Constants.TILE_SIZE, 9 * Constants.TILE_SIZE, 3 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE));
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