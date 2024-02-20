package messge

/**
 * Формат данных сообщения пришедшего по MQTT.
 * @param ref идентификатор сообщения приходящего по MQTT.
 * @param value полезные данные
 */
data class MqttMessage<T>(var ref : Ref, var value : T)
{
    var timestamp : Long = 0

    var deleted : Boolean = false

    var realm : String = ""
}
