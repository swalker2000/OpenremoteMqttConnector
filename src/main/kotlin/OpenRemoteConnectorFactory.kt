import org.eclipse.paho.client.mqttv3.*
import org.slf4j.LoggerFactory
import java.security.KeyStore
import java.security.SecureRandom
import javax.net.ssl.SSLContext
import javax.net.ssl.TrustManagerFactory


class OpenRemoteConnectorFactory(
    val host : String,
    val port : Int,
    val clientId : String,
    val username : String,
    val password : String) {

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
    fun <T>getOpenRemoteConnector(attributeName : String, assetId : String) : OpenRemoteSubscribeConnector<T>
    {
        return OpenRemoteSubscribeConnector<T>(client, attributeName, assetId, clientId)
    }

}