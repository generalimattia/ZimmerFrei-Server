package com.generals.zimmerfrei.server.controller

import com.generals.zimmerfrei.server.outbound.ReservationOutbound
import com.generals.zimmerfrei.server.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException

@RestController
@RequestMapping("/reservations")
class ReservationController {

    @Autowired
    private lateinit var service: ReservationService

    @PostMapping
    fun save(@RequestBody reservation: ReservationOutbound) {
        service.save(reservation)
    }

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody updated: ReservationOutbound) {
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
    fun get(@PathVariable id: Int): ReservationOutbound =
        service.get(id).fold(
            ifSuccess = { reservation: ReservationOutbound ->
                val reservationLink: Link = linkTo<ReservationController>().slash(id).withSelfRel()
                reservation.apply {
                    add(reservationLink)
                }
            },
            orElse = { throw ResponseStatusException(HttpStatus.NOT_FOUND) }
        )

    @GetMapping
    fun getAll(): CollectionModel<ReservationOutbound> {
        val allReservations: List<ReservationOutbound> = service.getAll().fold(
            ifSuccess = { reservations: List<ReservationOutbound> ->
                reservations.map { reservation: ReservationOutbound ->
                    val reservationLink: Link = linkTo<ReservationController>().slash(reservation.id).withSelfRel()
                    reservation.apply {
                        add(reservationLink)
                    }
                }
            }, orElse = { emptyList() })
        return CollectionModel(allReservations, linkTo<ReservationController>().withSelfRel())
    }
}