package com.generals.zimmerfrei.server.service

import com.generals.zimmerfrei.server.database.ReservationEntity
import com.generals.zimmerfrei.server.database.ReservationRepository
import com.generals.zimmerfrei.server.database.RoomEntity
import com.generals.zimmerfrei.server.database.RoomRepository
import com.generals.zimmerfrei.server.outbound.ReservationOutbound
import com.generals.zimmerfrei.server.outbound.toEntity
import com.generals.zimmerfrei.server.outbound.toOutbound
import org.springframework.stereotype.Service
import org.threeten.bp.LocalDate

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
        reservationRepository.save(reservation.toEntity())
    }

    override fun update(id: Int, updated: ReservationOutbound): Result<ReservationOutbound> =
        reservationRepository.findById(id).map<Result<ReservationOutbound>> { reservation: ReservationEntity ->
            reservation.copy(
                name = updated.name,
                startDate = updated.startDate,
                endDate = updated.endDate
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

    fun <R> fold(ifSuccess: (T) -> R, orElse: () -> R): R =
        when (this) {
            is Success -> ifSuccess(value)
            NotFound -> orElse()
        }
}