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
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) }
        )
    }

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) {
        service.delete(id).fold(
            ifSuccess = {},
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) }
        )
    }

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): ReservationOutbound =
        service.get(id).fold(
            ifSuccess = ReservationOutbound::fillWithLink,
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) }
        )

    @GetMapping(params = ["roomId", "from", "to"])
    fun getByRoomAndFromDateToDate(
        @RequestParam("roomId") roomId: Int,
        @RequestParam("from") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) from: Date,
        @RequestParam("to") @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) to: Date
    ): CollectionModel<ReservationOutbound> =
        service.getByRoomAndFromDateToDate(roomId, from.toLocalDate(), to.toLocalDate()).fold(
            ifSuccess = { reservations: List<ReservationOutbound> ->
                val reservationsWithLink: List<ReservationOutbound> =
                    reservations.map(ReservationOutbound::fillWithLink)
                CollectionModel(reservationsWithLink, linkTo<ReservationController>().withSelfRel())
            },
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) }
        )


    @GetMapping
    fun getAll(): CollectionModel<ReservationOutbound> {
        val allReservations: List<ReservationOutbound> = service.getAll().fold(
            ifSuccess = { it.map(ReservationOutbound::fillWithLink) },
            ifNotFound = { emptyList() }
        ) { throw ResponseStatusException(HttpStatus.FORBIDDEN) }
        return CollectionModel(allReservations, linkTo<ReservationController>().withSelfRel())
    }
}

fun Date.toLocalDate(): LocalDate = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()

fun ReservationOutbound.fillWithLink(): ReservationOutbound {
    val reservationLink: Link = linkTo<ReservationController>().slash(id).withSelfRel()
    val customerLink: Link = linkTo<CustomerController>().slash(customer.id).withSelfRel()
    customer.add(customerLink)
    add(reservationLink)
    return this
}
