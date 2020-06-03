package com.generals.zimmerfrei.server.controller

import com.generals.zimmerfrei.server.outbound.ReservationOutbound
import com.generals.zimmerfrei.server.service.ReservationService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.Link
import org.springframework.hateoas.server.core.DummyInvocationUtils.methodOn
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
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
    fun save(@RequestBody reservation: ReservationOutbound): ReservationOutbound =
        service.save(reservation).fold(
            ifSuccess = { outbound: ReservationOutbound ->
                val link: Link = linkTo<ReservationController>().slash(outbound.id).withSelfRel()
                outbound.apply {
                    add(link)
                }
            },
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) },
            ifConflict = { throw ResponseStatusException(HttpStatus.CONFLICT) }
        )

    @PutMapping("/{id}")
    fun update(@PathVariable id: Int, @RequestBody updated: ReservationOutbound): ReservationOutbound =
        service.update(id, updated).fold(
            ifSuccess = { outbound: ReservationOutbound ->
                val link: Link = linkTo<ReservationController>().slash(outbound.id).withSelfRel()
                outbound.apply {
                    add(link)
                }
            },
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) },
            ifConflict = { throw ResponseStatusException(HttpStatus.CONFLICT) }
        )

    @DeleteMapping("/{id}")
    fun delete(@PathVariable id: Int) =
        service.delete(id).fold(
            ifSuccess = { },
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) },
            ifConflict = { throw ResponseStatusException(HttpStatus.CONFLICT) }
        )

    @GetMapping("/{id}")
    fun get(@PathVariable id: Int): ReservationOutbound =
        service.get(id).fold(
            ifSuccess = ReservationOutbound::fillWithLink,
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) },
            ifConflict = { throw ResponseStatusException(HttpStatus.CONFLICT) }
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
                CollectionModel(
                    reservationsWithLink, linkTo(
                        methodOn(ReservationController::class.java)
                            .getByRoomAndFromDateToDate(roomId, from, to)
                    ).withSelfRel()
                )
            },
            ifNotFound = { throw ResponseStatusException(HttpStatus.NOT_FOUND) },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) },
            ifConflict = { throw ResponseStatusException(HttpStatus.CONFLICT) }
        )

    @GetMapping
    fun getAll(): CollectionModel<ReservationOutbound> {
        val allReservations: List<ReservationOutbound> = service.getAll().fold(
            ifSuccess = { it.map(ReservationOutbound::fillWithLink) },
            ifNotFound = { emptyList() },
            ifForbidden = { throw ResponseStatusException(HttpStatus.FORBIDDEN) },
            ifConflict = { throw ResponseStatusException(HttpStatus.CONFLICT) }
        )
        return CollectionModel(allReservations, linkTo<ReservationController>().withSelfRel())
    }
}

fun Date.toLocalDate(): LocalDate = Instant.ofEpochMilli(time).atZone(ZoneId.systemDefault()).toLocalDate()

fun ReservationOutbound.fillWithLink(): ReservationOutbound {
    val reservationLink: Link = linkTo<ReservationController>().slash(id).withSelfRel()
    customer?.apply {
        val customerLink: Link = linkTo<CustomerController>().slash(id).withSelfRel()
        add(customerLink)
    }
    add(reservationLink)
    return this
}
