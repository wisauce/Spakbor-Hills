// PanelController.java
package sti.oop.controllers;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.OptionalInt; // For a clear way to return no result or a result
import java.util.function.Consumer;
import java.util.function.UnaryOperator;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.util.Duration;

public class PanelController {

  @FXML
  private StackPane bottomPanel; // Main container where different panels are shown

  @FXML
  private HBox buttonPanel; // For NPC options etc.

  @FXML
  private VBox dialogBox; // For text dialogs
  @FXML
  private Label dialogLabel;

  private Timeline currentDialogTimeline;

  // --- Fishing Panel UI Elements (Loaded from FXML) ---
  private VBox fishingRootNode; // The root VBox from FishingPanel.fxml
  private TextField fishingNumberInput;
  private Button fishingConfirmButton;
  private Label fishingPromptLabel; // Assuming you add an fx:id for this in FXML

  // --- State for the Fishing Guessing Loop ---
  private int targetNumber;
  private int maxAttempts;
  private int currentAttempts;
  private Consumer<OptionalInt> currentFishingCallback; // OptionalInt for clear success/failure
  private int fishingMinRange;
  private int fishingMaxRange;

  @FXML
  public void initialize() {
    // Load the fishing panel FXML and get its nodes
    try {
      FXMLLoader fishingLoader = new FXMLLoader(getClass().getResource("/views/FishingPanel.fxml"));
      fishingRootNode = fishingLoader.load(); // fishingRootNode is the VBox with fx:id="rootFishing"

      // Find nodes within the loaded FXML. Make sure fx:id are set in your FXML.
      fishingNumberInput = (TextField) fishingRootNode.lookup("#numberInput");
      fishingConfirmButton = (Button) fishingRootNode.lookup("#confirmButton");
      // Add a Label for prompts/feedback in your FishingPanel.fxml with
      // fx:id="fishingPromptLabel"
      fishingPromptLabel = (Label) fishingRootNode.lookup("#fishingPromptLabel");

      if (fishingNumberInput == null || fishingConfirmButton == null || fishingPromptLabel == null) {
        System.err.println("Error: Could not find all required nodes in FishingPanel.fxml. Check fx:id's.");
        return;
      }

      // Initial setup (hide it)
      fishingRootNode.setVisible(false);
      fishingRootNode.setManaged(false);
      bottomPanel.getChildren().add(fishingRootNode); // Add it to the main panel container

    } catch (IOException e) {
      e.printStackTrace();
      // Handle FXML loading error
    }
  }

  public void showDialog(String message) {
    if (message == null || message.trim().isEmpty()) {
      return;
    }
    hideAllBottomPanels(); // Hide other panels before showing dialog
    bottomPanel.setVisible(true);
    dialogLabel.setText(message);
    dialogLabel.setWrapText(true);

    dialogBox.setVisible(true);
    dialogBox.setManaged(true);

    if (currentDialogTimeline != null) {
      currentDialogTimeline.stop();
    }
    currentDialogTimeline = new Timeline(new KeyFrame(
        Duration.seconds(3),
        event -> {
          dialogBox.setVisible(false);
          dialogBox.setManaged(false);
          currentDialogTimeline = null;
        }));
    currentDialogTimeline.setCycleCount(1);
    currentDialogTimeline.play();
  }

  public void showButtons(List<Button> buttons) {
    hideAllBottomPanels();
    bottomPanel.setVisible(true);
    buttonPanel.getChildren().clear();
    buttonPanel.getChildren().addAll(buttons);
    buttonPanel.setVisible(true);
    buttonPanel.setManaged(true);
  }

  public void hideAllBottomPanels() { // Renamed for clarity
    dialogBox.setVisible(false);
    dialogBox.setManaged(false);

    bottomPanel.setVisible(false);

    buttonPanel.getChildren().clear();
    buttonPanel.setVisible(false);
    buttonPanel.setManaged(false);

    if (fishingRootNode != null) {
      fishingRootNode.setVisible(false);
      fishingRootNode.setManaged(false);
    }

    if (currentDialogTimeline != null) {
      currentDialogTimeline.stop();
      currentDialogTimeline = null;
    }
  }

  // --- NPC Interaction Panel (Example from before, ensure it uses
  // hideAllBottomPanels) ---
  public void multipleOptionPanel(List<String> options, Consumer<String> callback) {
    hideAllBottomPanels();
    List<Button> buttons = new ArrayList<>();

    if (options == null || options.isEmpty()) {
      showDialog("No options available for ");
      return;
    }

    for (String option : options) {
      Button optionButton = new Button(option);
      optionButton.getStyleClass().add("stardew-panel");
      optionButton.getStyleClass().add("dialog-text");
      optionButton.setOnAction(e -> {
        hideAllBottomPanels(); // Hide options panel
        callback.accept(option);
      });
      buttons.add(optionButton);
    }
    showButtons(buttons); // This now makes buttonPanel visible
  }

  // --- New Fishing Guessing Game Logic ---

