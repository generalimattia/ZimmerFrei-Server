package com.generals.zimmerfrei.server.outbound

import com.generals.zimmerfrei.server.database.CustomerEntity
import com.generals.zimmerfrei.server.database.ReservationEntity
import com.generals.zimmerfrei.server.database.RoomEntity


fun RoomOutbound.toEntity(): RoomEntity = RoomEntity(
    id = id,
    name = name,
    maxPersons = maxPersons
)

fun RoomEntity.toOutbound(): RoomOutbound = RoomOutbound(
    id = id,
    name = name,
    maxPersons = maxPersons
)

fun ReservationOutbound.toEntity(): ReservationEntity =
    ReservationEntity(
        id = id,
        name = name,
        startDate = startDate,
        endDate = endDate,
        persons = persons,
        customer = customer.toEntity(),
        notes = notes,
        color = color,
        adults = adults,
        children = children,
        babies = babies
    )

fun ReservationEntity.toOutbound(): ReservationOutbound =
    ReservationOutbound(
        id = id,
        name = name,
        startDate = startDate,
        endDate = endDate,
        persons = persons,
        customer = customer.toOutbound(),
        notes = notes,
        color = color,
        adults = adults,
        children = children,
        babies = babies
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
        city = city,
        province = province,
        state = state,
        zip = zip,
        gender = gender,
        birthDate = birthDate,
        birthPlace = birthPlace
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
        city = city,
        province = province,
        state = state,
        zip = zip,
        gender = gender,
        birthDate = birthDate,
        birthPlace = birthPlace
    )