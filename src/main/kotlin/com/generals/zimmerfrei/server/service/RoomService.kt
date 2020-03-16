package com.generals.zimmerfrei.server.service

import com.generals.zimmerfrei.server.database.RoomEntity
import com.generals.zimmerfrei.server.database.RoomRepository
import com.generals.zimmerfrei.server.outbound.RoomOutbound
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
    private val repository: RoomRepository
) : RoomService {

    override fun get(id: Int): Result<RoomOutbound> =
        repository.findById(id).map<Result<RoomOutbound>> { Result.Success(it.toOutbound()) }
            .orElse(Result.NotFound)

    override fun save(room: RoomOutbound) {
        repository.save(room.toEntity())
    }

    override fun update(id: Int, updated: RoomOutbound): Result<RoomOutbound> =
        repository.findById(id).map<Result<RoomOutbound>> { room: RoomEntity ->
            room.copy(
                name = updated.name,
                roomCount = updated.roomCount
            ).also { repository.save(it) }
                .let { Result.Success(it.toOutbound()) }
        }.orElse(Result.NotFound)

    override fun delete(id: Int): Result<RoomOutbound> =
        repository.findById(id).map<Result<RoomOutbound>> {
            repository.delete(it)
            Result.Success(it.toOutbound())
        }.orElse(Result.NotFound)

    override fun getAll(): Result<List<RoomOutbound>> = Result.Success(repository.findAll().map(RoomEntity::toOutbound))
}