package sti.oop.models.assets;

import java.util.List;
import java.util.stream.Collectors;

import sti.oop.models.ItemRegistry;
import sti.oop.models.Item.Fish;
import sti.oop.utils.Constants;

public class LakeArea extends FishingArea {
  public LakeArea() {
    super(
    37 * Constants.TILE_SIZE, 46 * Constants.TILE_SIZE ,
    3 * Constants.TILE_SIZE, 4 *Constants.TILE_SIZE,
    List.of("LargemouthBass", "RainbowTrout", "Sturgeon", "MidnightCarp","Legend").stream()
      .map(ItemRegistry::createItem)
      .filter(item -> item instanceof Fish)
      .map(item -> (Fish) item)
      .collect(Collectors.toList())
    );
  }  
}