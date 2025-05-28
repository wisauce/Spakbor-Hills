package sti.oop.controllers;

import java.io.IOException;

import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.input.MouseEvent;

import javafx.stage.Stage;
import javafx.util.Duration;

public class MainMenuController {

  @FXML
  private Label guideText;

  @FXML
  void onAnywhereClick(MouseEvent event) throws IOException {
    Parent farmParent = FXMLLoader.load(getClass().getResource("/views/Farm.fxml"));
    Scene farmScene = new Scene(farmParent);

    Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
    stage.setScene(farmScene);
    stage.setFullScreen(true);
    stage.show();

  }

  @FXML
  public void initialize() {
    FadeTransition fade = new FadeTransition(Duration.millis(1000), guideText);
    fade.setCycleCount(FadeTransition.INDEFINITE);
    fade.setInterpolator(Interpolator.LINEAR);
    fade.setFromValue(1);
    fade.setToValue(0);
    fade.setAutoReverse(true);
    fade.play();
  }
}
