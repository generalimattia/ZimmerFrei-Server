package com.generals.zimmerfrei.server.database

import org.springframework.data.repository.CrudRepository

interface RoomRepository : CrudRepository<Room, Int>
interface ReservationRepository : CrudRepository<Reservation, Int>