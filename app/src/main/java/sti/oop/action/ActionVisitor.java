package sti.oop.action;
import sti.oop.models.NPC.NPC;
public class ActionVisitor implements Visitor{
    public void visit(NPC npc){
        System.out.println("What would you do to "+ npc.getName() );
        
    }
    

}