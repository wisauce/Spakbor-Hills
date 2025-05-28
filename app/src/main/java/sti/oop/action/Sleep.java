package sti.oop.action;

import sti.oop.controllers.FarmController;
import sti.oop.controllers.GameMapController.MapName;
import sti.oop.models.Player;

public class Sleep {

  public void sleep(FarmController farmController, int x, int y) {
    farmController.changeMap(MapName.HOUSE);
    farmController.getPlayerController().getPlayer().setX(x);
    farmController.getPlayerController().getPlayer().setY(y);
    farmController.getTimeController().getFarm().nextDay();
    farmController.getTimeController().getFarm().setTime(6, 0);
    reenergizePlayer(farmController.getPlayerController().getPlayer());
  }

  public void reenergizePlayer(Player player) {
    System.out.println(player.getEnergy());
    if (player.getEnergy() <= 0) {
      player.setEnergy(player.getEnergy() + 10);
    } else if (player.getEnergy() < 0.1 * player.getMAX_ENERGY()) {
      player.setEnergy(player.getEnergy() + (int) (player.getMAX_ENERGY() * 0.5));
    } else {
      player.setEnergy(player.getMAX_ENERGY());
    }
  }
}
