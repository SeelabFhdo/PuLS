package de.fhdo.puls.environment_service.command.domian

import org.springframework.data.annotation.Id
import org.springframework.data.mongodb.core.mapping.Document
import java.util.LinkedList

@Document
class SensorInformationSet(
        @Id var id: String?,
        var name: String,
        var sensorBoxId: Long,
        var ReceivedDataSetList: LinkedList<ReceivedDataSet> = LinkedList()
) {
    constructor( name: String, sensorBoxId: Long): this(null, name, sensorBoxId, LinkedList())
}