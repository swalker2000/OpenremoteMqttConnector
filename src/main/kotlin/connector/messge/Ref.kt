package connector.messge

/**
 * Идентификатор сообщения приходящего по MQTT.
 */
data class Ref(val id : String, val name : String)
{
    constructor() : this("", "")
}

