package com.generals.zimmerfrei.server.service

import com.generals.zimmerfrei.server.database.ReservationEntity
import com.generals.zimmerfrei.server.database.ReservationRepository
import com.generals.zimmerfrei.server.database.RoomEntity
import com.generals.zimmerfrei.server.outbound.ReservationOutbound
import com.generals.zimmerfrei.server.outbound.RoomOutbound
import org.springframework.stereotype.Service

interface ReservationService {
    fun get(id: Int): Result<ReservationOutbound>
    fun save(reservation: ReservationOutbound)
    fun update(id: Int, updated: ReservationOutbound): Result<ReservationOutbound>
    fun delete(id: Int): Result<ReservationOutbound>
    fun getAll(): Result<List<ReservationOutbound>>
}

@Service
class ReservationServiceImpl constructor(
    private val repository: ReservationRepository
) : ReservationService {

    override fun get(id: Int): Result<ReservationOutbound> =
        repository.findById(id).map<Result<ReservationOutbound>> { Result.Success(it.toOutbound()) }.orElse(Result.NotFound)

    override fun save(reservation: ReservationOutbound) {
        repository.save(reservation.toEntity())
    }

    override fun update(id: Int, updated: ReservationOutbound): Result<ReservationOutbound> =
        repository.findById(id).map<Result<ReservationOutbound>> { reservation: ReservationEntity ->
            reservation.copy(
                name = updated.name,
                startDate = updated.startDate,
                endDate = updated.endDate
            ).also { repository.save(it) }
                .let { Result.Success(it.toOutbound()) }
        }.orElse(Result.NotFound)

    override fun delete(id: Int): Result<ReservationOutbound> =
        repository.findById(id).map<Result<ReservationOutbound>> {
            repository.delete(it)
            Result.Success(it.toOutbound())
        }.orElse(Result.NotFound)

    override fun getAll(): Result<List<ReservationOutbound>> =
        Result.Success(repository.findAll().map(ReservationEntity::toOutbound))
}

private fun RoomOutbound.toEntity(): RoomEntity = RoomEntity(id = id, name = name, roomCount = roomCount)
private fun RoomEntity.toOutbound(): RoomOutbound = RoomOutbound(id = id, name = name, roomCount = roomCount)

private fun ReservationOutbound.toEntity(): ReservationEntity =
    ReservationEntity(id = id, name = name, startDate = startDate, endDate = endDate)

private fun ReservationEntity.toOutbound(): ReservationOutbound =
    ReservationOutbound(id = id, name = name, startDate = startDate, endDate = endDate)

sealed class Result<out T> {
    data class Success<out T>(val value: T) : Result<T>()
    object NotFound : Result<Nothing>()

    fun <R> fold(ifSuccess: (T) -> R, orElse: () -> R): R =
        when (this) {
            is Success -> ifSuccess(value)
            NotFound -> orElse()
        }
}