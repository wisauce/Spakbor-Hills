package sti.oop.controllers;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Label;

public class TimeController {
  private int inGameHour = 6;
  private int inGameMinute = 0;
  private String timeOfDay = "AM";
  private boolean timeFrozen = false;
  private final int MINUTES_PER_SECOND = 5; 
  private Label timeDisplay;

  public TimeController(int inGameHour, int inGameMinute, String timeOfDay, Label timeDisplay) {
    this.inGameHour = inGameHour;
    this.inGameMinute = inGameMinute;
    this.timeOfDay = timeOfDay;
    this.timeDisplay = timeDisplay;
  }

  public void update() {
    if (timeFrozen) return;
    inGameMinute += MINUTES_PER_SECOND;
    if (inGameMinute >= 60) {
      inGameHour += inGameMinute / 60;
      inGameMinute %= 60;

      if (inGameHour == 12) {
        if (timeOfDay.equals("AM")) {
          timeOfDay = "PM";
        } else {
          timeOfDay = "AM";
        }
      } else if (inGameHour > 12) {
        inGameHour %= 12;
        if (inGameHour == 0) {
          inGameHour = 12;
        }
      }
    }
  }

  public void render(GraphicsContext gc) {
    timeDisplay.setText(String.format("%d:%02d %s", inGameHour, inGameMinute, timeOfDay));
  }

  public void setTimeFrozen(boolean freeze) {
    timeFrozen = freeze;
  }

  public boolean isTimeFrozen() {
    return timeFrozen;
  }
}