package sti.oop.interfaces;

import sti.oop.models.assets.Land;
import sti.oop.models.assets.NPCArea;
import sti.oop.models.assets.SleepingArea;
import sti.oop.models.assets.Teleporter;

public interface Actor {
  public void act(Teleporter acted);
  public void act(NPCArea acted);
  public void act(Land acted);
  public void act(SleepingArea acted);
}
