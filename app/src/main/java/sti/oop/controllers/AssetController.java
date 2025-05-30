package sti.oop.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.Player;
import sti.oop.models.NPC.Dasco;
import sti.oop.models.assets.Asset;
import sti.oop.models.assets.Bed;
import sti.oop.models.assets.Land;
import sti.oop.models.assets.NPCArea;
import sti.oop.models.assets.SleepingArea;
import sti.oop.models.assets.Teleporter;
import sti.oop.models.deployedObject.House;
import sti.oop.models.deployedObject.Pond;
import sti.oop.models.deployedObject.ShippingBin;
import sti.oop.utils.Constants;
import sti.oop.utils.RandomizeFarm;

public class AssetController {
  Map<MapName, List<Asset>> mapOfListOfAssets;
  List<Asset> currentAssets;
  Player player;

  public AssetController(Player player, List<Asset> lands) {
    mapOfListOfAssets = new HashMap<>();

    /*Initialze farmMap */
    List<Asset> assetsOnFarm = new ArrayList<>();
    RandomizeFarm farmMap = new RandomizeFarm();
    assetsOnFarm.add(new NPCArea(new Dasco()));
    farmMap.generateMap(farmMap);
    House house = new House(farmMap);
    assetsOnFarm.add(house);
    Teleporter teleportToHouse = new Teleporter(house.getX() + (int) (2.5 * Constants.TILE_SIZE),house.getY() + 6 * Constants.TILE_SIZE, MapName.HOUSE, 16 * Constants.TILE_SIZE, 25 * Constants.TILE_SIZE);
    assetsOnFarm.add(teleportToHouse);
    assetsOnFarm.add(new Pond(farmMap));
    assetsOnFarm.add(new ShippingBin(farmMap));
    assetsOnFarm.addAll(lands);
    mapOfListOfAssets.put(MapName.FARM, assetsOnFarm);

    /*Initialize house */
    List<Asset> assetsOnHome = new ArrayList<>();
    assetsOnHome.add(new Teleporter(16 * Constants.TILE_SIZE, 25 * Constants.TILE_SIZE, MapName.FARM, teleportToHouse.getX(), teleportToHouse.getY()));
    assetsOnHome.add(new Bed( Constants.TILE_SIZE, 4 * Constants.TILE_SIZE));
    assetsOnHome.add(new SleepingArea(Constants.TILE_SIZE, 4 * Constants.TILE_SIZE, 3 * Constants.TILE_SIZE, 9 * Constants.TILE_SIZE, 3 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE));
    mapOfListOfAssets.put(MapName.HOUSE, assetsOnHome);

    /*Initialize World*/
    List<Asset> assetsOnWorld = new ArrayList<>();
    assetsOnWorld.add(new Asset(1*Constants.TILE_SIZE,42*Constants.TILE_SIZE,8*Constants.TILE_SIZE,4*Constants.TILE_SIZE,"/assets/store.png",true));
    assetsOnWorld.add(new Asset(15*Constants.TILE_SIZE,11*Constants.TILE_SIZE,4*Constants.TILE_SIZE,4*Constants.TILE_SIZE,"/assets/npc_house_1.png",true));
    assetsOnWorld.add(new Asset(31*Constants.TILE_SIZE,4*Constants.TILE_SIZE,4*Constants.TILE_SIZE,4*Constants.TILE_SIZE,"/assets/npc_house_2.png",true));
    assetsOnWorld.add(new Asset(10*Constants.TILE_SIZE,42*Constants.TILE_SIZE,4*Constants.TILE_SIZE,4*Constants.TILE_SIZE,"/assets/npc_house_3.png",true));
    assetsOnWorld.add(new Asset(15*Constants.TILE_SIZE,42*Constants.TILE_SIZE,4*Constants.TILE_SIZE,4*Constants.TILE_SIZE,"/assets/npc_house_4.png",true));
    assetsOnWorld.add(new Asset(20*Constants.TILE_SIZE,41*Constants.TILE_SIZE,4*Constants.TILE_SIZE,4*Constants.TILE_SIZE,"/assets/npc_house_5.png",true));
    assetsOnWorld.add(new Asset(39*Constants.TILE_SIZE,39*Constants.TILE_SIZE,4*Constants.TILE_SIZE,4*Constants.TILE_SIZE,"/assets/npc_house_5.png",true));
    mapOfListOfAssets.put(MapName.WORLD, assetsOnWorld);

    /*Initialize_NPC_House*/
    List<Asset> assetsOnNPC1_House = new ArrayList<>();
    mapOfListOfAssets.put(MapName.NPC1_HOUSE, assetsOnNPC1_House);

    /*Initialize_NPC_House*/
    List<Asset> assetsOnNPC2_House = new ArrayList<>();
    mapOfListOfAssets.put(MapName.NPC2_HOUSE, assetsOnNPC2_House);

    /*Initialize_NPC_House*/
    List<Asset> assetsOnNPC3_House = new ArrayList<>();
    mapOfListOfAssets.put(MapName.NPC3_HOUSE, assetsOnNPC3_House);

    /*Initialize_NPC_House*/
    List<Asset> assetsOnNPC4_House = new ArrayList<>();
    mapOfListOfAssets.put(MapName.NPC4_HOUSE, assetsOnNPC4_House);

    /*Initialize_NPC_House*/
    List<Asset> assetsOnNPC5_House = new ArrayList<>();
    mapOfListOfAssets.put(MapName.NPC5_HOUSE, assetsOnNPC5_House);

    /*Initialize_NPC_House*/
    List<Asset> assetsOnNPC6_House = new ArrayList<>();
    mapOfListOfAssets.put(MapName.NPC6_HOUSE, assetsOnNPC6_House);

    currentAssets = mapOfListOfAssets.get(MapName.NPC1_HOUSE);
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