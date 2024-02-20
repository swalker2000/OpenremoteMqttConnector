package com.openremote.telebot

import messge.MqttMessage

interface OpenRemoteConnectorRunnable<T> {

    fun run(mqttMessage : MqttMessage<T>)
}