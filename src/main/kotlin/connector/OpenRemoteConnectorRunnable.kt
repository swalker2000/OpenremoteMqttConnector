package connector

import connector.messge.OpenRemoteMqttMessage

fun interface OpenRemoteConnectorRunnable<T> {

    fun run(openRemoteMqttMessage : OpenRemoteMqttMessage<T>)
}