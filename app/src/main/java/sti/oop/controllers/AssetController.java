package sti.oop.controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import sti.oop.models.Asset;
import sti.oop.models.Player;
import sti.oop.utils.Constants;

public class AssetController {
  List<Asset> assets;
  Player player;

  public AssetController(Player player) {
    assets = new ArrayList<>();
    this.player = player;
  }

  public List<Asset> getAssets() {
    return assets;
  }

  public void render(GraphicsContext gc) {
    Canvas canvas = gc.getCanvas();
    double canvasWidth = canvas.getWidth();
    double canvasHeight = canvas.getHeight();
    int playerScreenX = (int) (canvasWidth / 2) - (Constants.TILE_SIZE / 2);
    int playerScreenY = (int) (canvasHeight / 2) - (Constants.TILE_SIZE / 2);
    int assetScreenX;
    int assetScreenY;
    for (Asset asset : assets) {
      assetScreenX = asset.getX() - player.getX() + playerScreenX;
      assetScreenY = asset.getY() - player.getY() + playerScreenY;
      asset.updateSolidArea();
      gc.drawImage(asset.getImage(), assetScreenX, assetScreenY, Constants.TILE_SIZE, Constants.TILE_SIZE);
    }
  }
}