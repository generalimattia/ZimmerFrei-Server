package com.generals.zimmerfrei.server.controller

import com.generals.zimmerfrei.server.outbound.CustomerOutbound
import com.generals.zimmerfrei.server.service.CustomerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/customers")
class CustomerController {

    @Autowired
    private lateinit var service: CustomerService

    @PostMapping
    fun save(@RequestBody room: CustomerOutbound) {
        service.save(room)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody updated: CustomerOutbound) {
        service.update(id, updated).fold(
            ifSuccess = {},
            orElse = { throw ResponseStatusException(HttpStatus.NOT_FOUND) }
        )
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        service.delete(id).fold(
            ifSuccess = {},
            orElse = { throw ResponseStatusException(HttpStatus.NOT_FOUND) }
        )
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): CustomerOutbound =
        service.get(id).fold(
            ifSuccess = { room: CustomerOutbound ->
                val roomLink: Link = linkTo<CustomerController>().slash(id).withSelfRel()
                room.apply {
                    add(roomLink)
                }
            },
            orElse = { throw ResponseStatusException(HttpStatus.NOT_FOUND) }
        )

    @GetMapping
    fun getAll(): CollectionModel<CustomerOutbound> {
        val allRooms: List<CustomerOutbound> = service.getAll().fold(
            ifSuccess = { rooms: List<CustomerOutbound> ->
                rooms.map { room: CustomerOutbound ->
                    val roomLink: Link = linkTo<CustomerController>().slash(room.id).withSelfRel()
                    room.apply {
                        add(roomLink)
                    }
                }
            },
            orElse = { emptyList() }
        )
        return CollectionModel(allRooms, linkTo<CustomerController>().withSelfRel())
    }
}