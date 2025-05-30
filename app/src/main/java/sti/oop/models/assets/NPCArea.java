package sti.oop.models.assets;

import sti.oop.action.Action;
import sti.oop.models.Interactable;
import sti.oop.models.NPC.NPC;
import sti.oop.utils.Constants;

public class NPCArea extends Asset implements Interactable {
  NPC npc;
  String choosen_act = "";
  public NPCArea(NPC npc, int x, int y) {
    super(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, 3 * Constants.TILE_SIZE,3 * Constants.TILE_SIZE  , false);
    this.npc = npc;
  }

  @Override
  public void accept(Action action) {
    action.act(this);
  } 

  public NPC getNpc() {
    return npc;
  }

  public String getChoosen_act() {
    return choosen_act;
  }

  public void setChoosen_act(String choosen_act) {
    this.choosen_act = choosen_act;
  }

  @Override
  public boolean multipleInput() {
    return true;
  }
  
}
