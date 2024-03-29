package connector

import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.slf4j.LoggerFactory

/**
 * Allows you to publish data to the openremote server.
 *  - instructions https://github.com/openremote/openremote/wiki/Tutorial%3A-Connect-your-MQTT-Client
 */
class OpenRemotePublishConnector<T>(
    val client : MqttClient,
    attributeName : String,
    assetId : String,
    clientId : String,
    realm : String
) {

    private val topic = "$realm/$clientId/writeattributevalue/$attributeName/$assetId"
    private val qos = 2
    private val logger = LoggerFactory.getLogger("${OpenRemotePublishConnector::class.java}[${attributeName}.${assetId}]")


    fun publish(value : T)
    {
        val message = MqttMessage(value.toString().toByteArray())
        message.qos = qos
        logger.info("TD:${value.toString()}")
        client.publish(topic, message)
    }
}