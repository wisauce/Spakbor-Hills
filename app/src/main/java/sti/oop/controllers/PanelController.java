package sti.oop.controllers;

import java.io.IOException;
import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PanelController {
  @FXML
  private StackPane bottomPanel;

  @FXML
  private HBox buttonPanel;

  @FXML
  private VBox dialogBox;

  @FXML
  private Label dialogLabel;

  private Timeline currentTimeline;

  private FishingPanelController fishingPanelController;

  @FXML
  public void initialize() throws IOException {
    FXMLLoader fishingLoader = new FXMLLoader(getClass().getResource("/views/FishingPanel.fxml"));
    Parent fishingRoot = fishingLoader.load();
    fishingPanelController = fishingLoader.getController();

    // Tambahkan fishing panel ke bottomPanel atau container lain
    bottomPanel.getChildren().add(fishingRoot);
  }

  public void showDialog(String message) {
    if (message != null) {
      if ("FISH".equals(message)) {
        // Tampilkan fishing panel
        fishingPanelController.getRootFishing().setVisible(true);
        fishingPanelController.getRootFishing().setManaged(true);

        // Sembunyikan dialog dan button panel
        dialogBox.setVisible(false);
        dialogBox.setManaged(false);

        buttonPanel.setVisible(false);
        buttonPanel.setManaged(false);

        // Hentikan timeline jika ada
        if (currentTimeline != null) {
          currentTimeline.stop();
          currentTimeline = null;
        }
      } else {
        // Tampilkan dialog biasa
        dialogLabel.setText(message);
        dialogLabel.setWrapText(true);

        dialogBox.setVisible(true);
        dialogBox.setManaged(true);

        buttonPanel.setVisible(false);
        buttonPanel.setManaged(false);

        // Sembunyikan fishing panel
        if (fishingPanelController != null) {
          fishingPanelController.getRootFishing().setVisible(false);
          fishingPanelController.getRootFishing().setManaged(false);
        }

        if (currentTimeline != null) {
          currentTimeline.stop();
        }

        currentTimeline = new Timeline(new KeyFrame(
            Duration.seconds(3),
            event -> {
              dialogBox.setVisible(false);
              dialogBox.setManaged(false);
              currentTimeline = null;
            }));
        currentTimeline.setCycleCount(1);
        currentTimeline.play();
      }
    }
  }

  public void showButtons(List<Button> buttons) {
    buttonPanel.getChildren().clear();
    buttonPanel.getChildren().addAll(buttons);

    buttonPanel.setVisible(true);
    buttonPanel.setManaged(true);

    dialogBox.setVisible(false);
    dialogBox.setManaged(false);

    if (currentTimeline != null) {
      currentTimeline.stop();
      currentTimeline = null;
    }
  }

  public void hideAllBottom() {
    dialogBox.setVisible(false);
    dialogBox.setManaged(false);
    buttonPanel.setVisible(false);
    buttonPanel.setManaged(false);

    if (currentTimeline != null) {
      currentTimeline.stop();
      currentTimeline = null;
    }
  }

  public FishingPanelController getFishingPanelController() {
    return fishingPanelController;
  }

  
}
