package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.controllers.PanelController;
import sti.oop.models.Player;
import sti.oop.models.assets.NPCArea;

public class NPCInteractionHandler {
  public void handleInteraction(NPCArea npcArea, Player player, PanelController panelController, FarmController farmController) {
    panelController.multipleOptionPanel(npcArea.getOptions(), (selectedOption) -> {
      if ("Chat".equals(selectedOption)) {
        panelController.showDialog(new Chatting().doChatting(player, npcArea.getNpc(), farmController.getFarm()));
      } else if ("Gift".equals(selectedOption)) {
        panelController.showDialog(new Gifting().doGifting(player, npcArea.getNpc(), farmController.getFarm()));
      } else if ("Propose".equals(selectedOption)) {
        panelController.showDialog(new Proposing().doProposing(player, npcArea.getNpc(), farmController.getFarm()));
      } else if ("Marry".equals(selectedOption)) {
        panelController.showDialog(new Marry().doMarry(player, npcArea.getNpc(), farmController));
      }
    });
  }

}