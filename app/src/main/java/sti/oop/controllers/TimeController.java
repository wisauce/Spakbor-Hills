package sti.oop.controllers;

import javafx.scene.control.Label;
import  sti.oop.models.Farm;

public class TimeController {
  private Farm farm;
  private Label timeDisplay;
  private Label dateDisplay;
  

  public TimeController(Farm farm, Label timeDisplay, Label dateDisplay) {
    this.farm = farm;
    this.timeDisplay = timeDisplay;
    this.dateDisplay = dateDisplay;
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