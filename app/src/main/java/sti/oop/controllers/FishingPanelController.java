package sti.oop.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;

public class FishingPanelController {
  @FXML
  private TextField numberInput;

  @FXML
  private Button confirmButton;

  @FXML
  private VBox rootFishing;

  @FXML
  private void initialize() {
    confirmButton.setOnAction(event -> handleConfirm());
  }

  private void handleConfirm() {
    String text = numberInput.getText();
    if (text == null || text.isBlank()) {
      showError("Input tidak boleh kosong.");
      return;
    }

    try {
      int value = Integer.parseInt(text);
      if (value < 1 || value > 100) {
        showError("Masukkan angka antara 1 sampai 100.");
        return;
      }

      // Kalau valid, lakukan sesuatu (contoh: print ke console)
      System.out.println("Input valid: " + value);
      // Kamu bisa tambahkan logika lain di sini

    } catch (NumberFormatException e) {
      showError("Input harus berupa angka.");
    }
  }

  private void showError(String message) {
    Alert alert = new Alert(Alert.AlertType.ERROR);
    alert.setTitle("Invalid Input");
    alert.setHeaderText(null);
    alert.setContentText(message);
    alert.showAndWait();
  }

  public VBox getRootFishing() {
    return rootFishing;
  }

  
}
