package sti.oop.action;

import sti.oop.controllers.PlayerController;
import sti.oop.models.assets.NPCArea;

public class NPCInteractionHandler {
  private PlayerController playerController;
  private boolean hasInstructionShowed = false;

  public NPCInteractionHandler(PlayerController playerController) {
    this.playerController = playerController;
  }

  public String handleInteraction(NPCArea npcArea) {
    if (playerController.isToggled()) {
      String result = null;

      if (playerController.isKey1Pressed()) {
        result = new Chatting().doChatting(playerController.getPlayer(), npcArea.getNpc());
      } else if (playerController.isKey2Pressed()) {
        result = new Gifting().doGifting(playerController.getPlayer(), npcArea.getNpc());
      } else if (playerController.isKey3Pressed()) {
        result = new Proposing().doProposing(playerController.getPlayer(), npcArea.getNpc());
      } else if (playerController.isKey4Pressed()) {
        result = new Marry().doMarry(playerController.getPlayer(), npcArea.getNpc());
      }

      if (result != null) {
        playerController.toggle(); // keluar dari interaction mode
        hasInstructionShowed = false;
        return result;
      }

      if (!hasInstructionShowed) {
        hasInstructionShowed = true;
        return "1: Chat    2: Gift    3: Propose    4: Marry";
      }
    }
    return null;
  }

}