package sti.oop.controllers;

import java.util.List;

import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Separator;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;

public class CreditController {

  private MediaPlayer backgroundMusicPlayer;

  @FXML
  private VBox creditBox;

  @FXML
  private ScrollPane scrollPane;

  @FXML
  public void initialize() {
    playBackgroundMusic();
    addCredit("Director", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("Programmer", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("UI Designer", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("Sound & Music", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("Special Thanks", List.of("Everyone who supported us!","Dhaffin yang ngilang buat kami semangat!"));
    addCredit("Director", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("Programmer", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("UI Designer", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("Sound & Music", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("Special Thanks", List.of("Everyone who supported us!","Dhaffin yang ngilang buat kami semangat!"));
    addCredit("Director", List.of("John Doe"));
    addSeparator();
    addCredit("Director", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("Programmer", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("UI Designer", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("Sound & Music", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("Special Thanks", List.of("Everyone who supported us!","Dhaffin yang ngilang buat kami semangat!"));
    addCredit("Director", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("Programmer", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("UI Designer", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("Sound & Music", List.of("Wisa", "Ardy", "Fajrul(Japrul)","RaiDharma"));
    addSeparator();
    addCredit("Special Thanks", List.of("Everyone who supported us!","Dhaffin yang ngilang buat kami semangat!"));
    addImage("/images/gacor.jpg");
    scrollPane.setPannable(false); // tidak bisa digeser manual
    scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER); // sembunyikan scrollbar vertikal

    // Disable scroll via mouse/touchpad/keyboard
    scrollPane.addEventFilter(javafx.scene.input.ScrollEvent.ANY, event -> event.consume());
    scrollPane.addEventFilter(javafx.scene.input.KeyEvent.ANY, event -> event.consume());
    Timeline timeline = new Timeline();
    KeyValue kv = new KeyValue(scrollPane.vvalueProperty(), 1);
    KeyFrame kf = new KeyFrame(Duration.seconds(20), kv); // scroll dalam 20 detik
    timeline.getKeyFrames().add(kf);
    timeline.play();

  }

private void addCredit(String role, List<String> names) {
  // Role title (bold + kuning)
  Text roleText = new Text(role);
  roleText.setFont(Font.font("System", FontWeight.BOLD, 18));
  roleText.setStyle("-fx-fill: #ffd700;"); // warna emas
  TextFlow roleFlow = new TextFlow(roleText);
  roleFlow.setTextAlignment(TextAlignment.CENTER);

  VBox roleSection = new VBox();
  roleSection.setSpacing(5);
  roleSection.setAlignment(javafx.geometry.Pos.CENTER); // pastikan kontennya rata tengah
  roleSection.getChildren().add(roleFlow);

  // Names (normal + putih)
  for (String name : names) {
    Text nameText = new Text(name);
    nameText.setFont(Font.font("System", FontWeight.NORMAL, 16));
    nameText.setStyle("-fx-fill: white;"); // teks putih
    TextFlow nameFlow = new TextFlow(nameText);
    nameFlow.setTextAlignment(TextAlignment.CENTER);
    roleSection.getChildren().add(nameFlow);
  }

  creditBox.getChildren().add(roleSection);
}


  private void addSeparator() {
    VBox spacer = new VBox();
    spacer.setMinHeight(20); // bisa disesuaikan tinggi spasinya
    creditBox.getChildren().add(spacer);
  }

  private void addImage(String imagePath) {
    Image image = new Image(getClass().getResourceAsStream(imagePath));
    ImageView imageView = new ImageView(image);
    imageView.setPreserveRatio(true);
    creditBox.getChildren().add(imageView);

  }
  
  private void playBackgroundMusic() {
    try {
      Media media = new Media(getClass().getResource("/audio/Timur.mp3").toExternalForm());
      backgroundMusicPlayer = new MediaPlayer(media);
      backgroundMusicPlayer.setCycleCount(MediaPlayer.INDEFINITE); // Loop musiknya
      backgroundMusicPlayer.play();
    } catch (Exception e) {
      System.err.println("Gagal memutar musik: " + e.getMessage());
    }
  }

}
