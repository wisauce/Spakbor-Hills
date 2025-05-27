package sti.oop.models;

import sti.oop.action.Marry;
import sti.oop.action.Proposing;
import sti.oop.controllers.FarmController;

public class Action implements Actor {
  FarmController farmController;

  public Action(FarmController farmController) {
    this.farmController = farmController;
  }

  public void act(Teleporter visited) {
    farmController.changeMap(visited.getDestination());
  }

  @Override
  public void act(NPCArea acted) {
    if (acted.getChoosen_act() == null) return;
    if (acted.getChoosen_act().equals("Marry")) {
      Marry marry = new Marry();
      marry.doMarry(farmController.getPlayerController().getPlayer(), acted.getNpc());
    }
    if (acted.getChoosen_act().equals("Propose")) {
      Proposing proposing = new Proposing();
      proposing.doProposing(farmController.getPlayerController().getPlayer(), acted.getNpc());
    }
  }
}