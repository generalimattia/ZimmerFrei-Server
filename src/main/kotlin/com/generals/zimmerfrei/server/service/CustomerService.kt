package com.generals.zimmerfrei.server.service

import com.generals.zimmerfrei.server.database.CustomerEntity
import com.generals.zimmerfrei.server.database.CustomerRepository
import com.generals.zimmerfrei.server.outbound.CustomerOutbound
import com.generals.zimmerfrei.server.outbound.toEntity
import com.generals.zimmerfrei.server.outbound.toOutbound
import org.springframework.stereotype.Service

interface CustomerService {
    fun get(id: Int): Result<CustomerOutbound>
    fun save(room: CustomerOutbound)
    fun update(id: Int, updated: CustomerOutbound): Result<CustomerOutbound>
    fun delete(id: Int): Result<CustomerOutbound>
    fun getAll(): Result<List<CustomerOutbound>>
}

@Service
class CustomerServiceImpl constructor(
    private val repository: CustomerRepository
) : CustomerService {

    override fun get(id: Int): Result<CustomerOutbound> =
        repository.findById(id).map<Result<CustomerOutbound>> { Result.Success(it.toOutbound()) }
            .orElse(Result.NotFound)

    override fun save(room: CustomerOutbound) {
        repository.save(room.toEntity())
    }

    override fun update(id: Int, updated: CustomerOutbound): Result<CustomerOutbound> =
        repository.findById(id).map<Result<CustomerOutbound>> { customer: CustomerEntity ->
            customer.copy(
                firstName = updated.firstName,
                lastName = updated.lastName,
                socialId = updated.socialId,
                mobile = updated.mobile,
                email = updated.email,
                birthDate = updated.birthDate,
                address = updated.address
            ).also { repository.save(it) }
                .let { Result.Success(it.toOutbound()) }
        }.orElse(Result.NotFound)

    override fun delete(id: Int): Result<CustomerOutbound> =
        repository.findById(id).map<Result<CustomerOutbound>> {
            repository.delete(it)
            Result.Success(it.toOutbound())
        }.orElse(Result.NotFound)

    override fun getAll(): Result<List<CustomerOutbound>> =
        Result.Success(repository.findAll().map(CustomerEntity::toOutbound))
}