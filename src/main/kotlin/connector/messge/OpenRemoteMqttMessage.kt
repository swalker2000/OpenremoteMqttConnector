package connector.messge

/**
 * Формат данных сообщения пришедшего по MQTT.
 * @param ref идентификатор сообщения приходящего по MQTT.
 * @param value полезные данные
 */
data class OpenRemoteMqttMessage<T>(var ref : Ref, var value : T?)
{
    constructor(): this(Ref(), null)
    var timestamp : Long = 0

    var deleted : Boolean = false

    var realm : String = ""
}
