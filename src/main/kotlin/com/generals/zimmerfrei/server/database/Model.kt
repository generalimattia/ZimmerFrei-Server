package com.generals.zimmerfrei.server.database

import org.springframework.data.jpa.convert.threetenbp.ThreeTenBackPortJpaConverters
import org.threeten.bp.LocalDate
import javax.persistence.*

@Entity
@Table(name = "room")
data class RoomEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Int = 0,
    val name: String,
    val maxPersons: Int,
    @ManyToMany(mappedBy = "rooms") val reservations: List<ReservationEntity> = emptyList()
)

@Entity
@Table(name = "reservation")
data class ReservationEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Int = 0,
    val name: String,
    val persons: Int,
    @Convert(converter = ThreeTenBackPortJpaConverters.LocalDateConverter::class) val startDate: LocalDate,
    @Convert(converter = ThreeTenBackPortJpaConverters.LocalDateConverter::class) val endDate: LocalDate,
    @ManyToMany
    @JoinTable(
        name = "reservation_room",
        joinColumns = [JoinColumn(name = "reservation_id")],
        inverseJoinColumns = [JoinColumn(name = "room_id")]
    )
    val rooms: List<RoomEntity> = emptyList(),
    @OneToOne(cascade = [CascadeType.PERSIST])
    @JoinColumn(name = "customer_id", referencedColumnName = "id")
    val customer: CustomerEntity,
    @Lob
    val notes: String = "",
    val color: String = "",
    val adults: Int,
    val children: Int,
    val babies: Int
)

@Entity
@Table(name = "customer")
data class CustomerEntity(
    @Id @GeneratedValue(strategy = GenerationType.AUTO) val id: Int = 0,
    val firstName: String,
    val lastName: String,
    @Column(unique = true) val socialId: String,
    val mobile: String,
    val email: String,
    val address: String,
    val city: String,
    val province: String,
    val state: String,
    val zip: String,
    val gender: String,
    @Convert(converter = ThreeTenBackPortJpaConverters.LocalDateConverter::class) val birthDate: LocalDate,
    val birthPlace: String
)