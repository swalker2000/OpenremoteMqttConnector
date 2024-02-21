package connector

enum class Protocol(val urlPrefix : String) {
    MQTT("tcp"),
    MQTTS("ssl")
}