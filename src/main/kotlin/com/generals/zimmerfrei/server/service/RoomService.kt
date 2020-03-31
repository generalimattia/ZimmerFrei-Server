package com.generals.zimmerfrei.server.service

import com.generals.zimmerfrei.server.database.ReservationEntity
import com.generals.zimmerfrei.server.database.ReservationRepository
import com.generals.zimmerfrei.server.database.RoomEntity
import com.generals.zimmerfrei.server.database.RoomRepository
import com.generals.zimmerfrei.server.outbound.RoomOutbound
import com.generals.zimmerfrei.server.outbound.toEntity
import com.generals.zimmerfrei.server.outbound.toOutbound
import org.springframework.stereotype.Service

interface RoomService {
    fun get(id: Int): Result<RoomOutbound>
    fun save(room: RoomOutbound)
    fun update(id: Int, updated: RoomOutbound): Result<RoomOutbound>
    fun delete(id: Int): Result<RoomOutbound>
    fun getAll(): Result<List<RoomOutbound>>
}

@Service
class RoomServiceImpl constructor(
    private val roomRepository: RoomRepository,
    private val reservationRepository: ReservationRepository
) : RoomService {

    override fun get(id: Int): Result<RoomOutbound> =
        roomRepository.findById(id).map<Result<RoomOutbound>> { Result.Success(it.toOutbound()) }
            .orElse(Result.NotFound)

    override fun save(room: RoomOutbound) {
        roomRepository.save(room.toEntity())
    }

    override fun update(id: Int, updated: RoomOutbound): Result<RoomOutbound> =
        roomRepository.findById(id).map<Result<RoomOutbound>> { room: RoomEntity ->
            room.copy(
                name = updated.name,
                maxPersons = updated.maxPersons
            ).also { roomRepository.save(it) }
                .let { Result.Success(it.toOutbound()) }
        }.orElse(Result.NotFound)

    override fun delete(id: Int): Result<RoomOutbound> =
        roomRepository.findById(id).map<Result<RoomOutbound>> { room: RoomEntity ->
            val reservationsByRoom: List<ReservationEntity> = reservationRepository.findByRoom(room)
            if (reservationsByRoom.isEmpty()) {
                roomRepository.delete(room)
                Result.Success(room.toOutbound())
            } else {
                Result.Forbidden
            }
        }.orElse(Result.NotFound)

    override fun getAll(): Result<List<RoomOutbound>> =
        Result.Success(roomRepository.findAll().map(RoomEntity::toOutbound))
}