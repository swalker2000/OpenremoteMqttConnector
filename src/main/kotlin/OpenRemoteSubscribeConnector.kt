import com.openremote.telebot.OpenRemoteConnectorRunnable
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.slf4j.LoggerFactory

abstract class OpenRemoteSubscribeConnector<T> {

    protected abstract val client : MqttClient

    protected abstract val attributeName : String

    protected abstract val assetId : String

    protected abstract val clientId : String

    private val logger = LoggerFactory.getLogger("${OpenRemoteSubscribeConnector::class.java}_${attributeName}.${assetId}")

    /**
     * Список подписчиков topic.
     * <topic, .....>
     */
    private val subscribers = mutableListOf<OpenRemoteConnectorRunnable<T>>()

    private inner class Callback(private val subscribeTopic : String) : MqttCallback
    {
        override fun connectionLost(cause: Throwable?) {
            //ни чего не делаем, если что - то написать здесь, вылезет множество сообщений в случае потери соединения
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            if(topic == subscribeTopic)
            {
                logger.info("RD:${message!!.payload.toString(Charsets.UTF_8)}")

            }

        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            //ни чего не делаем, если что - то написать здесь, вылезет множество сообщений в случае потери соединения
        }

    }

    fun start() {
        val subscribeTopic = "master/$clientId/attribute/$attributeName/$assetId"
        client.setCallback(Callback(subscribeTopic))
        client.subscribe(subscribeTopic, 0);
    }

    fun subscribe(action : OpenRemoteConnectorRunnable<T>)
    {
        subscribers.add(action)
    }
}