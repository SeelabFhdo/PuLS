package de.fhdo.puls.park_and_charge_service.command.domain

import java.sql.Timestamp
import java.util.*

class CPIChangeMessage (
        var chargingPointId: Long,
        var chargedEnergy: Float,
        var userId: String,
        var current_L1: Float,
        var current_L2: Float,
        var current_L3: Float,
        var voltage_L1: Float,
        var voltage_L2: Float,
        var voltage_L3: Float,
        var current_Limit: Int,
        var timestamp: Date,
        var status: Status
)

enum class Status {
    A,
    B,
    C,
    D
}