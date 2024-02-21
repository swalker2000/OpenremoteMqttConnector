fun main(args: Array<String>) {
    //val ignoreCertificates = IgnoreCertificates()
    //ignoreCertificates.ignoreCertificates()
    val openRemoteConnectorFactory = OpenRemoteConnectorFactory(
        System.getenv("host"),
        System.getenv("port").toInt(),
        System.getenv("clientId"),
        System.getenv("username"),
        System.getenv("password")
        )
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
    readln()
}