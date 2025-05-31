package sti.oop.controllers;

import java.io.IOException;
import java.util.List;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.Farm;
import sti.oop.models.Player;
import sti.oop.models.Player.Gender;
import sti.oop.models.assets.Asset;
import sti.oop.utils.ItemSpriteManager;

public class FarmController {
  @FXML
  private Label timeDisplay;

  @FXML
  private Label dateDisplay;

  @FXML
  private Label locationDisplay;

  @FXML
  private AnchorPane anchorPane;

  @FXML
  private BorderPane hud;

  @FXML
  private Canvas canvas;

  @FXML
  private Label interactionNotification;

  @FXML
  private Label nameDisplay;

  @FXML
  private Label energyDisplay;

  @FXML
  private ProgressBar energyBar;

  private long lastTime = 0;

  private Scene scene;
  private GraphicsContext gc;

  private boolean inventoryOpened = false;
  private StackPane inventoryPane;

  private Farm farm;
  private TimeController timeController;
  private CollisionController collisionController;
  private PlayerController playerController;
  GameMapController gameMapController;
  private AssetController assetController;
  private HotbarController hotbarController;
  private InventoryController inventoryController;
  private PanelController panelController;

  private Player player;

  int spriteCounter = 0;
  int idleCounter = 0;
  boolean isIdle = true;

  public void render() {
  }

  @FXML
  public void initialize() throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Panel.fxml"));
    Parent panelRoot = loader.load();
    panelController = loader.getController();

    hud.setBottom(panelRoot);

    canvas.widthProperty().bind(anchorPane.widthProperty());
    canvas.heightProperty().bind(anchorPane.heightProperty());

    ItemSpriteManager.preloadSprites();
  }

  private void applyTimeOfDayLighting() {
    Color timeColor = farm.timeOfDayColor();

    Color originalColor = (Color) gc.getFill();

    gc.setFill(timeColor);
    gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

    Color weatherColor = farm.weatherColor();
    if (!weatherColor.equals(Color.TRANSPARENT)) {
      gc.setFill(weatherColor);
      gc.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    gc.setFill(originalColor);
  }

public void initializePlayerData(String playerName, String gender, String farmName) {
  Gender genderEnum = "Male".equals(gender) ? Gender.MALE : Gender.FEMALE;
  this.player = new Player(playerName, genderEnum, farmName);

  nameDisplay.setText(player.getName());

  HealthBarUpdater healthBarUpdater = new HealthBarUpdater(energyDisplay, energyBar);
  healthBarUpdater.updateHealthBar(player.getEnergy(), player.getMAX_ENERGY());
  player.setHealthBarUpdater(healthBarUpdater);

  hotbarController = new HotbarController(player);
  HBox hotbar = hotbarController.getHotbarContainer();
  hotbar.setLayoutX(20);
  hotbar.setLayoutY(550);
  anchorPane.getChildren().add(hotbar);

  LandSetter landSetter = new LandSetter();
  List<Asset> lands = landSetter.setLandOnFarm();
  assetController = new AssetController(player, lands);
  collisionController = new CollisionController();
  gameMapController = new GameMapController(player);
  playerController = new PlayerController(player, collisionController, this);
  farm = new Farm(playerController, lands);
  timeController = new TimeController(farm, this, timeDisplay, dateDisplay, locationDisplay);

  updateHotbar();
  timeController.render();

  AnimationTimer gameTime = new AnimationTimer() {
    private long lastTime = 0;

    @Override
    public void handle(long now) {
      if (lastTime == 0) {
        lastTime = now;
      }

      if (now - lastTime >= 1_000_000_000) {
        timeController.update();
        timeController.render();
        lastTime = now;
      }

      gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
      gameMapController.render(gc);
      collisionController.checkAssetCollision(assetController.getAssets(), playerController, panelController);

      if (playerController.isJustEaten()) {
        String eatingResult = playerController.getAction().handleEating();
        panelController.showDialog(eatingResult);
        playerController.setJustEaten(false);
      }

      assetController.render(gc);
      playerController.render(gc);

      applyTimeOfDayLighting();
    }
  };

  Platform.runLater(() -> {
    scene = hud.getParent().getScene();
    gc = canvas.getGraphicsContext2D();
    gc.setImageSmoothing(false);
    playerController.keyMapper(scene);
    timeController.render();
    gameTime.start();
  });
}


  /*
   * <------------------------------------------SEPERATOR-------------------------
   * ----------------------->
   */

  /* -------------------------------------------------------------------------- */
  /* Inventory Logics */
  /* -------------------------------------------------------------------------- */

  public PlayerController getPlayerController() {
    return playerController;
  }

  public GameMapController getGameMapController() {
    return gameMapController;
  }

  public InventoryController getInventoryController() {
    return inventoryController;
  }

  /* Inventory Logics */
  public void toggleInventory() {
    System.out.println("Toggling Inventory... Current state: " + (inventoryOpened ? "Open" : "Closed"));

    if (inventoryOpened) {
      System.out.println("Closing inventory...");
      hud.getChildren().remove(inventoryPane);
      inventoryOpened = false;
      timeController.setTimeFrozen(false);
      System.out.println("Inventory closed");
    }

    else {
      System.out.println("Opening inventory...");
      timeController.setTimeFrozen(true);

      /* Inventory GUI render */
      try {
        FXMLLoader inventoryLoader = new FXMLLoader(getClass().getResource("/views/Inventory.fxml"));
        Parent inventoryParent = inventoryLoader.load();
        inventoryPane = (StackPane) inventoryParent;

        InventoryController inventoryController = inventoryLoader.getController();
        inventoryController.setFarmController(this);
        inventoryController.setPlayer(playerController);

        inventoryPane.setMaxSize(800, 400);
        inventoryPane
            .setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-border-color: white; -fx-border-width: 2px;");

        BorderPane.setAlignment(inventoryPane, javafx.geometry.Pos.CENTER);
        hud.setCenter(inventoryPane);

        inventoryPane.requestFocus();

        inventoryOpened = true;
        System.out.println("Inventory opened");
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("Failed to load Inventory.fxml: " + e.getMessage());
      } catch (Exception e) {
        e.printStackTrace();
        System.out.println("Unexpected error: " + e.getMessage());
      }
    }
  }

  public boolean getStatusInventory() {
    return inventoryOpened;
  }

  public void changeMap(MapName mapName) {
    gameMapController.setCurrentMap(mapName);
    collisionController.setCurrentCollisionMap(mapName);
    assetController.setAssets(mapName);
  }

  public void setInteractNotification(boolean visible) {
    interactionNotification.setVisible(visible);
  }

  public TimeController getTimeController() {
    return timeController;
  }

  public void updateHotbar() {
    if (hotbarController != null && playerController != null) {
      hotbarController.updateOnHandDisplay(playerController.getPlayer());

      if (inventoryController != null && inventoryOpened) {
        inventoryController.handleHotbarDeselection();
      }
    }
  }

  public PanelController getPanelController() {
    return panelController;
  }

  public Farm getFarm() {
    return farm;
  }

  /*
   * <------------------------------------------SEPERATOR-------------------------
   * ----------------------->
   */
}