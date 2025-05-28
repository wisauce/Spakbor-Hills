package sti.oop.interfaces;

import sti.oop.models.Land;
import sti.oop.models.NPCArea;
import sti.oop.models.Teleporter;

public interface Actor {
  public void act(Teleporter acted);
  public void act(NPCArea acted);
  public void act(Land acted);
}
