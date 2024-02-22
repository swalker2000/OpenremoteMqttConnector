<h1>Java and Kotlin library for connecting to an openremote server as a client.</h1>
<h2>Quick start</h2>
An example of subscribing and publishing an event.<br/>

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
    publishConnector.publish(publishValue)
