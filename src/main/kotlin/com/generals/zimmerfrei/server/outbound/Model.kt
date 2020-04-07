package com.generals.zimmerfrei.server.outbound

import org.springframework.hateoas.RepresentationModel
import org.springframework.hateoas.server.core.Relation
import org.threeten.bp.LocalDate

@Relation(value = "room", collectionRelation = "rooms")
open class RoomOutbound(
    val id: Int,
    val name: String,
    val maxPersons: Int
) : RepresentationModel<RoomOutbound>()

@Relation(value = "reservation", collectionRelation = "reservations")
open class ReservationOutbound(
    val id: Int,
    val name: String,
    val persons: Int,
    val startDate: LocalDate,
    val endDate: LocalDate,
    val customer: CustomerOutbound,
    val notes: String,
    val color: String,
    val adults: Int,
    val children: Int,
    val babies: Int
) : RepresentationModel<ReservationOutbound>()

@Relation(value = "customer", collectionRelation = "customers")
open class CustomerOutbound(
    val id: Int,
    val firstName: String,
    val lastName: String,
    val socialId: String,
    val mobile: String,
    val email: String,
    val address: String,
    val city: String,
    val province: String,
    val state: String,
    val zip: String,
    val gender: String,
    val birthDate: LocalDate,
    val birthPlace: String
) : RepresentationModel<ReservationOutbound>()