package com.generals.zimmerfrei.server.service

import com.generals.zimmerfrei.server.database.RoomEntity
import com.generals.zimmerfrei.server.database.RoomRepository
import com.generals.zimmerfrei.server.outbound.RoomOutbound
import org.springframework.stereotype.Service
import java.util.*

interface RoomService {
    fun get(id: Int): Optional<RoomOutbound>
    fun save(room: RoomOutbound)
    fun delete(room: RoomOutbound)
    fun getAll(): List<RoomOutbound>
}

@Service
class RoomServiceImpl constructor(
    private val repository: RoomRepository
) : RoomService {

    override fun get(id: Int): Optional<RoomOutbound> = repository.findById(id).map { it.toOutbound() }

    override fun save(room: RoomOutbound) {
        repository.save(room.toEntity())
    }

    override fun delete(room: RoomOutbound) {
        repository.delete(room.toEntity())
    }

    override fun getAll(): List<RoomOutbound> = repository.findAll().map(RoomEntity::toOutbound)
}

private fun RoomOutbound.toEntity(): RoomEntity = RoomEntity(id = id, name = name, roomCount = roomCount)
private fun RoomEntity.toOutbound(): RoomOutbound = RoomOutbound(id = id, name = name, roomCount = roomCount)