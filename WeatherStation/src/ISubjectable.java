
/**
 * Created by Nehrist on 14.09.2014.
 */
public interface ISubjectable {
    public void registerObserver(IObserverable o);
    public void removeObserver(IObserverable o);
    public void notifyObservers();

}
