package sti.oop.controllers;

import java.util.Arrays;
import java.util.List;

import sti.oop.models.NPC.Abigail;
import sti.oop.models.NPC.Caroline;
import sti.oop.models.NPC.Dasco;
import sti.oop.models.NPC.Emily;
import sti.oop.models.NPC.MayorTadi;
import sti.oop.models.NPC.NPC;
import sti.oop.models.NPC.Perry;

public class NPCController {
  List<NPC> listOfNPCs;

  public NPCController() {
    listOfNPCs = Arrays.asList(
      new MayorTadi(),
      new Emily(),
      new Abigail(),
      new Caroline(),
      new Dasco(),
      new Perry()
    );
  }
    
}