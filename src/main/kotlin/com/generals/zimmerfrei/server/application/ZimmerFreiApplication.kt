package com.generals.zimmerfrei.server.application

import com.generals.zimmerfrei.server.database.*
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.domain.EntityScan
import org.springframework.context.annotation.Bean
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
        reservationRepository: ReservationRepository,
        customerRepository: CustomerRepository
    ): CommandLineRunner =
        CommandLineRunner {
            roomRepository.save(RoomEntity(name = "A", maxPersons = 2))
            roomRepository.save(RoomEntity(name = "B", maxPersons = 3))
            roomRepository.save(RoomEntity(name = "C", maxPersons = 4))
            roomRepository.save(RoomEntity(name = "D", maxPersons = 4))
            roomRepository.save(RoomEntity(name = "E", maxPersons = 4))
            roomRepository.save(RoomEntity(name = "F", maxPersons = 4))
            roomRepository.save(RoomEntity(name = "G", maxPersons = 4))
            roomRepository.save(RoomEntity(name = "H", maxPersons = 4))
            roomRepository.save(RoomEntity(name = "I", maxPersons = 4))
            roomRepository.save(RoomEntity(name = "L", maxPersons = 4))
            roomRepository.save(RoomEntity(name = "M", maxPersons = 4))
            roomRepository.save(RoomEntity(name = "N", maxPersons = 4))

            val rooms: List<RoomEntity> = roomRepository.findAll().toList()

            val customer1: CustomerEntity = customerRepository.save(
                CustomerEntity(
                    firstName = "Jhon",
                    lastName = "Black",
                    socialId = "12345566",
                    mobile = "234543366",
                    email = "jhon@black.com",
                    address = "Black Street, 1",
                    birthDate = LocalDate.now().minusYears(50),
                    city = "London",
                    province = "London",
                    state = "GB",
                    zip = "23131",
                    gender = "M",
                    birthPlace = "Birmingham"
                )
            )

            val customer2: CustomerEntity = customerRepository.save(
                CustomerEntity(
                    firstName = "Mark",
                    lastName = "Blue",
                    socialId = "12345434556456",
                    mobile = "234543366",
                    email = "mark@blue.com",
                    address = "Blue Street, 1",
                    birthDate = LocalDate.now().minusYears(30),
                    city = "London",
                    province = "London",
                    state = "GB",
                    zip = "23131",
                    gender = "M",
                    birthPlace = "Birmingham"
                )
            )

            val customer3: CustomerEntity = customerRepository.save(
                CustomerEntity(
                    firstName = "Fitz",
                    lastName = "Yellow",
                    socialId = "36842790",
                    mobile = "234543366",
                    email = "fitz@yellow.com",
                    address = "Yellow Street, 1",
                    birthDate = LocalDate.now().minusYears(70),
                    city = "London",
                    province = "London",
                    state = "GB",
                    zip = "23131",
                    gender = "M",
                    birthPlace = "Birmingham"
                )
            )

            reservationRepository.save(
                ReservationEntity(
                    name = "Test1",
                    startDate = LocalDate.now(),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms.first()),
                    color = "#d50000",
                    notes = "Please clean everything",
                    persons = 3,
                    adults = 2,
                    children = 1,
                    babies = 0,
                    customer = customer1
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test2",
                    startDate = LocalDate.now().minusDays(10),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[1]),
                    color = "#c51162",
                    notes = "Please clean everything",
                    persons = 3,
                    adults = 2,
                    children = 1,
                    babies = 0,
                    customer = customer2
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test3",
                    startDate = LocalDate.now().minusDays(5),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[2]),
                    color = "#8e24aa",
                    notes = "Please clean everything",
                    persons = 3,
                    adults = 2,
                    children = 1,
                    babies = 0,
                    customer = customer3
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test4",
                    startDate = LocalDate.now(),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[3]),
                    color = "#6200ea",
                    notes = "Please clean everything",
                    persons = 3,
                    adults = 2,
                    children = 1,
                    babies = 0,
                    customer = customer1
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test5",
                    startDate = LocalDate.now().minusDays(10),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[4]),
                    color = "#283593",
                    notes = "Please clean everything",
                    persons = 3,
                    adults = 2,
                    children = 1,
                    babies = 0,
                    customer = customer2
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test6",
                    startDate = LocalDate.now().minusDays(5),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[5]),
                    color = "#2962ff",
                    notes = "Please clean everything",
                    persons = 3,
                    adults = 2,
                    children = 1,
                    babies = 0,
                    customer = customer3
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test7",
                    startDate = LocalDate.now().minusDays(5),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[6]),
                    color = "#0091ea",
                    notes = "Please clean everything",
                    persons = 3,
                    adults = 2,
                    children = 1,
                    babies = 0,
                    customer = customer3
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test8",
                    startDate = LocalDate.now(),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[7]),
                    color = "#00b8d4",
                    notes = "Please clean everything",
                    persons = 3,
                    adults = 2,
                    children = 1,
                    babies = 0,
                    customer = customer1
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test9",
                    startDate = LocalDate.now().minusDays(10),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[8]),
                    color = "#00695c",
                    notes = "Please clean everything",
                    persons = 3,
                    adults = 2,
                    children = 1,
                    babies = 0,
                    customer = customer2
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test10",
                    startDate = LocalDate.now().minusDays(5),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[9]),
                    color = "#4caf50",
                    notes = "Please clean everything",
                    persons = 3,
                    adults = 2,
                    children = 1,
                    babies = 0,
                    customer = customer3
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test9",
                    startDate = LocalDate.now().minusDays(10),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[10]),
                    color = "#8bc34a",
                    notes = "Please clean everything",
                    persons = 3,
                    adults = 2,
                    children = 1,
                    babies = 0,
                    customer = customer2
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test10",
                    startDate = LocalDate.now().minusDays(5),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms.last()),
                    color = "#fbc02d",
                    notes = "Please clean everything",
                    persons = 3,
                    adults = 2,
                    children = 1,
                    babies = 0,
                    customer = customer3
                )
            )
        }
}