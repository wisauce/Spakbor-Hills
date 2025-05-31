package sti.oop.models.assets;

import java.util.List;
import java.util.stream.Collectors;

import sti.oop.models.ItemRegistry;
import sti.oop.models.Item.Fish;
import sti.oop.models.deploys.Pond;
import sti.oop.utils.Constants;

public class OceanArea extends FishingArea {
  public OceanArea() {
    super(
    5 * Constants.TILE_SIZE, 9 * Constants.TILE_SIZE ,
    4 * Constants.TILE_SIZE, 4 *Constants.TILE_SIZE,
    List.of("Flounder", "Halibut", "Octopus", "Pufferfish","Sardine","SuperCucumber","Crimsonfish").stream()
      .map(ItemRegistry::createItem)
      .filter(item -> item instanceof Fish)
      .map(item -> (Fish) item)
      .collect(Collectors.toList())
    );
  }  
}
