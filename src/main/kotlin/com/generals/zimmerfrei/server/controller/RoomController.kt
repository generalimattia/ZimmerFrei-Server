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
    fun save(@RequestBody room: RoomOutbound): RoomOutbound =
        service.save(room).fold(
            ifSuccess = { it },
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) },
            ifConflict = { throw ResponseStatusException(HttpStatus.CONFLICT) }
        )

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody updated: RoomOutbound): RoomOutbound =
        service.update(id, updated).fold(
            ifSuccess = { it },
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) },
            ifConflict = { throw ResponseStatusException(HttpStatus.CONFLICT) }
        )

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        service.delete(id).fold(
            ifSuccess = {},
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) },
            ifConflict = { throw ResponseStatusException(HttpStatus.CONFLICT) }
        )
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): RoomOutbound =
        service.get(id).fold(
            ifSuccess = { room: RoomOutbound ->
                val roomLink: Link = linkTo<RoomController>().slash(id).withSelfRel()
                room.apply {
                    add(roomLink)
                }
            },
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) },
            ifConflict = { throw ResponseStatusException(HttpStatus.CONFLICT) }
        )

    @GetMapping
    fun getAll(): CollectionModel<RoomOutbound> {
        val allRooms: List<RoomOutbound> = service.getAll().fold(
            ifSuccess = { rooms: List<RoomOutbound> ->
                rooms.map { room: RoomOutbound ->
                    val roomLink: Link = linkTo<RoomController>().slash(room.id).withSelfRel()
                    room.apply {
                        add(roomLink)
                    }
                }
            },
            ifNotFound = { emptyList() },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) },
            ifConflict = { throw ResponseStatusException(HttpStatus.CONFLICT) }
        )
        return CollectionModel(allRooms, linkTo<RoomController>().withSelfRel())
    }
}

inline fun <reified T> linkTo(): WebMvcLinkBuilder = linkTo(T::class.java)