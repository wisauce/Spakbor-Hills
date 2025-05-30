package sti.oop.controllers;

import java.util.ArrayList;
import java.util.List;

import javafx.scene.image.Image;
import sti.oop.models.assets.Asset;
import sti.oop.models.assets.Land;
import sti.oop.models.assets.Land.LandState;
import sti.oop.utils.Constants;

public class LandSetter {
  Image tilledLandImage = new Image(getClass().getResourceAsStream("/land/plantable.png"));
  Image plantedLandImage = new Image(getClass().getResourceAsStream("/land/planted.png"));
  Image harvestableLandImage = new Image(getClass().getResourceAsStream("/land/harvestable.png"));
  Image plantedAndWaterdLandImage = new Image(getClass().getResourceAsStream("/land/plantedAndWatered.png"));

  public List<Asset> setLandOnFarm() {
    List<Asset> listOfLands = new ArrayList<>();
    for (int row = 9; row < 41; row++) {
      for (int col= 9; col < 41; col++) {
        listOfLands.add(new Land(col * Constants.TILE_SIZE, row * Constants.TILE_SIZE, tilledLandImage, plantedLandImage, harvestableLandImage, plantedAndWaterdLandImage));
      }
    }
    return listOfLands;
  }

  public void updatePlantedLandState(List<Land> lands) {
    for (Land land : lands) {
      if (land.getState().equals(LandState.PLANTED_LAND)) {

      }
    }
  }
}