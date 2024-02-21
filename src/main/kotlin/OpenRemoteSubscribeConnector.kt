import com.openremote.telebot.OpenRemoteConnectorRunnable
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.slf4j.LoggerFactory
import com.google.gson.Gson
import inmessagerepository.InMessageRepository
import messge.OpenRemoteMqttMessage


class OpenRemoteSubscribeConnector<T>(
    val client : MqttClient,
    private val attributeName : String,
    private val assetId : String,
    private val clientId : String
    ) {


    private val logger = LoggerFactory.getLogger("${OpenRemoteSubscribeConnector::class.java}[${attributeName}.${assetId}]")

    private val gson:Gson  = Gson()

    /**
     * Список подписчиков topic.
     * <topic, .....>
     */
    private val subscribers = mutableListOf<OpenRemoteConnectorRunnable<T>>()

    private val subscribeTopic = "master/$clientId/attribute/$attributeName/$assetId"

    private inner class Callback() : MqttCallback
    {
        override fun connectionLost(cause: Throwable?) {
            //ни чего не делаем, если что - то написать здесь, вылезет множество сообщений в случае потери соединения
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {
            if(topic == subscribeTopic)
            {
                val rd = message!!.payload.toString(Charsets.UTF_8)
                logger.info("RD:${rd}")
                val inMessage = gson.fromJson(rd, OpenRemoteMqttMessage<T>().javaClass)
                subscribers.forEach { it.run(inMessage) }
                /*
                if(!InMessageRepository.whetherTheMessageHasAlreadyBeenChecked(inMessage.ref.id))
                {
                    logger.info("Unique message")
                    subscribers.forEach { it.run(inMessage) }
                }
                else
                {
                    logger.warn("Not unique message")
                }
                */

            }

        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            //ни чего не делаем, если что - то написать здесь, вылезет множество сообщений в случае потери соединения
        }

    }

   init {
        client.setCallback(Callback())
        client.subscribe(subscribeTopic, 0);
    }

    fun subscribe(action : OpenRemoteConnectorRunnable<T>)
    {
        subscribers.add(action)
    }
}