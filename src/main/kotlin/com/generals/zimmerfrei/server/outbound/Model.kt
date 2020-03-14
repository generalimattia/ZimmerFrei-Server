package com.generals.zimmerfrei.server.outbound

import org.threeten.bp.LocalDate

data class RoomOutbound(
    val id: Int,
    val name: String,
    val roomCount: Int
)

data class ReservationOutbound(
    val id: Int,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)