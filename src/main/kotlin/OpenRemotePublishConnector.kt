import org.eclipse.paho.client.mqttv3.MqttClient

class OpenRemotePublishConnector<T>(
    val client : MqttClient,
    private val attributeName : String,
    private val assetId : String,
    private val clientId : String
) {


    fun publish(value : T)
    {

    }
}