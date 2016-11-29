import java.util.ArrayList;

public class Memento
{
    /**
     * The Memento stores the internal state of the Originator. In this case,
     * it's simply the GameObjects and also the turnNumber.
     */
    private ArrayList<GameObject> entities = new ArrayList<GameObject>();
    private int turnNumber;
    
    public Memento(Originator game)
    {
        for (int i = 0; i < ((GameEngine) game).gatherObjects().size(); ++i)
        {
            entities.add(((GameEngine) game).gatherObjects().get(i));
        }
        turnNumber = ((GameEngine) game).getTurnNumber();
    }
    
    public ArrayList<GameObject> getSavedEntities()
    {
        return entities;
    }
    public int getTurnNumber()
    {
        return turnNumber;
    }
}
