package sti.oop.interfaces;

import sti.oop.models.assets.Land;
import sti.oop.models.assets.NPCArea;
import sti.oop.models.assets.SleepingArea;
import sti.oop.models.assets.Teleporter;

public interface Actor {
  public String act(Teleporter acted);
  public String act(NPCArea acted);
  public String act(Land acted);
  public String act(SleepingArea acted);
}
