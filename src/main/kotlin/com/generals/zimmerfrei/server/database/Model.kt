package com.generals.zimmerfrei.server.database

import org.threeten.bp.LocalDate
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id

@Entity
data class Room(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Int,
    val name: String
)

@Entity
data class Reservation(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Int,
    val name: String,
    val startDate: LocalDate,
    val endDate: LocalDate
)