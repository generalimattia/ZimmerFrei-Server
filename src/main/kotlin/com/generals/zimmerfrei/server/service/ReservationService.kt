package com.generals.zimmerfrei.server.service

import com.generals.zimmerfrei.server.database.ReservationEntity
import com.generals.zimmerfrei.server.database.ReservationRepository
import com.generals.zimmerfrei.server.database.RoomEntity
import com.generals.zimmerfrei.server.database.RoomRepository
import com.generals.zimmerfrei.server.outbound.ReservationOutbound
import com.generals.zimmerfrei.server.outbound.RoomOutbound
import org.springframework.stereotype.Service
import java.util.*

interface ReservationService {
    fun get(id: Int): Optional<ReservationOutbound>
    fun save(reservation: ReservationOutbound)
    fun delete(reservation: ReservationOutbound)
    fun getAll(): List<ReservationOutbound>
}

@Service
class ReservationServiceImpl constructor(
    private val repository: ReservationRepository
) : ReservationService {

    override fun get(id: Int): Optional<ReservationOutbound> = repository.findById(id).map { it.toOutbound() }

    override fun save(reservation: ReservationOutbound) {
        repository.save(reservation.toEntity())
    }

    override fun delete(reservation: ReservationOutbound) {
        repository.delete(reservation.toEntity())
    }

    override fun getAll(): List<ReservationOutbound> = repository.findAll().map(ReservationEntity::toOutbound)
}

private fun RoomOutbound.toEntity(): RoomEntity = RoomEntity(id = id, name = name, roomCount = roomCount)
private fun RoomEntity.toOutbound(): RoomOutbound = RoomOutbound(id = id, name = name, roomCount = roomCount)
private fun ReservationOutbound.toEntity(): ReservationEntity = ReservationEntity(id = id, name = name, startDate = startDate, endDate = endDate)
private fun ReservationEntity.toOutbound(): ReservationOutbound = ReservationOutbound(id = id, name = name, startDate = startDate, endDate = endDate)