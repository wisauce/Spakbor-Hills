package sti.oop.models;

public interface Actor {
  public void act(Teleporter acted);
  public void act(NPCArea acted);
  public void act(Land acted);
}
