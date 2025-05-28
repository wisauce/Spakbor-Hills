package sti.oop.controllers;

import java.io.IOException;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.css.PseudoClass;
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
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.Asset;
import sti.oop.models.Farm;
import sti.oop.models.NPC.NPC;
import sti.oop.models.Player;
import sti.oop.models.Teleporter;
import sti.oop.models.NPC.Abigail;
import sti.oop.models.NPC.Caroline;
import sti.oop.models.NPC.Dasco;
import sti.oop.models.NPC.Emily;
import sti.oop.models.NPC.MayorTadi;
import sti.oop.models.NPC.Perry;
import sti.oop.models.Player.Gender;
import sti.oop.utils.Constants;
import sti.oop.utils.ItemSpriteManager;



public class FarmController {
    @FXML
    private Label timeDisplay;

    @FXML
    private Label dateDisplay;

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

    private NPC mayorTadi;
    private NPC abigail;
    private NPC caroline;
    private NPC emily;
    private NPC dasco;
    private NPC perry;

    private TimeController timeController;
    private CollisionController collisionController;
    private PlayerController playerController;
    GameMapController gameMapController;
    private AssetController assetController;

    int spriteCounter = 0;
    int idleCounter = 0;
    boolean isIdle = true;

    public void render() {}

    @FXML
    public void initialize() {
      canvas.widthProperty().bind(anchorPane.widthProperty());
      canvas.heightProperty().bind(anchorPane.heightProperty());
      
      /* Load Item Sprites */
      ItemSpriteManager.preloadSprites();
      
      /* Initialize Player */
      Player player = new Player("Asep", Gender.MALE, "Asep's diary");
      nameDisplay.setText(player.getName());
      farm = new Farm(player);

      HealthBarUpdater healthBarUpdater =  new HealthBarUpdater(energyDisplay, energyBar);
      healthBarUpdater.updateHealthBar(player.getEnergy(), player.getMAX_ENERGY());
      player.setHealthBarUpdater(healthBarUpdater);

      
      /* Initialize Contoller */
      assetController = new AssetController(player);
      collisionController = new CollisionController();
      gameMapController = new GameMapController(player);
      playerController = new PlayerController(player, collisionController, this);
      timeController = new TimeController(farm, timeDisplay, dateDisplay);
      // assetController.getAssets().add(new Asset(20 * Constants.TILE_SIZE, 20 * Constants.TILE_SIZE, "/images/monyet.jpg", true));
      // assetController.getAssets().add(new Teleporter(23 * Constants.TILE_SIZE, 13 * Constants.TILE_SIZE, Constants.TILE_SIZE * 3, Constants.TILE_SIZE * 3));
      
      timeController.render();
      
      /* Initialize NPC */
      mayorTadi = new MayorTadi();
      abigail = new Abigail();
      caroline = new Caroline();  
      emily = new Emily();
      dasco = new Dasco();
      perry = new Perry();
      
      
      
      /* <------------------------------------------SEPERATOR------------------------------------------------> */
      
        AnimationTimer gameTime = new AnimationTimer() {
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
            
            
            renderNPCs();
            
            collisionController.checkAssetCollision(assetController.getAssets(),playerController);
            assetController.render(gc);
            playerController.render(gc);
            
            applyTimeOfDayLighting();
            
          }
        };
        
        /* Delay until scene is ready */
        Platform.runLater(() -> {
          scene = hud.getParent().getScene();
          gc = canvas.getGraphicsContext2D();
          gc.setImageSmoothing(false);
          playerController.keyMapper(scene);
          
          timeController.render();
          
          gameTime.start();
        });
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

    /* -------------------------------------------------------------------------- */
    /*                              Render NPC Logics                             */
    /* -------------------------------------------------------------------------- */

    private void renderNPCs() {
        int x_player = farm.getPlayer().getX();
        int y_player = farm.getPlayer().getY();

        renderNPC(mayorTadi, x_player, y_player);
        renderNPC(abigail, x_player, y_player);
        renderNPC(caroline, x_player, y_player);
        renderNPC(emily, x_player, y_player);
        renderNPC(dasco, x_player, y_player);
        renderNPC(perry, x_player, y_player);
    }

    private void renderNPC(NPC NPCname, int x_player, int y_player) {
        Canvas canvas = gc.getCanvas();
        double canvasWidth = canvas.getWidth();
        double canvasHeight = canvas.getHeight();
        int playerScreenX = (int) (canvasWidth / 2) - (Constants.TILE_SIZE / 2);
        int playerScreenY = (int) (canvasHeight / 2) - (Constants.TILE_SIZE / 2);
        int x_screen = NPCname.getLocation().x - x_player + playerScreenX;
        int y_screen = NPCname.getLocation().y - y_player + playerScreenY;

        if (x_screen > -128 && x_screen < canvas.getWidth() + 128 && y_screen > -128 && y_screen < canvas.getHeight() + 128) {
            gc.drawImage(NPCname.NPCSpriteSheet, NPC.sourceX(), NPC.sourceY(), NPC.NPCFrameWidth, NPC.NPCFrameHeight, x_screen, y_screen, Constants.TILE_SIZE, Constants.TILE_SIZE);
        }
    }


/* <------------------------------------------SEPERATOR------------------------------------------------> */

    /* -------------------------------------------------------------------------- */
    /*                              Inventory Logics                              */
    /* -------------------------------------------------------------------------- */

    public PlayerController getPlayerController() {
      return playerController;
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
                inventoryPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.8); -fx-border-color: white; -fx-border-width: 2px;");
                
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

    public Label getInteractionNotification() {
      return interactionNotification;
    }

    


/* <------------------------------------------SEPERATOR------------------------------------------------> */



}