import connector.OpenRemoteConnectorFactory

fun main(args: Array<String>) {
    //Create factory
    val openRemoteConnectorFactory = OpenRemoteConnectorFactory(
        System.getenv("host"),
        System.getenv("port").toInt(),
        System.getenv("clientId"),
        System.getenv("realm"),
        )
        .authorizationByLoginAndPassword(System.getenv("username"),System.getenv("password"))
        .trustAllCerts()
        .build()
    //subscribe on event
    val subscribeConnector = openRemoteConnectorFactory.getOpenRemoteSubscribeConnector<String>( System.getenv("attributeSubscribeName"), System.getenv("assetId"))
    subscribeConnector.subscribe{println("new message : ${it.value}")}

    //publish event
    val publishConnector = openRemoteConnectorFactory.getOpenRemotePublishConnector<Boolean>( System.getenv("attributePublishName"), System.getenv("assetId"))
    var publishValue= false
    while (true)
    {
        publishConnector.publish(publishValue)
        publishValue=!publishValue
        Thread.sleep(3000)
    }
}