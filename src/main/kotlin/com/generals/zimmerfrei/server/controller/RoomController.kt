package com.generals.zimmerfrei.server.controller

import com.generals.zimmerfrei.server.outbound.RoomOutbound
import com.generals.zimmerfrei.server.service.RoomService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/rooms")
class RoomController {

    @Autowired
    private lateinit var service: RoomService

    @PostMapping
    fun save(room: RoomOutbound) {
        service.save(room)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, room: RoomOutbound) {
        service.save(room)
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int, room: RoomOutbound) {
        service.delete(room)
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): RoomOutbound =
        service.get(id).map { room: RoomOutbound ->
            val roomLink: Link = linkTo<RoomController>().slash(id).withSelfRel()
            room.apply {
                add(roomLink)
            }
        }.orElseThrow { ResponseStatusException(HttpStatus.NOT_FOUND) }

    @GetMapping
    fun getAll(): CollectionModel<RoomOutbound> {
        val allRooms: List<RoomOutbound> = service.getAll().map { room: RoomOutbound ->
            val roomLink: Link = linkTo<RoomController>().slash(room.id).withSelfRel()
            room.apply {
                add(roomLink)
            }
        }
        return CollectionModel(allRooms, linkTo<RoomController>().withSelfRel())
    }
}

inline fun <reified T> linkTo(): WebMvcLinkBuilder = linkTo(T::class.java)