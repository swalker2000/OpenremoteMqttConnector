package com.openremote.telebot

import messge.MqttMessage

fun interface OpenRemoteConnectorRunnable<T> {

    fun run(mqttMessage : MqttMessage<T>)
}