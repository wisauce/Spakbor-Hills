package sti.oop.controllers;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.Player;
import sti.oop.models.NPC.Abigail;
import sti.oop.models.NPC.Caroline;
import sti.oop.models.NPC.Dasco;
import sti.oop.models.NPC.Emily;
import sti.oop.models.NPC.MayorTadi;
import sti.oop.models.NPC.Perry;
import sti.oop.models.assets.Asset;
import sti.oop.models.assets.Bed;
import sti.oop.models.assets.BinArea;
import sti.oop.models.assets.CookingArea;
import sti.oop.models.assets.LakeArea;
import sti.oop.models.assets.NPCArea;
import sti.oop.models.assets.OceanArea;
import sti.oop.models.assets.PondArea;
import sti.oop.models.assets.RiverArea;
import sti.oop.models.assets.SleepingArea;
import sti.oop.models.assets.StoreArea;
import sti.oop.models.assets.Teleporter;
import sti.oop.models.deploys.House;
import sti.oop.models.deploys.Pond;
import sti.oop.models.deploys.ShippingBin;
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
    // assetsOnFarm.add(new NPCArea(new Dasco()));
    farmMap.generateMap(farmMap);
    House house = new House(farmMap);
    assetsOnFarm.add(house);
    Teleporter teleportToHouse = new Teleporter(house.getX() + (int) (2.5 * Constants.TILE_SIZE),house.getY() + 6 * Constants.TILE_SIZE, MapName.HOUSE, 16 * Constants.TILE_SIZE, 25 * Constants.TILE_SIZE);
    assetsOnFarm.add(teleportToHouse);
    Pond pond = new Pond(farmMap);
    assetsOnFarm.add(pond);
    assetsOnFarm.add(new PondArea(pond));
    assetsOnFarm.add(new ShippingBin(farmMap));
    assetsOnFarm.add(new BinArea( (farmMap.getShippingBinLoc()[2]+8)*Constants.TILE_SIZE, (farmMap.getShippingBinLoc()[0]+8)*Constants.TILE_SIZE, 5*Constants.TILE_SIZE, 4*Constants.TILE_SIZE) );
    assetsOnFarm.add(new StoreArea( (farmMap.getShippingBinLoc()[2]+8)*Constants.TILE_SIZE, (farmMap.getShippingBinLoc()[0]+8)*Constants.TILE_SIZE, 5*Constants.TILE_SIZE, 4*Constants.TILE_SIZE) );
    assetsOnFarm.addAll(lands);
    assetsOnFarm.add(new Teleporter(29 * Constants.TILE_SIZE, 45* Constants.TILE_SIZE, 2* Constants.TILE_SIZE, 1* Constants.TILE_SIZE, MapName.WORLD, 1* Constants.TILE_SIZE, 47* Constants.TILE_SIZE));
    mapOfListOfAssets.put(MapName.FARM, assetsOnFarm);

    /*Initialize house */
    List<Asset> assetsOnHome = new ArrayList<>();
    assetsOnHome.add(new Teleporter(16 * Constants.TILE_SIZE, 25 * Constants.TILE_SIZE, MapName.FARM, teleportToHouse.getX(), teleportToHouse.getY()));
    assetsOnHome.add(new Bed( Constants.TILE_SIZE, 4 * Constants.TILE_SIZE));
    assetsOnHome.add(new SleepingArea(Constants.TILE_SIZE, 4 * Constants.TILE_SIZE, 3 * Constants.TILE_SIZE, 5 * Constants.TILE_SIZE, 3 * Constants.TILE_SIZE, 4 * Constants.TILE_SIZE));
    assetsOnHome.add(new CookingArea(27 * Constants.TILE_SIZE, 20 * Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, 1 * Constants.TILE_SIZE));
    assetsOnHome.add(new CookingArea(34 * Constants.TILE_SIZE, 20 * Constants.TILE_SIZE, 2 * Constants.TILE_SIZE, 1 * Constants.TILE_SIZE));
    mapOfListOfAssets.put(MapName.HOUSE, assetsOnHome);

    /*Initialize World*/
    List<Asset> assetsOnWorld = new ArrayList<>();
    assetsOnWorld.add(new Teleporter(1 * Constants.TILE_SIZE,47 * Constants.TILE_SIZE,2 * Constants.TILE_SIZE,2 * Constants.TILE_SIZE,MapName.FARM,29 * Constants.TILE_SIZE,45 * Constants.TILE_SIZE));
    
    assetsOnWorld.add(new Asset(1*Constants.TILE_SIZE,41*Constants.TILE_SIZE,8*Constants.TILE_SIZE,4*Constants.TILE_SIZE,"/assets/store.png",true));
    assetsOnWorld.add(new Teleporter(5 * Constants.TILE_SIZE,46 * Constants.TILE_SIZE,2 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,MapName.STORE,5 * Constants.TILE_SIZE,10 * Constants.TILE_SIZE));

    assetsOnWorld.add(new Asset(15*Constants.TILE_SIZE,11*Constants.TILE_SIZE,5*Constants.TILE_SIZE,5*Constants.TILE_SIZE,"/assets/npc_house_1.png",true));
    assetsOnWorld.add(new Teleporter(17 * Constants.TILE_SIZE,16 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,MapName.NPC1_HOUSE,5 * Constants.TILE_SIZE,9 * Constants.TILE_SIZE));

    assetsOnWorld.add(new Asset(31*Constants.TILE_SIZE,4*Constants.TILE_SIZE,5*Constants.TILE_SIZE,5*Constants.TILE_SIZE,"/assets/npc_house_2.png",true));
    assetsOnWorld.add(new Teleporter(33 * Constants.TILE_SIZE,9 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,MapName.NPC2_HOUSE,5 * Constants.TILE_SIZE,9 * Constants.TILE_SIZE));

    assetsOnWorld.add(new Asset(10*Constants.TILE_SIZE,40*Constants.TILE_SIZE,4*Constants.TILE_SIZE,5*Constants.TILE_SIZE,"/assets/npc_house_3.png",true));
    assetsOnWorld.add(new Teleporter(11 * Constants.TILE_SIZE,45 * Constants.TILE_SIZE,2 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,MapName.NPC3_HOUSE,5 * Constants.TILE_SIZE,9 * Constants.TILE_SIZE));

    assetsOnWorld.add(new Asset(15*Constants.TILE_SIZE,40*Constants.TILE_SIZE,4*Constants.TILE_SIZE,5*Constants.TILE_SIZE,"/assets/npc_house_4.png",true));
    assetsOnWorld.add(new Teleporter(16 * Constants.TILE_SIZE,45 * Constants.TILE_SIZE,2 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,MapName.NPC4_HOUSE,5 * Constants.TILE_SIZE,9 * Constants.TILE_SIZE));
    
    assetsOnWorld.add(new Asset(45*Constants.TILE_SIZE,2*Constants.TILE_SIZE,5*Constants.TILE_SIZE,5*Constants.TILE_SIZE,"/assets/npc_house_5.png",true));
    assetsOnWorld.add(new Teleporter(47 * Constants.TILE_SIZE,7 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,MapName.NPC5_HOUSE,5 * Constants.TILE_SIZE,9 * Constants.TILE_SIZE));

    assetsOnWorld.add(new OceanArea());
    assetsOnWorld.add(new RiverArea());
    assetsOnWorld.add(new LakeArea());
    mapOfListOfAssets.put(MapName.WORLD, assetsOnWorld);

    /*Initialize_Store*/
    List<Asset> assetsOnStore = new ArrayList<>();
    assetsOnStore.add(new Asset(4 * Constants.TILE_SIZE,4 * Constants.TILE_SIZE,"/sprites/emilyCooker.gif",1 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,true));
    assetsOnStore.add(new NPCArea(new Emily(), 3,3 ));
    assetsOnStore.add(new Teleporter(5 * Constants.TILE_SIZE,10 * Constants.TILE_SIZE,2 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,MapName.WORLD,5 * Constants.TILE_SIZE,45 * Constants.TILE_SIZE));
    mapOfListOfAssets.put(MapName.STORE, assetsOnStore);

    /*Initialize_NPC_House*/
    List<Asset> assetsOnNPC1_House = new ArrayList<>();
    assetsOnNPC1_House.add(new Asset(6 * Constants.TILE_SIZE,7 * Constants.TILE_SIZE,"/sprites/mayorTadi.gif",1 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,true));
    assetsOnNPC1_House.add(new NPCArea(new MayorTadi(), 5,6 ));
    assetsOnNPC1_House.add(new Teleporter(4 * Constants.TILE_SIZE,9 * Constants.TILE_SIZE,2 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,MapName.WORLD,17 * Constants.TILE_SIZE,16 * Constants.TILE_SIZE));
    mapOfListOfAssets.put(MapName.NPC1_HOUSE, assetsOnNPC1_House);

    /*Initialize_NPC_House*/
    List<Asset> assetsOnNPC2_House = new ArrayList<>();
    assetsOnNPC2_House.add(new Asset(3 * Constants.TILE_SIZE,7 * Constants.TILE_SIZE,"/sprites/abigailExplorer.gif",1 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,true));
    assetsOnNPC2_House.add(new NPCArea(new Abigail(), 2,6 ));
    assetsOnNPC2_House.add(new Teleporter(4 * Constants.TILE_SIZE,9 * Constants.TILE_SIZE,2 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,MapName.WORLD,33 * Constants.TILE_SIZE,9 * Constants.TILE_SIZE));
    mapOfListOfAssets.put(MapName.NPC2_HOUSE, assetsOnNPC2_House);

    /*Initialize_NPC_House*/
    List<Asset> assetsOnNPC3_House = new ArrayList<>();
    assetsOnNPC3_House.add(new Asset(6 * Constants.TILE_SIZE,5 * Constants.TILE_SIZE,"/sprites/carolineCarpenter.gif",1 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,true));
    assetsOnNPC3_House.add(new NPCArea(new Caroline(), 5,4 ));
    assetsOnNPC3_House.add(new Teleporter(4 * Constants.TILE_SIZE,9 * Constants.TILE_SIZE,2 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,MapName.WORLD,11 * Constants.TILE_SIZE,45 * Constants.TILE_SIZE));
    mapOfListOfAssets.put(MapName.NPC3_HOUSE, assetsOnNPC3_House);

    /*Initialize_NPC_House*/
    List<Asset> assetsOnNPC4_House = new ArrayList<>();
    assetsOnNPC4_House.add(new Asset(6 * Constants.TILE_SIZE,7 * Constants.TILE_SIZE,"/sprites/dascoDealer.gif",1 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,true));
    assetsOnNPC4_House.add(new NPCArea(new Dasco(), 5,6 ));
    assetsOnNPC4_House.add(new Teleporter(4 * Constants.TILE_SIZE,9 * Constants.TILE_SIZE,2 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,MapName.WORLD,16 * Constants.TILE_SIZE,45 * Constants.TILE_SIZE));
    mapOfListOfAssets.put(MapName.NPC4_HOUSE, assetsOnNPC4_House);

    /*Initialize_NPC_House*/
    List<Asset> assetsOnNPC5_House = new ArrayList<>();
    assetsOnNPC5_House.add(new Asset(6 * Constants.TILE_SIZE,7 * Constants.TILE_SIZE,"/sprites/perryAuthor.gif",1 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,true));
    assetsOnNPC5_House.add(new NPCArea(new Perry(), 5,6 ));
    assetsOnNPC5_House.add(new Teleporter(4 * Constants.TILE_SIZE,9 * Constants.TILE_SIZE,2 * Constants.TILE_SIZE,1 * Constants.TILE_SIZE,MapName.WORLD,47 * Constants.TILE_SIZE,7 * Constants.TILE_SIZE));
    mapOfListOfAssets.put(MapName.NPC5_HOUSE, assetsOnNPC5_House);

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