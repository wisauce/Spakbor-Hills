package sti.oop.controllers;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;

// Define a functional interface for the callback (if still needed for data processing before transition)
@FunctionalInterface
interface CharacterCreationListener {
  void onCharacterCreated(String playerName, String selectedGenderOption, String farmName);
}

public class SetupController {

  @FXML
  private VBox rootPane; // The root VBox of this CharacterCreation.fxml

  @FXML
  private TextField playerNameField;

  @FXML
  private ToggleGroup genderToggleGroup;

  @FXML
  private RadioButton maleRadioButton;

  @FXML
  private RadioButton femaleRadioButton;

  @FXML
  private TextField farmNameField;

  @FXML
  private Button confirmButton;

  private CharacterCreationListener onConfirmListener;

  @FXML
  public void initialize() {
    if (maleRadioButton != null) {
      maleRadioButton.setSelected(true);
    }
  }

  public void setOnConfirmListener(CharacterCreationListener listener) {
    this.onConfirmListener = listener;
  }

  @FXML
  private Label playerNameWarning;
  @FXML
  private Label genderWarning;
  @FXML
  private Label farmNameWarning;

  @FXML
  private void handleConfirmAction(ActionEvent event) {
    // Clear previous warnings
    playerNameWarning.setText("");
    genderWarning.setText("");
    farmNameWarning.setText("");

    String playerName = playerNameField.getText().trim();
    String farmName = farmNameField.getText().trim();
    RadioButton selectedGenderRadio = (RadioButton) genderToggleGroup.getSelectedToggle();
    String selectedGenderOptionText = selectedGenderRadio != null ? selectedGenderRadio.getText() : "";

    boolean hasError = false;

    if (playerName.isEmpty()) {
      playerNameWarning.setText("Please enter your farmer's name.");
      playerNameField.requestFocus();
      hasError = true;
    }

    if (selectedGenderRadio == null) {
      genderWarning.setText("Please choose an appearance option.");
      hasError = true;
    }

    if (farmName.isEmpty()) {
      farmNameWarning.setText("Every farm needs a name!");
      if (!hasError)
        farmNameField.requestFocus(); // only shift focus if no previous error
      hasError = true;
    }

    if (hasError)
      return;

    // For debugging or immediate feedback
    System.out.println("Character Details Confirmed:");
    System.out.println("Player Name: " + playerName);
    System.out.println("Appearance/Gender Option: " + selectedGenderOptionText);
    System.out.println("Farm Name: " + farmName);

    // If a listener is set, invoke it. This is where you'd typically
    // initialize your Player object and Farm object in your main game logic.
    if (onConfirmListener != null) {
      onConfirmListener.onCharacterCreated(playerName, selectedGenderOptionText, farmName);
    } else {
      System.out.println("No listener set, but proceeding to load farm screen.");
      // If no listener, you might still want to store this data somewhere globally
      // or pass it to the Farm.fxml's controller if it needs it.
    }

    // Now, load Farm.fxml and replace the scene content
    try {
      // Assuming Farm.fxml is in the same /views/ directory
      FXMLLoader loader = new FXMLLoader(getClass().getResource("/views/Farm.fxml"));
      Parent farmRoot = loader.load();
      FarmController farmController = loader.getController();
      if (farmController != null) {
        farmController.initializePlayerData(playerName, selectedGenderOptionText, farmName);
      }

      // Get the current scene from any node within the current FXML (e.g., rootPane)
      Scene currentScene = rootPane.getScene();
      if (currentScene != null) {
        // Replace the root of the current scene with the new farmRoot
        // This assumes your application's main stage is showing 'currentScene'
        // and you want to swap its content.
        currentScene.setRoot(farmRoot);

        // Optional: If Farm.fxml's controller needs initialization with character data,
        // you would get its controller here and pass the data.
        // FarmController farmController = loader.getController();
        // if (farmController != null) {
        // farmController.initializePlayerData(playerName, selectedGenderOptionText,
        // farmName);
        // }

      } else {
        System.err.println("Could not get current scene to replace its root.");
        showAlert("Navigation Error", "Could not load the farm screen.");
      }

    } catch (IOException e) {
      e.printStackTrace();
      showAlert("Error", "Failed to load the farm screen: " + e.getMessage());
    }
  }

  private void showAlert(String title, String message) {
    Alert alert = new Alert(AlertType.INFORMATION);
    alert.setTitle(title);
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }
}
