import com.openremote.telebot.OpenRemoteConnectorRunnable

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
    val connector = openRemoteConnectorFactory.getOpenRemoteConnector<Boolean>( System.getenv("attributeName"), System.getenv("assetId"))
    connector.subscribe{println("new message : ${it.value}")}
    readln()
}