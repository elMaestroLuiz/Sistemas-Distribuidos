package publishsubscriber;

public interface ConnectionProps {
    String TOPIC = "sensor/temperature/";
    int QualityOfService = 2;
    String BROKER = "tcp://localhost:1883";
}