  /**
   * Shows the fishing input panel and starts a guessing game.
   *
   * @param prompt         The initial prompt for the player.
   * @param dynamicTarget  The number the player needs to guess.
   * @param minRange       The minimum value for input.
   * @param maxRange       The maximum value for input.
   * @param attempts       Number of retry attempts allowed.
   * @param resultCallback Callback with OptionalInt.empty() if failed/cancelled,
   *                       or OptionalInt.of(guessedNumber) on success.
   */
  public void showFishingGuessingGame(String prompt, int dynamicTarget, int minRange, int maxRange, int attempts,
      Consumer<OptionalInt> resultCallback) {
    if (fishingRootNode == null || fishingNumberInput == null || fishingConfirmButton == null
        || fishingPromptLabel == null) {
      System.err.println("Fishing panel UI not initialized. Cannot start game.");
      resultCallback.accept(OptionalInt.empty()); // Notify failure
      return;
    }

    hideAllBottomPanels(); // Hide other UI
    bottomPanel.setVisible(true);
    // Store game state
    this.targetNumber = dynamicTarget;
    this.maxAttempts = attempts;
    this.currentAttempts = 0;
    this.currentFishingCallback = resultCallback;
    this.fishingMinRange = minRange;
    this.fishingMaxRange = maxRange;

    // Setup TextFormatter with dynamic range
    UnaryOperator<TextFormatter.Change> filter = change -> {
      String newText = change.getControlNewText();
      // Regex to match numbers from minRange to maxRange
      // This regex is a bit simplistic for arbitrary ranges, especially with leading
      // zeros.
      // For a robust solution, parsing in the action handler is better.
      // For now, a simple digit check might be okay, and validate range on submit.
      if (newText.matches("\\d*")) { // Allows only digits
        // Further validation for actual range (1-100, 1-10 etc.) will be done on submit
        return change;
      }
      return null;
    };
    fishingNumberInput.setTextFormatter(new TextFormatter<>(filter));

    // Update prompt
    updateFishingPrompt(prompt + " (Attempts left: " + (maxAttempts - currentAttempts) + ")");
    fishingNumberInput.clear();

    // Configure the confirm button's action for this game session
    fishingConfirmButton.setOnAction(e -> handleFishingGuess());

    // Show the fishing panel
    fishingRootNode.setVisible(true);
    fishingRootNode.setManaged(true);
    fishingNumberInput.requestFocus();
  }

  private void handleFishingGuess() {
    String text = fishingNumberInput.getText();
    if (text.isEmpty()) {
      updateFishingPrompt("Please enter a number. Attempts left: " + (maxAttempts - currentAttempts));
      return;
    }

    int guessedValue;
    try {
      guessedValue = Integer.parseInt(text);
    } catch (NumberFormatException ex) {
      updateFishingPrompt("Invalid number. Try again. Attempts left: " + (maxAttempts - currentAttempts));
      fishingNumberInput.clear();
      return;
    }

    // Validate against dynamic range
    if (guessedValue < fishingMinRange || guessedValue > fishingMaxRange) {
      updateFishingPrompt("Number must be between " + fishingMinRange + " and " + fishingMaxRange + ". Attempts left: "
          + (maxAttempts - currentAttempts));
      fishingNumberInput.clear();
      return;
    }

    currentAttempts++;

    if (guessedValue == targetNumber) {
      updateFishingPrompt("Correct! You guessed " + targetNumber + ".");
      // Delay hiding and calling callback to show success message
      Timeline successTimeline = new Timeline(new KeyFrame(Duration.seconds(1.5), event -> {
        hideFishingPanel();
        if (currentFishingCallback != null) {
          currentFishingCallback.accept(OptionalInt.of(guessedValue));
        }
      }));
      successTimeline.play();
    } else {
      if (currentAttempts >= maxAttempts) {
        updateFishingPrompt("Wrong! No more attempts. The number was " + targetNumber + ".");
        // Delay hiding and calling callback
        Timeline failureTimeline = new Timeline(new KeyFrame(Duration.seconds(2), event -> {
          hideFishingPanel();
          if (currentFishingCallback != null) {
            currentFishingCallback.accept(OptionalInt.empty()); // No guess or failed
          }
        }));
        failureTimeline.play();
      } else {
        String hint = guessedValue < targetNumber ? "Too low." : "Too high.";
        updateFishingPrompt(hint + " Try again. Attempts left: " + (maxAttempts - currentAttempts));
        fishingNumberInput.clear();
        fishingNumberInput.requestFocus();
      }
    }
  }

  private void updateFishingPrompt(String message) {
    if (fishingPromptLabel != null) {
      fishingPromptLabel.setText(message);
    }
  }

  private void hideFishingPanel() {
    if (fishingRootNode != null) {
      fishingRootNode.setVisible(false);
      fishingRootNode.setManaged(false);
    }
    // Detach the specific game session's action handler
    if (fishingConfirmButton != null) {
      fishingConfirmButton.setOnAction(null);
    }
  }
}