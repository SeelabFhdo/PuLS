package de.fhdo.puls.environment_service.query.service

import de.fhdo.puls.environment_service.common.event.SensorValue
import de.fhdo.puls.environment_service.query.domain.EnvironmentInformation
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.data.mongodb.core.MongoTemplate
import org.springframework.data.mongodb.core.query.Criteria
import org.springframework.data.mongodb.core.query.Query
import org.springframework.stereotype.Service
import java.util.LinkedList

@Service
class EnvironmentInformationService {

    @Autowired
    lateinit var mongoTemplate: MongoTemplate

    @Throws(Exception::class)
    fun getLatestSensorValue( sensorBoxId: Long, sensor: String, numberOfValues: Int): LinkedList<SensorValue>? {
        val query = Query()
        query.addCriteria(Criteria.where("sensorBoxId").`is`(sensorBoxId))

        query.fields().include(sensor)
        //Note: The minus value of the numberOfValues is used to receive the latest list entries.
        query.fields().include("sensorBoxId").slice(sensor + "Data", -numberOfValues)
        val result = mongoTemplate.findOne(query, EnvironmentInformation::class.java)

        return when(sensor) {
            "temperature" -> result?.temperatureData
            "light" -> result?.lightData
            "particulate2" -> result?.particulate2Data
            "particulate10" -> result?.particulate10Data
            "humidity" -> result?.humidityData
            else -> throw Exception("No data found for $sensor")
        }
    }

    fun getMeasurementTypes(): List<String> {
        val measurementTypes = LinkedList<String>()
        measurementTypes.add("humidity")
        measurementTypes.add("temperature")
        measurementTypes.add("particulate2")
        measurementTypes.add("particulate10")
        measurementTypes.add("light")
        return measurementTypes
    }
}