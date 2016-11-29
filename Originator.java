public interface Originator
{
    public Memento saveToMemento();
    
    public void restoreFromMemento(Memento m);
}
