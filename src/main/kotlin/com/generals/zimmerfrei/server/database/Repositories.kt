package com.generals.zimmerfrei.server.database

import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.data.repository.query.Param
import org.threeten.bp.LocalDate

interface RoomRepository : CrudRepository<RoomEntity, Int> {
}

interface ReservationRepository : CrudRepository<ReservationEntity, Int> {

    @Query("SELECT r from ReservationEntity r WHERE :room MEMBER OF r.rooms")
    fun findByRoom(@Param("room") room: RoomEntity): List<ReservationEntity>

    @Query("SELECT r from ReservationEntity r WHERE :room MEMBER OF r.rooms AND r.startDate >= :startDate AND r.startDate <= :endDate")
    fun findByRoomAndStartDateBetween(
        @Param("room") room: RoomEntity,
        @Param("startDate") startDate: LocalDate,
        @Param("endDate") endDate: LocalDate
    ): List<ReservationEntity>

}

interface CustomerRepository : CrudRepository<CustomerEntity, Int> {
}