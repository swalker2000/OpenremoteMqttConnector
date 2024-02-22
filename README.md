<h1>Java and Kotlin library for connecting to an openremote server as a client.</h1>
<h2>Connecting the library</h2>
Clone the project and add it to the local maven repository using the command.

     gradle publishToMavenLocal
Or build the project using your IDE and add it to the local maven repository using it.

    repositories {
        mavenCentral()
        mavenLocal()//add this
    }
And include the library

    implementation("org.openremote:Openremotemqttconnector:0.0.0")


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
