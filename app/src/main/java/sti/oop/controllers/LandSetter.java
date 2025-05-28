package sti.oop.controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import sti.oop.models.Asset;
import sti.oop.models.Land;
import sti.oop.utils.Constants;

public class LandSetter {
  Image tilledLandImage = new Image(getClass().getResourceAsStream("/land/plantable.png"));
  Image plantedLandImage = new Image(getClass().getResourceAsStream("/land/planted.png"));
  Image harvestableLandImage = new Image(getClass().getResourceAsStream("/land/harvestable.png"));

  public List<Asset> setLandOnFarm() {
    List<Asset> listOfLands = new ArrayList<>();
    for (int row = 9; row < 40; row++) {
      for (int col= 9; col < 40; col++) {
        listOfLands.add(new Land(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE, tilledLandImage, plantedLandImage, harvestableLandImage));
      }
    }
    return listOfLands;
  }
}
