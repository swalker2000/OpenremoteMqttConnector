import org.eclipse.paho.client.mqttv3.*


class OpenRemoteConnectorFactory(
    val host : String,
    val port : Int,
    val clientId : String,
    val username : String,
    val password : String) {



    /*******CONNECTION*******/

    private val broker = "tcp://$host:$port"

    private val client = MqttClient(broker, clientId)

    init {
        val connOpts = MqttConnectOptions()
        connOpts.isCleanSession = true
        connOpts.userName = username
        connOpts.password = password.toCharArray()
        client.connect(connOpts)
    }

    /**
     *
     */
    fun <T>getOpenRemoteConnector(attributeName : String, assetId : String) : OpenRemoteSubscribeConnector<T>
    {
        //:todo посмотреть как расширить область видимости
        val client = this.client
        val clientId = this.clientId
        return object : OpenRemoteSubscribeConnector<T>()
        {
            override val client : MqttClient =  client
            override val attributeName : String = attributeName
            override val assetId : String = assetId
            override val clientId: String = clientId
        }
    }

}