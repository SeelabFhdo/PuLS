package de.fhdo.puls.environment_service.command.listener

import de.fhdo.puls.environment_service.command.service.EnvironmentSensorUnitService
import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttClient.generateClientId
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class CNIMqttListener {
    @Autowired
    lateinit var environmentSensorUnitService: EnvironmentSensorUnitService
    @Value("\${mqtt.server.uri}")
    lateinit var mqttServerUri: String

    @Value("\${mqtt.topic}")
    lateinit var mqttTopic: String

    private val logger = KotlinLogging.logger {}

    @PostConstruct
    fun consumeMqttMessage() {
        val client = MqttClient(mqttServerUri, generateClientId())
        val options = MqttConnectOptions()
        options.isAutomaticReconnect = true
        options.isCleanSession = false
        options.keepAliveInterval = 1

        client.setCallback(object : MqttCallback {
            override fun connectionLost(throwable: Throwable) {
                logger.info{"Connection Lost."}
            }

            @Throws(Exception::class)
            override fun messageArrived(t: String, m: MqttMessage) {
                logger.info{"MQTT Message received: ${String(m.payload)}"}
                logger.info{"Size: ${m.payload.size}"}
                if(m.payload.size == 84) {
                    environmentSensorUnitService.handleIncomingSensorValues(String(m.payload))
                } else {
                    logger.error{"Message format not supported. Message: ${String(m.payload)}"}
                }
            }

            override fun deliveryComplete(t: IMqttDeliveryToken) {
            }
        })
        client.connect(options)
        client.subscribe(mqttTopic)
    }
}