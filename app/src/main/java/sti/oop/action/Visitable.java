package sti.oop.action;

public interface Visitable {
    public void accept(ActionVisitor actionVisitor);
    
} 
