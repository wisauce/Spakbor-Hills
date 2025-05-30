package sti.oop.models.assets;

import java.util.List;
import java.util.stream.Collectors;

import sti.oop.models.ItemRegistry;
import sti.oop.models.Item.Fish;
import sti.oop.models.deploys.Pond;
import sti.oop.utils.Constants;

public class PondArea extends FishingArea {
  public PondArea(Pond pond) {
    super(
    pond.getX() - Constants.TILE_SIZE, 
    pond.getY() - Constants.TILE_SIZE, 
    (int) pond.getSolidArea().getWidth() + 2*Constants.TILE_SIZE, 
    (int) pond.getSolidArea().getWidth() + 2*Constants.TILE_SIZE,
    List.of("Carp", "MidnightCarp", "Catfish", "Angler").stream()
      .map(ItemRegistry::createItem)
      .filter(item -> item instanceof Fish)
      .map(item -> (Fish) item)
      .collect(Collectors.toList())
    );
  }  
}