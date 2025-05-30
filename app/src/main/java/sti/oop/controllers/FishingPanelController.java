package sti.oop.controllers;

import java.util.function.UnaryOperator;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.control.TextFormatter;
import javafx.scene.layout.VBox;
import sti.oop.interfaces.UserInputListener;

public class FishingPanelController {

  @FXML
  private TextField numberInput;

  @FXML
  private Button confirmButton;

  @FXML
  private VBox rootFishing;

  private UserInputListener<Integer> listener; // <- simpan listener

  @FXML
  public void initialize() {
    UnaryOperator<TextFormatter.Change> filter = change -> {
      String newText = change.getControlNewText();
      if (newText.matches("([1-9][0-9]{0,1}|100)?")) {
        return change;
      } else {
        return null;
      }
    };

    numberInput.setTextFormatter(new TextFormatter<>(filter));

    confirmButton.setOnAction(e -> {
      String text = numberInput.getText();
      if (!text.isEmpty()) {
        int value = Integer.parseInt(text);
        if (listener != null) {
          listener.onUserInput(value); // PANGGIL LISTENER!
        }
        hide(); // Optional: sembunyiin panel setelah input
      }
    });
  }

  public void setUserInputListener(UserInputListener<Integer> listener) {
    this.listener = listener;
  }

  public void show() {
    rootFishing.setVisible(true);
    rootFishing.setManaged(true);
    numberInput.clear();
  }

  public void hide() {
    rootFishing.setVisible(false);
    rootFishing.setManaged(false);
  }

  public VBox getRootFishing() {
    return rootFishing;
  }
}
