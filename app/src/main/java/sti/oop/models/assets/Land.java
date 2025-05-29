package sti.oop.models.assets;

import javafx.scene.image.Image;
import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Interactable;
import sti.oop.action.Action;
import sti.oop.models.Item.Crop;
import sti.oop.models.Item.Equipment;
import sti.oop.models.Item.Item;
import sti.oop.models.Item.Seed;

public class Land extends Asset implements Interactable, EnergyConsuming {
  public enum LandState {
    TILLABLE_LAND,
    TILLED_LAND,
    PLANTED_LAND,
    HARVESTABLE_LAND
  }
  private Image tillableLandImage = null;
  private Image tilledLandImage;
  private Image plantedLandImage;
  private Image harvestableLandImage;

  private LandState state;

  private Item requiredForTIlling = new Equipment("Hoe");
  private Item requiredForLandRecovery = new Equipment("Pickaxe");
  private Item requiredForWatering = new Equipment("Watering Can");

  private Seed seed;
  private Crop crop;
  private int daysNotWatered;

  private int energyRequired = 5;

  public Land(int x, int y, Image tilledLandImage, Image plantedLandImage, Image harvestableLandImage) {
    super(x, y, false);
    state = LandState.TILLABLE_LAND;
    this.tilledLandImage = tilledLandImage;
    this.plantedLandImage = plantedLandImage;
    this.harvestableLandImage = harvestableLandImage;
  }

  public void changeLandState(LandState state) {
    if (state.equals(LandState.TILLABLE_LAND)) {
      setImage(tillableLandImage);
    } else if (state.equals(LandState.TILLED_LAND)) {
      setImage(tilledLandImage);
    } else if (state.equals(LandState.PLANTED_LAND)) {
      setImage(plantedLandImage);
    } else if (state.equals(LandState.HARVESTABLE_LAND)) {
      setImage(harvestableLandImage);

    }
    System.out.println("change state from " + this.state + " to " + state);
    this.state = state;
  }

  @Override
  public String accept(Action action)  {
    return action.act(this);
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

  @Override
  public int getEnergyRequired() {
    return energyRequired;
  }

  @Override
  public boolean multipleInput() {
    return false;
  }

  


  // JANGNA LUPA BIKIN LOGIC BUAT BIAR TIAP GANTI HARI DAY NOT WATERED NAIK + KALO UJAN KERESET 
  

  // JANGAN LUPA BIKIN LOGIC BIAR KALO UDH HARVEST DAY NANTI KESET SENDIRI CROPNYA JADI ADA DAN BISA DIHARVEST
}
