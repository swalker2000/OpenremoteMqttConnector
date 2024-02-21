package com.openremote.telebot

import messge.OpenRemoteMqttMessage

fun interface OpenRemoteConnectorRunnable<T> {

    fun run(openRemoteMqttMessage : OpenRemoteMqttMessage<T>)
}