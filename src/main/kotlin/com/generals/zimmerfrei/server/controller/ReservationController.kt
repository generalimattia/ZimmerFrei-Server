package com.generals.zimmerfrei.server.controller

import com.generals.zimmerfrei.server.outbound.ReservationOutbound
import com.generals.zimmerfrei.server.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import org.springframework.web.server.ResponseStatusException
import org.threeten.bp.Instant
import org.threeten.bp.LocalDate
import org.threeten.bp.ZoneId
import java.util.*

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

    @GetMapping(params = ["roomId", "from", "to"])
    fun getByRoomAndFromDateToDate(
        @RequestParam("roomId") roomId: Int,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: Date,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: Date
    ): CollectionModel<ReservationOutbound> =
        service.getByRoomAndFromDateToDate(roomId, from.toLocalDate(), to.toLocalDate()).fold(
            ifSuccess = { reservations: List<ReservationOutbound> ->
                val reservationsWithLink: List<ReservationOutbound> = reservations.fillWithLink()
                CollectionModel(reservationsWithLink, linkTo<ReservationController>().withSelfRel())
            },
            orElse = { throw ResponseStatusException(HttpStatus.NOT_FOUND) }
        )


    @GetMapping
    fun getAll(): CollectionModel<ReservationOutbound> {
        val allReservations: List<ReservationOutbound> = service.getAll().fold(
            ifSuccess = List<ReservationOutbound>::fillWithLink,
            orElse = { emptyList() })
        return CollectionModel(allReservations, linkTo<ReservationController>().withSelfRel())
    }
}

fun Date.toLocalDate(): LocalDate = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()

fun List<ReservationOutbound>.fillWithLink(): List<ReservationOutbound> =
    map { reservation: ReservationOutbound ->
        val reservationLink: Link = linkTo<ReservationController>().slash(reservation.id).withSelfRel()
        val customerLink: Link = linkTo<CustomerController>().slash(reservation.customer.id).withSelfRel()
        reservation.customer.add(customerLink)
        reservation.apply {
            add(reservationLink)
        }
    }
