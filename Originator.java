/**
 * Simple interface marking the GameEngine as an Originator.
 */
public interface Originator
{
    public Memento saveToMemento();
    
    public void restoreFromMemento(Memento m);
}
