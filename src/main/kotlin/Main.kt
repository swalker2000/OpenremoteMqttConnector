import connector.OpenRemoteConnectorFactory

fun main(args: Array<String>) {
    //val ignoreCertificates = connector.ssltools.IgnoreCertificates()
    //ignoreCertificates.ignoreCertificates()
    val openRemoteConnectorFactory = OpenRemoteConnectorFactory(
        System.getenv("host"),
        System.getenv("port").toInt(),
        System.getenv("clientId"),
        )
        .authorizationByLoginAndPassword(System.getenv("username"),System.getenv("password"))
        .trustAllCerts()
        .build()
    val subscribeConnector = openRemoteConnectorFactory.getOpenRemoteSubscribeConnector<String>( System.getenv("attributeSubscribeName"), System.getenv("assetId"))
    val publishConnector = openRemoteConnectorFactory.getOpenRemotePublishConnector<Boolean>( System.getenv("attributePublishName"), System.getenv("assetId"))
    subscribeConnector.subscribe{println("new message : ${it.value}")}
    var publishValue= false
    while (true)
    {
        publishConnector.publish(publishValue)
        publishValue=!publishValue
        Thread.sleep(3000)
    }
}