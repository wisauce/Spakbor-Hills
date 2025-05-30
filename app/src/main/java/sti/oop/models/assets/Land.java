package sti.oop.models.assets;

import javafx.scene.image.Image;
import sti.oop.action.Action;
import sti.oop.interfaces.EnergyConsuming;
import sti.oop.models.Interactable;
import sti.oop.models.Weather;
import sti.oop.models.item.Equipment;
import sti.oop.models.item.Item;
import sti.oop.models.item.Seed;

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
  private Image plantedAndWateredLandImage;

  private LandState state;

  private Item requiredForTIlling = new Equipment("Hoe");
  private Item requiredForLandRecovery = new Equipment("Pickaxe");
  private Item requiredForWatering = new Equipment("Watering Can");

  private final int MAX_DAYS_NOT_WATERED = 2;

  private Seed seed;
  private int daysToHarvest;
  private int daysPassed;
  private int daysNotWatered;
  private boolean isTodayWatered;

  private int energyRequired = 5;

  public Land(int x, int y, Image tilledLandImage, Image plantedLandImage, Image harvestableLandImage, Image plantedAndWateredLandImage) {
    super(x, y, false);
    state = LandState.TILLABLE_LAND;
    this.tilledLandImage = tilledLandImage;
    this.plantedLandImage = plantedLandImage;
    this.harvestableLandImage = harvestableLandImage;
    this.plantedAndWateredLandImage = plantedAndWateredLandImage;
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

  public void updateStateOfPlantedLand(Weather weather) {
    if (state.equals(LandState.PLANTED_LAND)) {
      if (weather.equals(Weather.RAINY)) {
        daysNotWatered = 0;
        setImage(plantedAndWateredLandImage);
        isTodayWatered = true;
      } else {
        if (!isTodayWatered) {
          daysNotWatered++;
        } else {
          setImage(plantedLandImage);
          daysNotWatered = 0;
        }
        isTodayWatered = false;
      }
      daysPassed++;
      System.out.println("daystoharvest" + daysToHarvest);
      System.out.println("daysPassed" + daysPassed);
      System.out.println(state);
      if (daysPassed == daysToHarvest) {
        changeLandState(LandState.HARVESTABLE_LAND);
        daysNotWatered = 0;
        daysPassed = 0;
        daysToHarvest = 0;
      } else if (daysNotWatered > MAX_DAYS_NOT_WATERED) {
        changeLandState(LandState.TILLED_LAND);
        seed = null;
        daysNotWatered = 0;
        daysPassed = 0;
        daysToHarvest = 0;
      }
    }
  }

  

  public void setTodayWatered(boolean isTodayWatered) {
    this.isTodayWatered = isTodayWatered;
    if (isTodayWatered) setImage(plantedAndWateredLandImage);
  }

  @Override
  public int getEnergyRequired() {
    return energyRequired;
  }

  @Override
  public boolean multipleInput() {
    return false;
  }

  public void setDaysToHarvest(int daysToHarvest) {
    this.daysToHarvest = daysToHarvest;
  }

  public boolean isTodayWatered() {
    return isTodayWatered;
  }

  

  

  // JANGNA LUPA BIKIN LOGIC BUAT BIAR TIAP GANTI HARI DAY NOT WATERED NAIK + KALO
  // UJAN KERESET

  // JANGAN LUPA BIKIN LOGIC BIAR KALO UDH HARVEST DAY NANTI KESET SENDIRI CROPNYA
  // JADI ADA DAN BISA DIHARVEST
}
