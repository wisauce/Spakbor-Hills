package sti.oop.models;

public class Teleport extends InteractableAssset {
  public Teleport(int x, int y, String imageSrc) {
    super(x, y, imageSrc);
    //TODO Auto-generated constructor stub
  }

  public void accept(Action action) {
    System.out.println("Hello");
  }
}
