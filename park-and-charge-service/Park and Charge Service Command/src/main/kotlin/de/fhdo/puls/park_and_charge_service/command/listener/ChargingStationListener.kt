package de.fhdo.puls.park_and_charge_service.command.listener

import de.fhdo.puls.park_and_charge_service.command.service.ElectrifiedParkingSpaceService
import mu.KotlinLogging
import org.eclipse.paho.client.mqttv3.IMqttDeliveryToken
import org.eclipse.paho.client.mqttv3.MqttCallback
import org.eclipse.paho.client.mqttv3.MqttClient
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.eclipse.paho.client.mqttv3.MqttMessage
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import javax.annotation.PostConstruct

@Service
class ChargingStationListener {
    @Value("\${mqtt.server.uri}")
    lateinit var mqttServerUri: String

    @Value("\${mqtt.charging.topic}")
    lateinit var mqttTopic: String

    @Autowired
    lateinit var electrifiedParkingSpaceService: ElectrifiedParkingSpaceService

    private val logger = KotlinLogging.logger {}

    @PostConstruct
    fun consumeMqttMessage() {
        val client = MqttClient(mqttServerUri, MqttClient.generateClientId())
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
                val message = String(m.payload)
                when {
                    t.endsWith("status") -> electrifiedParkingSpaceService.handleIncomingStatusMessage(message)
                    t.endsWith("keepalive") -> electrifiedParkingSpaceService.handleIncomingKeepAliveMessage(message)
                    t.endsWith("charging") -> electrifiedParkingSpaceService.handleIncomingChargingMessage(message)
                }
            }

            override fun deliveryComplete(t: IMqttDeliveryToken) {
            }
        })
        client.connect(options)
        client.subscribe(mqttTopic)
    }

}