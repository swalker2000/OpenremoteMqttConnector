import com.openremote.telebot.OpenRemoteConnectorRunnable

fun main(args: Array<String>) {
    //val ignoreCertificates = IgnoreCertificates()
    //ignoreCertificates.ignoreCertificates()
    val openRemoteConnectorFactory = OpenRemoteConnectorFactory(
        "",
        8883,
        "client123",
        "master:mqttuser",
        ""
        )
    val connector = openRemoteConnectorFactory.getOpenRemoteConnector<Boolean>("TextToBot", "")
    connector.subscribe{println("new message")}
    connector.start()
    readln()
}