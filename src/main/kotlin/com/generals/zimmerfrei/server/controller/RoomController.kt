package com.generals.zimmerfrei.server.controller

import com.generals.zimmerfrei.server.database.RoomEntity
import com.generals.zimmerfrei.server.service.RoomService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class RoomController {

    @Autowired
    private lateinit var service: RoomService

    @GetMapping("/rooms")
    fun fetchAllRooms(): List<RoomEntity> = service.fetchAllRooms()
}