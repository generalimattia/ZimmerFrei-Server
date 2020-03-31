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
            roomRepository.save(RoomEntity(name = "A", roomCount = 2))
            roomRepository.save(RoomEntity(name = "B", roomCount = 3))
            roomRepository.save(RoomEntity(name = "C", roomCount = 4))
            roomRepository.save(RoomEntity(name = "D", roomCount = 4))
            roomRepository.save(RoomEntity(name = "E", roomCount = 4))
            roomRepository.save(RoomEntity(name = "F", roomCount = 4))
            roomRepository.save(RoomEntity(name = "G", roomCount = 4))
            roomRepository.save(RoomEntity(name = "H", roomCount = 4))
            roomRepository.save(RoomEntity(name = "I", roomCount = 4))
            roomRepository.save(RoomEntity(name = "L", roomCount = 4))
            roomRepository.save(RoomEntity(name = "M", roomCount = 4))
            roomRepository.save(RoomEntity(name = "N", roomCount = 4))

            val rooms: List<RoomEntity> = roomRepository.findAll().toList()

            reservationRepository.save(
                ReservationEntity(
                    name = "Test1",
                    startDate = LocalDate.now(),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms.first()),
                    numberOfParticipants = 3,
                    customer = CustomerEntity(
                        firstName = "Jhon",
                        lastName = "Black",
                        socialId = "12345566",
                        mobile = "234543366",
                        email = "jhon@black.com",
                        address = "Black Street, 1",
                        birthDate = LocalDate.now().minusYears(50)
                    )
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test2",
                    startDate = LocalDate.now().minusDays(10),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[1]),
                    numberOfParticipants = 4,
                    customer = CustomerEntity(
                        firstName = "Mark",
                        lastName = "Blue",
                        socialId = "12345434556456",
                        mobile = "234543366",
                        email = "mark@blue.com",
                        address = "Blue Street, 1",
                        birthDate = LocalDate.now().minusYears(30)
                    )
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test3",
                    startDate = LocalDate.now().minusDays(5),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[2]),
                    numberOfParticipants = 7,
                    customer = CustomerEntity(
                        firstName = "Fitz",
                        lastName = "Yellow",
                        socialId = "36842790",
                        mobile = "234543366",
                        email = "fitz@yellow.com",
                        address = "Yellow Street, 1",
                        birthDate = LocalDate.now().minusYears(70)
                    )
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test4",
                    startDate = LocalDate.now(),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[3]),
                    numberOfParticipants = 3,
                    customer = CustomerEntity(
                        firstName = "Jhon",
                        lastName = "Black",
                        socialId = "28176319",
                        mobile = "234543366",
                        email = "jhon@black.com",
                        address = "Black Street, 1",
                        birthDate = LocalDate.now().minusYears(50)
                    )
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test5",
                    startDate = LocalDate.now().minusDays(10),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[4]),
                    numberOfParticipants = 4,
                    customer = CustomerEntity(
                        firstName = "Mark",
                        lastName = "Blue",
                        socialId = "4543238904",
                        mobile = "234543366",
                        email = "mark@blue.com",
                        address = "Blue Street, 1",
                        birthDate = LocalDate.now().minusYears(30)
                    )
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test6",
                    startDate = LocalDate.now().minusDays(5),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[5]),
                    numberOfParticipants = 7,
                    customer = CustomerEntity(
                        firstName = "Fitz",
                        lastName = "Yellow",
                        socialId = "324876232",
                        mobile = "234543366",
                        email = "fitz@yellow.com",
                        address = "Yellow Street, 1",
                        birthDate = LocalDate.now().minusYears(70)
                    )
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test7",
                    startDate = LocalDate.now().minusDays(5),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[6]),
                    numberOfParticipants = 7,
                    customer = CustomerEntity(
                        firstName = "Fitz",
                        lastName = "Yellow",
                        socialId = "2y492372389723",
                        mobile = "234543366",
                        email = "fitz@yellow.com",
                        address = "Yellow Street, 1",
                        birthDate = LocalDate.now().minusYears(70)
                    )
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test8",
                    startDate = LocalDate.now(),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[7]),
                    numberOfParticipants = 3,
                    customer = CustomerEntity(
                        firstName = "Jhon",
                        lastName = "Black",
                        socialId = "9823493287423",
                        mobile = "234543366",
                        email = "jhon@black.com",
                        address = "Black Street, 1",
                        birthDate = LocalDate.now().minusYears(50)
                    )
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test9",
                    startDate = LocalDate.now().minusDays(10),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[8]),
                    numberOfParticipants = 4,
                    customer = CustomerEntity(
                        firstName = "Mark",
                        lastName = "Blue",
                        socialId = "4023420948'",
                        mobile = "234543366",
                        email = "mark@blue.com",
                        address = "Blue Street, 1",
                        birthDate = LocalDate.now().minusYears(30)
                    )
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test10",
                    startDate = LocalDate.now().minusDays(5),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[9]),
                    numberOfParticipants = 7,
                    customer = CustomerEntity(
                        firstName = "Fitz",
                        lastName = "Yellow",
                        socialId = "298472",
                        mobile = "234543366",
                        email = "fitz@yellow.com",
                        address = "Yellow Street, 1",
                        birthDate = LocalDate.now().minusYears(70)
                    )
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test9",
                    startDate = LocalDate.now().minusDays(10),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms[10]),
                    numberOfParticipants = 4,
                    customer = CustomerEntity(
                        firstName = "Mark",
                        lastName = "Blue",
                        socialId = "324434645667'",
                        mobile = "234543366",
                        email = "mark@blue.com",
                        address = "Blue Street, 1",
                        birthDate = LocalDate.now().minusYears(30)
                    )
                )
            )
            reservationRepository.save(
                ReservationEntity(
                    name = "Test10",
                    startDate = LocalDate.now().minusDays(5),
                    endDate = LocalDate.now().plusDays(10),
                    rooms = listOf(rooms.last()),
                    numberOfParticipants = 7,
                    customer = CustomerEntity(
                        firstName = "Fitz",
                        lastName = "Yellow",
                        socialId = "3209843204823",
                        mobile = "234543366",
                        email = "fitz@yellow.com",
                        address = "Yellow Street, 1",
                        birthDate = LocalDate.now().minusYears(70)
                    )
                )
            )
        }
}