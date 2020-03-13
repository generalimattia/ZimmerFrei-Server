package com.generals.zimmerfrei.server.database

import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.threeten.bp.LocalDate

interface RoomRepository : CrudRepository<RoomEntity, Int> {
}

interface ReservationRepository : CrudRepository<ReservationEntity, Int> {

    fun findByStartDateBetween(@Param("startDate") startDate: LocalDate, @Param("endDate") endDate: LocalDate): List<ReservationEntity>

}