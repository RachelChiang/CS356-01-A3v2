import java.util.ArrayList;

public class Caretaker
{
    private ArrayList<Memento> savedStates = new ArrayList<Memento>();
    
    public void addMemento(Memento m)
    {
        savedStates.add(m);
    }
    
    public Memento getLastMemento()
    {
        return savedStates.get(savedStates.size() - 1);
    }
    
    public boolean hasCheckpoint()
    {
        if (savedStates.isEmpty()) {
            return false;
        }
        return true;
    }
}
