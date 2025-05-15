package sti.oop.controllers;

import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.BorderPane;
import sti.oop.models.Farm;
import sti.oop.models.Player;
import sti.oop.models.Player.Gender;

public class FarmController {
  @FXML
  private BorderPane hud;

  @FXML
  private Canvas canvas;

  private Scene scene;
  private GraphicsContext gc;

  private Farm farm;
  private Player player;
  private MapController mapController;
  private PlayerController playerController;
  private PlayerRenderer playerRenderer;


  public void render() {
    gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
    mapController.render(player.getX(), player.getY(), playerRenderer.getScreenX(), playerRenderer.getScreenY());
    playerController.playerUpdate();
  }
  
  @FXML
  public void initialize() {
    player = new Player("Asep", Gender.MALE, "Asep's diary", 50, 4);
    farm = new Farm(player);
    Platform.runLater(()-> {
      scene = hud.getScene();
      gc = canvas.getGraphicsContext2D();
      playerRenderer = new PlayerRenderer(gc);
      playerController = new PlayerController(scene, player, playerRenderer);
      mapController = new MapController(gc,"/tileSheet/farm/cek2.png","/tileSheet/farm/pond.txt", 128 );
      
      playerController.inputMovementHandler();

      AnimationTimer gameTime = new AnimationTimer() {
        @Override
        public void handle(long now) {
          render();
        }
      };
  
      gameTime.start();
    });
  }
}