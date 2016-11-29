import java.util.ArrayList;

public class Caretaker
{
    /**
     * In this implementation, there is only one checkpoint that can be loaded,
     * so it would save space and time if this was not an array list. However,
     * it's possible to go back and change it to allow checkpoint selection.
     */
    private ArrayList<Memento> savedStates = new ArrayList<Memento>();
    
    public void addMemento(Memento m)
    {
        savedStates.add(m);
    }
    
    public Memento getLastMemento()
    {
        return savedStates.get(savedStates.size() - 1);
    }
    
    /**
     * Just an extra option to make sure the player has actually saved a
     * checkpoint when trying to load it.
     */
    public boolean hasCheckpoint()
    {
        if (savedStates.isEmpty()) {
            return false;
        }
        return true;
    }
}
