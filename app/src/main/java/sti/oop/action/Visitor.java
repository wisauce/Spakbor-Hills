package sti.oop.action;
import sti.oop.models.NPC.NPC;

public interface Visitor {
    public void visit(NPC npc);
    
}
