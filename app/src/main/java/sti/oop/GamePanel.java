package sti.oop;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable {
  final int originalTileSize = 16;
  final int scale = 3;
  final int tileSize = originalTileSize * scale;
  final int maxScreenCol = 32;
  final int maxScreenRow = 32;
  final int screenWidth = tileSize * maxScreenCol;
  final int screenHeight = tileSize * maxScreenRow;

  Thread gameThread;


  public GamePanel() {
    this.setPreferredSize(new Dimension(screenWidth,screenHeight));
    this.setBackground(Color.black);
    this.setDoubleBuffered(true);
  }

  public void startGameThread() {
    gameThread = new Thread(this);
    gameThread.start();
  }


  @Override
  public void run() {
    while(gameThread != null) {
      
      update();
      repaint();
    }
  }

  public void update() {

  }

  public void paintComponent(Graphics g) {
    super.paintComponent(g);
    Graphics2D g2 = (Graphics2D) g;
    g2.setColor(Color.white);
    g2.fillRect(100,100,tileSize,tileSize);
    g2.dispose();
  }
}
