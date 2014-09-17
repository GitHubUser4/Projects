import javax.security.auth.Subject;

/**
 * Created by Nehrist on 15.09.2014.
 */
public class CurrentConditionsDisplay implements IObserverable, IDisplayElementable {
    private float temperature;
    private float humidity;
    private ISubjectable weatherData;

    public CurrentConditionsDisplay(ISubjectable weatherData) {
        this.weatherData = weatherData;
        weatherData.registerObserver(this);
    }

    public void update(float temperature, float humidity, float pressure) {
        this.temperature = temperature;
        this.humidity = humidity;
        display();

    }

    public void display() {
        System.out.println("Current conditions: " + temperature + "F degrees and " + humidity + "% humidity");
    }
}
