package connector

import connector.ssltools.IgnoreCertificates
import org.eclipse.paho.client.mqttv3.*
import org.slf4j.LoggerFactory


class OpenRemoteConnectorFactory(
    private val host : String,
    private val port : Int,
    private val clientId : String,
    private val username : String,
    private val password : String) {

    private val logger = LoggerFactory.getLogger("${OpenRemoteConnectorFactory::class.java}_${clientId}")
    private inner class Callback() : MqttCallback
    {
        override fun connectionLost(cause: Throwable?) {
            //ни чего не делаем, если что - то написать здесь, вылезет множество сообщений в случае потери соединения
            logger.error(cause!!.stackTraceToString())
        }

        override fun messageArrived(topic: String?, message: MqttMessage?) {

            println("New message.")
            println(" - topic : $topic")
            println(" - message : ${message!!.payload}")
        }

        override fun deliveryComplete(token: IMqttDeliveryToken?) {
            //ни чего не делаем, если что - то написать здесь, вылезет множество сообщений в случае потери соединения
        }

    }

    /*******CONNECTION*******/

    private val broker = "ssl://$host:$port"

    private val client : MqttClient = MqttClient(broker, clientId)

    private val ignoreCertificates = IgnoreCertificates()

    init {
        val connOpts = MqttConnectOptions()
        connOpts.socketFactory=ignoreCertificates.getIgnoreSSLContext().socketFactory
        connOpts.isHttpsHostnameVerificationEnabled = false
        connOpts.isCleanSession = true
        connOpts.userName = username
        connOpts.password = password.toCharArray()
        client.connect(connOpts)
        client.setCallback(Callback())
    }

    /**
     *
     */
    fun <T>getOpenRemoteSubscribeConnector(attributeName : String, assetId : String) : OpenRemoteSubscribeConnector<T>
    {
        return OpenRemoteSubscribeConnector<T>(client, attributeName, assetId, clientId)
    }

    /**
     *
     */
    fun <T>getOpenRemotePublishConnector(attributeName : String, assetId : String) : OpenRemotePublishConnector<T>
    {
        return OpenRemotePublishConnector<T>(client, attributeName, assetId, clientId)
    }

}