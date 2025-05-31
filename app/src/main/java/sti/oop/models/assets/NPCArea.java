package sti.oop.models.assets;

import java.util.List;

import sti.oop.action.Action;
import sti.oop.interfaces.MultipleOptions;
import sti.oop.models.Interactable;
import sti.oop.models.NPC.NPC;
import sti.oop.utils.Constants;

public class NPCArea extends Asset implements Interactable, MultipleOptions {

  NPC npc;


  public NPCArea(NPC npc, int x, int y, int width, int height) {
    super(x * Constants.TILE_SIZE, y * Constants.TILE_SIZE, width * Constants.TILE_SIZE,height * Constants.TILE_SIZE  , false);
    this.npc = npc;

  }

  @Override
  public void accept(Action action) {
    action.act(this);
  } 

  public NPC getNpc() {
    return npc;
  }


  @Override
  public boolean multipleInput() {
    return false;
  }

  @Override
  public List<String> getOptions() {
    return List.of("Chat", "Gift", "Propose","Marry");
  }
  
}
