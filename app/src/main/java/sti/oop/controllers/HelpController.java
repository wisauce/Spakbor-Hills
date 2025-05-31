package sti.oop.controllers;

import java.io.IOException;
import java.util.List;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

public class HelpController {

  @FXML
  private ImageView imageViewer;

  @FXML
  private Label pageLabel;

  @FXML
  private Button prevButton;

  @FXML
  private Button nextButton;

  private List<Image> images;
  private int currentIndex = 0;

  @FXML
  public void initialize() {
    images = List.of(
        new Image(getClass().getResource("/help/1.png").toExternalForm()),
        new Image(getClass().getResource("/help/2.png").toExternalForm()),
        new Image(getClass().getResource("/help/3.png").toExternalForm()),
        new Image(getClass().getResource("/help/4.png").toExternalForm()),
        new Image(getClass().getResource("/help/5.png").toExternalForm()),
        new Image(getClass().getResource("/help/6.png").toExternalForm()),
        new Image(getClass().getResource("/help/7.png").toExternalForm()));
    updateViewer();
  }

  @FXML
  private void onPrevClick() {
    if (currentIndex > 0) {
      currentIndex--;
      updateViewer();
    }
  }

  @FXML
  private void onNextClick() {
    if (currentIndex < images.size() - 1) {
      currentIndex++;
      updateViewer();
    }
  }

  private void updateViewer() {
    imageViewer.setImage(images.get(currentIndex));
    pageLabel.setText((currentIndex + 1) + " dari " + images.size());

    // Optional: disable buttons when at start/end
    prevButton.setDisable(currentIndex == 0);
    nextButton.setDisable(currentIndex == images.size() - 1);
  }

  @FXML
  private void onBackToMenuClick(ActionEvent event) throws IOException {
    FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/MainMenu.fxml"));
    Parent root = loader.load();
    Scene scene = (Scene) ((Node) event.getSource()).getScene();
    scene.setRoot(root);
  }

}
