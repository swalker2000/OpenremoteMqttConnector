package connector

import connector.ssltools.IgnoreCertificates
import org.eclipse.paho.client.mqttv3.*
import org.slf4j.LoggerFactory


class OpenRemoteConnectorFactory(
    private val host : String,
    private val port : Int,
    private val clientId : String,
    private val realm : String) {

    private val logger = LoggerFactory.getLogger("${OpenRemoteConnectorFactory::class.java}.${clientId}")
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

    private var protocol : Protocol = Protocol.MQTTS

    private lateinit var client : MqttClient

    val connOpts = MqttConnectOptions()

    private val ignoreCertificates = IgnoreCertificates()

    private var builded = false

    /*****SETUP******/


    /**
     * Build OpenRemoteConnectorFactory.
     */
    fun build() : OpenRemoteConnectorFactory {
        val broker = "${protocol.urlPrefix}://$host:$port"
        client = MqttClient(broker, clientId)
        connOpts.isCleanSession = true
        client.connect(connOpts)
        client.setCallback(Callback())
        builded = true
        return this
    }

    /**
     * Authorization to MQTT by login and password.
     */
    fun authorizationByLoginAndPassword(username : String, password : String,) : OpenRemoteConnectorFactory
    {
        connOpts.userName = "$realm:$username"
        connOpts.password = password.toCharArray()
        return this
    }

    /**
     *   Trust all SSL certs.
     *   (only for mqtts)
     */
    fun trustAllCerts() : OpenRemoteConnectorFactory
    {
        connOpts.socketFactory=ignoreCertificates.getIgnoreSSLContext().socketFactory
        connOpts.isHttpsHostnameVerificationEnabled = false
        return this
    }

    /**
     * Set protocol (mqtt or mqtts).
     * (default mqtts)
     */
    fun setProtocol(protocol: Protocol)
    {
        this.protocol = protocol
    }


    /**
     *
     */
    fun <T>getOpenRemoteSubscribeConnector(attributeName : String, assetId : String) : OpenRemoteSubscribeConnector<T>
    {
        buildIfNotBuilded()
        return OpenRemoteSubscribeConnector<T>(client, attributeName, assetId, clientId, realm)
    }

    /**
     *
     */
    fun <T>getOpenRemotePublishConnector(attributeName : String, assetId : String) : OpenRemotePublishConnector<T>
    {
        buildIfNotBuilded()
        return OpenRemotePublishConnector<T>(client, attributeName, assetId, clientId, realm)
    }

    private fun buildIfNotBuilded()
    {
        if(!builded)
            build()
    }

}