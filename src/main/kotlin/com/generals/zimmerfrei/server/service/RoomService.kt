package com.generals.zimmerfrei.server.service

import com.generals.zimmerfrei.server.database.RoomEntity
import com.generals.zimmerfrei.server.database.RoomRepository
import org.springframework.stereotype.Service

interface RoomService {
    fun fetchAllRooms(): List<RoomEntity>
}

@Service
class RoomServiceImpl constructor(
    private val repository: RoomRepository
) : RoomService {

    override fun fetchAllRooms(): List<RoomEntity> = repository.findAll().toList()
}