package sti.oop.models;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class CollisionMap {
  private List<List<Boolean>> collisionMatrix;

  public CollisionMap(String collisionMapSrc) {
    collisionMatrix = new ArrayList<>();
    BufferedReader br = new BufferedReader(new InputStreamReader(getClass().getResourceAsStream(collisionMapSrc)));
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

  public List<List<Boolean>> getCollisionMatrix() {
    return collisionMatrix;
  }
}
