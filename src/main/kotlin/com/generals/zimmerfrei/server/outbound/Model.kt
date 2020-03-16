package com.generals.zimmerfrei.server.outbound

import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import org.threeten.bp.LocalDate

@Relation(value = "room", collectionRelation = "rooms")
open class RoomOutbound(
    val id: Int,
    val name: String,
    val roomCount: Int
) : RepresentationModel<RoomOutbound>()

@Relation(value = "reservation", collectionRelation = "reservations")
open class ReservationOutbound(
    val id: Int,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate
) : RepresentationModel<ReservationOutbound>()