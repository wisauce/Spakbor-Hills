package sti.oop.controllers;

import java.io.IOException;

import javafx.application.Platform;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;

public class MainMenuController {

  @FXML
  private Button btnNewGame;

  @FXML
  private Button btnHelp;

  @FXML
  private Button btnCredits;

  @FXML
  private Button btnExit;

  @FXML
  private void handleNewGame(Event event) throws IOException {
    Parent farmParent = FXMLLoader.load(getClass().getResource("/views/Farm.fxml"));
    Scene scene = (Scene) ((Node) event.getSource()).getScene();
    scene.setRoot(farmParent);
  }

  @FXML
  private void handleHelp() {
    System.out.println("Help clicked!");
    // TODO: tampilkan dialog/help scene
  }

  @FXML
  private void handleCredits(Event event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Credit.fxml"));
    Scene scene = (Scene) ((Node) event.getSource()).getScene();
    Parent farmParent = loader.load();
    scene.setRoot(farmParent);
  }

  @FXML
  private void handleExit() {
    System.out.println("Exit clicked!");
    // Keluar aplikasi
    Platform.exit();
  }
}
