/**
 * Another simple interface that marks the internal states of the Originator
 * that need to be saved and restored.
 */
public interface GameObject
{
    public GameObject makeCopy();
    public void reinstate(GameObject entity);
}
