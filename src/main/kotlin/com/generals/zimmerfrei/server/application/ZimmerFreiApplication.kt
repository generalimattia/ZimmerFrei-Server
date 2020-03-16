package com.generals.zimmerfrei.server.application

import com.fasterxml.jackson.datatype.threetenbp.ThreeTenModule
import com.generals.zimmerfrei.server.database.ReservationEntity
import com.generals.zimmerfrei.server.database.ReservationRepository
import com.generals.zimmerfrei.server.database.RoomEntity
import com.generals.zimmerfrei.server.database.RoomRepository
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.ComponentScan
import org.springframework.data.jpa.repository.config.EnableJpaRepositories
import org.threeten.bp.LocalDate

@EntityScan(
    basePackages = [
        "com.generals.zimmerfrei.server.database",
        "org.springframework.data.jpa.convert.threetenbp"
    ]
)
@EnableJpaRepositories(basePackages = ["com.generals.zimmerfrei.server.database"])
@SpringBootApplication(
    scanBasePackages = [
        "com.generals.zimmerfrei.server.database",
        "com.generals.zimmerfrei.server.controller",
        "com.generals.zimmerfrei.server.service",
        "com.generals.zimmerfrei.server.application"
    ]
)
open class ZimmerFreiApplication {

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(ZimmerFreiApplication::class.java, *args)
        }
    }

    @Bean
    fun demo(
        roomRepository: RoomRepository,
        reservationRepository: ReservationRepository
    ): CommandLineRunner =
        CommandLineRunner {
            roomRepository.save(RoomEntity(name = "B", roomCount = 2))
            roomRepository.save(RoomEntity(name = "A", roomCount = 3))
            roomRepository.save(RoomEntity(name = "C", roomCount = 4))

            val rooms: List<RoomEntity> = roomRepository.findAll().toList()

            reservationRepository.save(
                ReservationEntity(
                    name = "Test1",
                    startDate = LocalDate.now(),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms.first())
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test2",
                    startDate = LocalDate.now(),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms.first(), rooms[1])
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test3",
                    startDate = LocalDate.now().plusDays(5),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[1], rooms.last())
                )
            )
        }
}