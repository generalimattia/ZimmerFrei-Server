package com.generals.zimmerfrei.server.outbound

import com.generals.zimmerfrei.server.database.CustomerEntity
import com.generals.zimmerfrei.server.database.ReservationEntity
import com.generals.zimmerfrei.server.database.RoomEntity


fun RoomOutbound.toEntity(): RoomEntity = RoomEntity(
    id = id,
    name = name,
    roomCount = roomCount
)

fun RoomEntity.toOutbound(): RoomOutbound = RoomOutbound(
    id = id,
    name = name,
    roomCount = roomCount
)

fun ReservationOutbound.toEntity(): ReservationEntity =
    ReservationEntity(
        id = id,
        name = name,
        startDate = startDate,
        endDate = endDate,
        numberOfParticipants = numberOfParticipants,
        customer = customer.toEntity()
    )

fun ReservationEntity.toOutbound(): ReservationOutbound =
    ReservationOutbound(
        id = id,
        name = name,
        startDate = startDate,
        endDate = endDate,
        numberOfParticipants = numberOfParticipants,
        customer = customer.toOutbound()
    )

fun CustomerEntity.toOutbound(): CustomerOutbound =
    CustomerOutbound(
        id = id,
        firstName = firstName,
        lastName = lastName,
        socialId = socialId,
        mobile = mobile,
        email = email,
        address = address,
        birthDate = birthDate
    )

fun CustomerOutbound.toEntity(): CustomerEntity =
    CustomerEntity(
        id = id,
        firstName = firstName,
        lastName = lastName,
        socialId = socialId,
        mobile = mobile,
        email = email,
        address = address,
        birthDate = birthDate
    )