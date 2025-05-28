package sti.oop.controllers;

import javafx.css.PseudoClass;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import sti.oop.models.Player;

public class HealthBarUpdater {
  Label energyDisplay;
  ProgressBar energyBar;

  

  public HealthBarUpdater(Label energyDisplay, ProgressBar energyBar) {
    this.energyDisplay = energyDisplay;
    this.energyBar = energyBar;
  }



  public void updateHealthBar(int currentEnergy, int maxEnergy) {
    double progress = (double) currentEnergy / maxEnergy;
    energyBar.setProgress(progress);
    energyDisplay.setText(String.format("%d/%d", currentEnergy, maxEnergy));

    // Optional: Set pseudo-class kalau mau ubah warna CSS berdasarkan kondisi
    boolean isLow = progress < 0.25;
    energyBar.pseudoClassStateChanged(PseudoClass.getPseudoClass("percentage-low"), isLow);
  }
}