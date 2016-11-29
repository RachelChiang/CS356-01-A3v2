import java.util.ArrayList;

public class Memento
{
    private ArrayList<GameObject> entities = new ArrayList<GameObject>();
    private int turnNumber;
    
    public Memento(GameEngine game)
    {
        for (int i = 0; i < game.gatherObjects().size(); ++i)
        {
            entities.add(game.gatherObjects().get(i));
        }
        turnNumber = game.getTurnNumber();
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
