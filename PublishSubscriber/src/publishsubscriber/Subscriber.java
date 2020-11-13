package publishsubscriber;

import org.eclipse.paho.client.mqttv3.*;

public class Subscriber {
    public static final String BROKER = "tcp://localhost:1883";
    public static final String CLIENTID = "JavaSample";
    public static final String TOPIC = "sensor/temperature/";

    public static void main(String []args){
        try{
            MqttClient client = new MqttClient(BROKER, MqttAsyncClient.generateClientId());
            MqttConnectOptions connectOptions = new MqttConnectOptions();
            connectOptions.setCleanSession(false);
            connectOptions.setAutomaticReconnect(true);
            client.setCallback(new MqttCallback() {
                @Override
                public void connectionLost(Throwable throwable) {
                    System.out.println("Connection lost.");
                    System.out.println("Cause: "  + throwable.getCause());
                }

                @Override
                public void messageArrived(String s, MqttMessage mqttMessage) throws Exception {
                    System.out.println("Received new temperature. " +
                            "\nTemperature: " + new String(mqttMessage.getPayload()));
                }

                @Override
                public void deliveryComplete(IMqttDeliveryToken iMqttDeliveryToken) {

                }
            });
            client.connect(connectOptions);
            client.subscribe(TOPIC, 2);

        }catch (MqttException mqttException){
            System.out.println("Reason: " + mqttException.getReasonCode());
            System.out.println("Message: " + mqttException.getMessage());
            System.out.println("Localization: " + mqttException.getLocalizedMessage());
            mqttException.printStackTrace();
        }
    }
}
