package com.generals.zimmerfrei.server.database

import org.springframework.data.jpa.convert.threetenbp.ThreeTenBackPortJpaConverters
import org.threeten.bp.LocalDate
import javax.persistence.*

@Entity
@Table(name = "room")
data class RoomEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Int = 0,
    val name: String,
    val roomCount: Int,
    @ManyToMany(mappedBy = "rooms") val reservations: List<ReservationEntity> = emptyList()
)

@Entity
@Table(name = "reservation")
data class ReservationEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Int = 0,
    val name: String,
    @Convert(converter = ThreeTenBackPortJpaConverters.LocalDateConverter::class) val startDate: LocalDate,
    @Convert(converter = ThreeTenBackPortJpaConverters.LocalDateConverter::class) val endDate: LocalDate,
    @ManyToMany
    @JoinTable(
        name = "reservation_room",
        joinColumns = [JoinColumn(name = "reservation_id")],
        inverseJoinColumns = [JoinColumn(name = "room_id")]
    )
    val rooms: List<RoomEntity> = emptyList()
)