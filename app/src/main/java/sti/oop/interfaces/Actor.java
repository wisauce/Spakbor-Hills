package sti.oop.interfaces;

import sti.oop.models.assets.Land;
import sti.oop.models.assets.NPCArea;
import sti.oop.models.assets.CookingArea;
import sti.oop.models.assets.SleepingArea;
import sti.oop.models.assets.Teleporter;
import sti.oop.models.assets.BinArea;

public interface Actor {
  public void act(Teleporter acted);
  public void act(NPCArea acted);
  public void act(Land acted);
  public void act(SleepingArea acted);
  public void act(CookingArea acted);
  public void act(BinArea acted);
}
