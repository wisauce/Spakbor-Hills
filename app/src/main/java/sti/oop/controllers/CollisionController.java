package sti.oop.controllers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import sti.oop.utils.Constants;

// import sti.oop.models.Constants;

public class CollisionController {
  private List<List<Boolean>> collisionMatrix;

  public CollisionController(String colissionMapsrc) {
    collisionMatrix = new ArrayList<>();
    BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(colissionMapsrc)));
    String line;
    try {
      line = br.readLine();
      while (line != null) {
          collisionMatrix.add(Arrays.stream(line.split(" ")).map(s -> s.equals("1")).collect(Collectors.toList()));
          line = br.readLine();
        }
    } catch(IOException e) {
      e.printStackTrace();
    }
  }

  public boolean isCollision(int x, int y) {
    int tileX = x / Constants.TILE_SIZE;
    int tileY = y / Constants.TILE_SIZE;
    return collisionMatrix.get(tileY).get(tileX);
  }
}