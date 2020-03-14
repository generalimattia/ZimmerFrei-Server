package com.generals.zimmerfrei.server.outbound

import org.springframework.hateoas.RepresentationModel
import org.threeten.bp.LocalDate

open class RoomOutbound(
    val id: Int,
    val name: String,
    val roomCount: Int
) : RepresentationModel<RoomOutbound>()

open class ReservationOutbound(
    val id: Int,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate
) : RepresentationModel<ReservationOutbound>()