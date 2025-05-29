package sti.oop.controllers;

import java.util.List;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;

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
    dialogBox.getChildren().clear(); // Kosongkan dulu kalau ada text sebelumnya

    Label dialogText = new Label(message);
    dialogText.setWrapText(true);
    dialogBox.getChildren().add(dialogText);

    dialogBox.setVisible(true);
    dialogBox.setManaged(true); // supaya layout ikut menyesuaikan saat muncul

    buttonPanel.setVisible(false);
    buttonPanel.setManaged(false);
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
