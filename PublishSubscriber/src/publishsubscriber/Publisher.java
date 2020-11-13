package publishsubscriber;

import org.eclipse.paho.client.mqttv3.MqttClient;
import org.eclipse.paho.client.mqttv3.MqttConnectOptions;
import org.eclipse.paho.client.mqttv3.MqttException;
import org.eclipse.paho.client.mqttv3.MqttMessage;
import org.eclipse.paho.client.mqttv3.persist.MemoryPersistence;

import java.util.concurrent.TimeUnit;

public class Publisher {
    public static final String TOPIC = "sensor/temperature/";
    public static final int QualityOfService = 2;
    public static final String BROKER = "tcp://localhost:1883";
    public static final String CLIENTID = "JavaSample";

    public static void main(String []args){
        MemoryPersistence persistence = new MemoryPersistence();
        String content;
        try{
            MqttClient sampleClient = new MqttClient(BROKER, CLIENTID, persistence);
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(true);
            System.out.println("Connecting to the broker: " + BROKER);

            sampleClient.connect(connectOptions);
            System.out.println("Connected");
            while(true){
                try{
                    TimeUnit.SECONDS.sleep(1);
                    content = String.valueOf(generateRandomNumber(15, 45));
                    System.out.println("Content: " + content);

                    MqttMessage message = new MqttMessage(content.getBytes());
                    message.setQos(QualityOfService);

                    sampleClient.publish(TOPIC, message);
                    System.out.println("Message Published");
                }catch(InterruptedException interruptedException){
                    System.out.println("Error: " + interruptedException.getMessage());
                }
            }
        }catch (MqttException mqttException){
            System.out.println("Reason: " + mqttException.getReasonCode());
            System.out.println("Message: " + mqttException.getMessage());
            System.out.println("Localization: " + mqttException.getLocalizedMessage());
        }
    }

    public static int generateRandomNumber(int min, int max){
        return (int) ((Math.random() * (max - min)) + min);
    }
}
