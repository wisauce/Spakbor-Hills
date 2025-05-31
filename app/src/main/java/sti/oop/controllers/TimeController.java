package sti.oop.controllers;

import javafx.scene.control.Label;
import  sti.oop.models.Farm;

public class TimeController {
  private Farm farm;
  private Label timeDisplay;
  private Label dateDisplay;
  private Label locationDisplay;
  private FarmController farmController;
  

  public TimeController(Farm farm, FarmController farmController, Label timeDisplay, Label dateDisplay, Label locationDisplay) {
    this.farm = farm;
    this.farmController = farmController;
    this.timeDisplay = timeDisplay;
    this.dateDisplay = dateDisplay;
    this.locationDisplay = locationDisplay;
  }

  public void update() {
   farm.updateTime();
  }

  public void render() {
    if (timeDisplay != null) {
      timeDisplay.setText(farm.getFormatTimeNow());
    }
    if (dateDisplay != null) {
      dateDisplay.setText(farm.getFormatDateMonthSeason());
    }
    if (locationDisplay != null) {
      locationDisplay.setText(farmController.getGameMapController().getCurrentMap().toString());
    }
  }

  public void setTimeFrozen(boolean freeze) {
    farm.setTimeFrozen(freeze);
  }

  public boolean isTimeFrozen() {
    return farm.isTimeFrozen();
  }

  public Farm getFarm() {
    return farm;
  }

  
}