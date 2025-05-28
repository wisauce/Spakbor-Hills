package sti.oop.models;

import javafx.scene.image.Image;
import sti.oop.models.Item.Crop;
import sti.oop.models.Item.Equipment;
import sti.oop.models.Item.Item;
import sti.oop.models.Item.Seed;

public class Land extends Asset implements Interactable {
  public enum LandState {
    TILLABLE_LAND,
    TILLED_LAND,
    PLANTED_LAND,
    HARVESTABLE_LAND
  }
  Image tillableLandImage = null;
  Image tilledLandImage = new Image(getClass().getResourceAsStream("/land/plantable.png"));
  Image plantedLandImage = new Image(getClass().getResourceAsStream("/land/planted.png"));
  Image harvestableLandImage = new Image(getClass().getResourceAsStream("/land/harvestable.png"));

  LandState state;

  Item requiredForTIlling = new Equipment("Hoe");
  Item requiredForLandRecovery = new Equipment("Pickaxe");
  Item requiredForWatering = new Equipment("Watering Can");

  Seed seed;
  Crop crop;
  int daysNotWatered;

  public Land(int x, int y, Image tilledLandImage, Image plantedLandImage, Image harvestableLandImage) {
    super(x, y, false);
    state = LandState.TILLABLE_LAND;
  }

  public void changeLandState(LandState state) {
    if (state.equals(LandState.TILLABLE_LAND)) {
      setImage(tillableLandImage);
    }
    if (state.equals(LandState.TILLED_LAND)) {
      setImage(tilledLandImage);
    }
    if (state.equals(LandState.PLANTED_LAND)) {
      setImage(plantedLandImage);
    }
    if (state.equals(LandState.HARVESTABLE_LAND)) {
      setImage(harvestableLandImage);

    }
    System.out.println("change state from " + this.state + " to " + state);
    this.state = state;
  }

  @Override
  public void accept(Action action) {
    action.act(this);
  }

  public LandState getState() {
    return state;
  }

  public Item getRequiredForTIlling() {
    return requiredForTIlling;
  }

  public Item getRequiredForLandRecovery() {
    return requiredForLandRecovery;
  }

  public Item getRequiredForWatering() {
    return requiredForWatering;
  }

  public Seed getSeed() {
    return seed;
  }

  public void setSeed(Seed seed) {
    this.seed = seed;
  }

  public int getDaysNotWatered() {
    return daysNotWatered;
  }

  public void setDaysNotWatered(int daysNotWatered) {
    this.daysNotWatered = daysNotWatered;
  }

  public Crop getCrop() {
    return crop;
  }

  public void setCrop(Crop crop) {
    this.crop = crop;
  }

  


  // JANGNA LUPA BIKIN LOGIC BUAT BIAR TIAP GANTI HARI DAY NOT WATERED NAIK + KALO UJAN KERESET 
  

  // JANGAN LUPA BIKIN LOGIC BIAR KALO UDH HARVEST DAY NANTI KESET SENDIRI CROPNYA JADI ADA DAN BISA DIHARVEST
}
