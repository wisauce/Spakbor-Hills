package sti.oop.controllers;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PanelController {
  StackPane bottomPanel;
  HBox buttonPanel;
  VBox dialogBox;

  public PanelController(StackPane bottomPanel, HBox buttonPanel, VBox dialogBox) {
    this.bottomPanel = bottomPanel;
    this.buttonPanel = buttonPanel;
    this.dialogBox = dialogBox;
  }

  public void showDialog(String message) {
    if (message != null) {
      Node first = dialogBox.getChildren().get(0); // Ambil label teks utama

      if (first instanceof Label dialogText) {
        dialogText.setText(message);
        dialogText.setWrapText(true); // just in case
      }

      dialogBox.setVisible(true);
      dialogBox.setManaged(true);

      buttonPanel.setVisible(false);
      buttonPanel.setManaged(false);

      Timeline timeline = new Timeline(new KeyFrame(
        Duration.seconds(3),
        event -> {
          dialogBox.setVisible(false);
          dialogBox.setManaged(false);
        }
      ));
      timeline.setCycleCount(1);
      timeline.play();
    }
  }

  public void showButtons(List<Button> buttons) {
    buttonPanel.getChildren().clear();
    buttonPanel.getChildren().addAll(buttons);

    buttonPanel.setVisible(true);
    buttonPanel.setManaged(true);

    dialogBox.setVisible(false);
    dialogBox.setManaged(false);
  }

  public void hideAllBottom() {
    dialogBox.setVisible(false);
    dialogBox.setManaged(false);
    buttonPanel.setVisible(false);
    buttonPanel.setManaged(false);
  }
}
