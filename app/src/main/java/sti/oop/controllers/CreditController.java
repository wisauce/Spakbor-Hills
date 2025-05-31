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
 addCredit("Game Directors", List.of("Wisa Ahmaduta", "S. Ardy Bramantyo", "Fajrul 'Japrul'", "Rai Wiguna Dharma"));
    addSeparator();
    addCredit("Producers", List.of("Wisa Ahmaduta", "S. Ardy Bramantyo", "Fajrul 'Japrul'", "Rai Wiguna Dharma")); // Berdasarkan aktivitas merge dan integrasi fitur [cite: 9, 15, 21]
    addSeparator();
    addCredit("Lead Software Architect", List.of("Wisa Ahmaduta")); // Refactor modularisasi action logic dengan lambda, refactor controller logic agar sesuai MVC [cite: 8]
    addSeparator();
    addCredit("Engine & Core Systems Programmer", List.of("Wisa Ahmaduta")); // Menambahkan controller waktu[cite: 4], fitur auto fullscreen, dynamic canvas, kamera, collision detection, rendering map, map switching logic [cite: 5]
    addSeparator();
    addCredit("Lead Gameplay Designers", List.of(
        "Wisa Ahmaduta", // Konsep mekanik inti: fishing[cite: 1, 2], tilling, planting, watering, harvesting, energi [cite: 3]
        "S. Ardy Bramantyo", // Konsep sistem inventory, cooking, farming recipe, NPC [cite: 11]
        "Fajrul 'Japrul'" // Konsep interaksi NPC mendalam (chatting, gifting, proposing, marrying)[cite: 22], fitur eating [cite: 22]
    ));
    addSeparator();
    addCredit("Core Gameplay Mechanics Programmer", List.of("Wisa Ahmaduta")); // Logic untuk action seperti tilling, planting, watering, harvesting[cite: 3], logika untuk energi[cite: 3], GUI & interface fishing [cite: 1, 2]
    addSeparator();
    addCredit("Advanced Gameplay Systems Programmer", List.of("S. Ardy Bramantyo")); // Logika bin, cooking, inventory, farming recipe, NPC, recipe barrier[cite: 11], logika penyimpanan item, starter item, cooking logics, deselect item [cite: 13]
    addSeparator();
    addCredit("NPC Interaction Designer & Programmer", List.of("Fajrul 'Japrul'")); // Membuat seluruh NPC Interaction (chatting, gifting, proposing, marrying) [cite: 22]
    addSeparator();
    addCredit("World Systems Programmer", List.of("S. Ardy Bramantyo")); // Menambahkan fitur cuaca, waktu, musim, dan transisi warna hari [cite: 13]
    addSeparator();
    addCredit("Lead UI/UX Designers", List.of(
        "Wisa Ahmaduta", // Desain awal main menu, fishing GUI, panel notifikasi [cite: 1, 2]
        "S. Ardy Bramantyo" // Desain Inventory GUI, Hotbar, struktur GUI Inventory [cite: 12, 14]
    ));
    addSeparator();
    addCredit("UI Programmers & Implementers", List.of(
        "Wisa Ahmaduta",    // Menambahkan main menu button, credit, GUI untuk fishing, panelController, notification panel, modal [cite: 1, 2]
        "S. Ardy Bramantyo", // Menambahkan GUI Inventory, Item Sprites, Equipment, Hotbar, Inventory Controller [cite: 12, 14]
        "Fajrul 'Japrul'"   // Menyesuaikan Ul dan integrasi GUI [cite: 23]
    ));
    addSeparator();
    addCredit("UI Artist", List.of("S. Ardy Bramantyo")); // Item Sprites, Equipment (visual elemen UI) [cite: 12]
    addSeparator();
    addCredit("World Designer & Cartographer", List.of("Rai Wiguna Dharma")); // Menambahkan Fishing Area, NPC Houses, koneksi world map ke farm[cite: 18], Mengubah house map [cite: 19]
    addSeparator();
    addCredit("Level Designer & Environmental Scripter", List.of("Rai Wiguna Dharma")); // Integrasi mapping randomize[cite: 18], logic NPC ke map, posisi rumah[cite: 18], logika interaksi dengan NPC dan lokasi[cite: 19], Menghubungkan map secara dinamis [cite: 20]
    addSeparator();
    addCredit("Character Animator", List.of("Wisa Ahmaduta")); // Menambahkan spritesheet animation untuk player [cite: 5]
    addSeparator();
    addCredit("Build & Tools Engineer", List.of("Wisa Ahmaduta")); // Mengganti sistem build dari Maven ke Gradle[cite: 10], Menyesuaikan versi JavaFX [cite: 10]
    addSeparator();
    addCredit("Lead QA Tester", List.of("Rai Wiguna Dharma")); // Testing, updating, merging mapping [cite: 20]
    addSeparator();
    addCredit("QA & Bug Squashing Team", List.of(
        "Wisa Ahmaduta", // Memperbaiki file FXML, teleportation logic, bug hujan & interaksi, double watering/energy cost, asset collision, rendering, interaksi [cite: 6, 7]
        "S. Ardy Bramantyo", // Memperbaiki bug package dan item saat harvest, logic harvest dan error package bin [cite: 16]
        "Rai Wiguna Dharma", // Testing, updating, revert perubahan mapping (implikasi penanganan bug) [cite: 20]
        "Fajrul 'Japrul'" // Menyesuaikan Ul dan integrasi GUI (implikasi perbaikan isu integrasi)[cite: 23], merge branches [cite: 21]
    ));
    addSeparator();
    addCredit("Sound & Music", List.of("Wisa Ahmaduta", "S. Ardy Bramantyo", "Fajrul 'Japrul'", "Rai Wiguna Dharma")); // Mengikuti pola contoh untuk kontribusi yang tidak tercatat spesifik di log
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
    KeyFrame kf = new KeyFrame(Duration.seconds(300), kv); // scroll dalam 20 detik
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
