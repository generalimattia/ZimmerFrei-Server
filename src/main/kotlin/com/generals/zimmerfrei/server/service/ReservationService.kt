package com.generals.zimmerfrei.server.service

import com.generals.zimmerfrei.server.database.ReservationEntity
import com.generals.zimmerfrei.server.database.ReservationRepository
import com.generals.zimmerfrei.server.database.RoomEntity
import com.generals.zimmerfrei.server.database.RoomRepository
import com.generals.zimmerfrei.server.outbound.ReservationOutbound
import com.generals.zimmerfrei.server.outbound.RoomOutbound
import com.generals.zimmerfrei.server.outbound.toEntity
import com.generals.zimmerfrei.server.outbound.toOutbound
import org.springframework.stereotype.Service
import org.threeten.bp.LocalDate
import java.util.*

interface ReservationService {
    fun get(id: Int): Result<ReservationOutbound>
    fun save(reservation: ReservationOutbound)
    fun update(id: Int, updated: ReservationOutbound): Result<ReservationOutbound>
    fun delete(id: Int): Result<ReservationOutbound>
    fun getAll(): Result<List<ReservationOutbound>>
    fun getByRoomAndFromDateToDate(roomId: Int, from: LocalDate, to: LocalDate): Result<List<ReservationOutbound>>
}

@Service
class ReservationServiceImpl constructor(
    private val reservationRepository: ReservationRepository,
    private val roomRepository: RoomRepository
) : ReservationService {

    override fun get(id: Int): Result<ReservationOutbound> =
        reservationRepository.findById(id).map<Result<ReservationOutbound>> { Result.Success(it.toOutbound()) }.orElse(
            Result.NotFound
        )

    override fun save(reservation: ReservationOutbound) {
        val persistentRoom: RoomEntity? =
            reservation.room?.let { room: RoomOutbound ->
                val optional: Optional<RoomEntity> = roomRepository.findById(room.id)
                optional.orElse(null)
            }
        val toPersist: ReservationEntity = reservation.toEntity()
        val rooms: List<RoomEntity> = persistentRoom?.let {
            listOf(it)
        } ?: emptyList()

        reservationRepository.save(
            toPersist.copy(
                rooms = toPersist.rooms + rooms
            )
        )
    }

    override fun update(id: Int, updated: ReservationOutbound): Result<ReservationOutbound> =
        reservationRepository.findById(id).map<Result<ReservationOutbound>> { reservation: ReservationEntity ->
            reservation.copy(
                name = updated.name,
                startDate = updated.startDate,
                endDate = updated.endDate,
                persons = updated.persons,
                adults = updated.adults,
                children = updated.children,
                babies = updated.babies,
                notes = updated.notes,
                color = updated.color
            ).also { reservationRepository.save(it) }
                .let { Result.Success(it.toOutbound()) }
        }.orElse(Result.NotFound)

    override fun delete(id: Int): Result<ReservationOutbound> =
        reservationRepository.findById(id).map<Result<ReservationOutbound>> {
            reservationRepository.delete(it)
            Result.Success(it.toOutbound())
        }.orElse(Result.NotFound)

    override fun getAll(): Result<List<ReservationOutbound>> =
        Result.Success(reservationRepository.findAll().map(ReservationEntity::toOutbound))

    override fun getByRoomAndFromDateToDate(
        roomId: Int,
        from: LocalDate,
        to: LocalDate
    ): Result<List<ReservationOutbound>> =
        roomRepository.findById(roomId).map<Result<List<ReservationOutbound>>> { room: RoomEntity ->
            Result.Success(
                reservationRepository.findByRoomAndStartDateBetween(
                    room,
                    from,
                    to
                ).map(ReservationEntity::toOutbound)
            )
        }.orElse(Result.NotFound)
}

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    object NotFound : Result<Nothing>()
    object Forbidden : Result<Nothing>()

    fun <R> fold(ifSuccess: (T) -> R, ifNotFound: () -> R, ifForbidden: () -> R): R =
        when (this) {
            is Success -> ifSuccess(value)
            NotFound -> ifNotFound()
            Forbidden -> ifForbidden()
        }
}