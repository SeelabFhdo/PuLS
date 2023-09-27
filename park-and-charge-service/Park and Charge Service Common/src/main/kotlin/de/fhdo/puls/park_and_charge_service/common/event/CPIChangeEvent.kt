package de.fhdo.puls.park_and_charge_service.common.event

class CPIChangeEvent (
        val id_command: String,
        val id_query: String,
        val name: String,
        val activated: Boolean
)