package com.bizanaliza.restapireactive.service

import com.bizanaliza.restapireactive.model.User
import com.bizanaliza.restapireactive.repository.UserRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.firstOrNull
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class UserService(private val userRepository: UserRepository) {

    suspend fun saveUser(user: User): User? =
        userRepository.byEmail(user.email)
            .firstOrNull()
            ?.let { throw ResponseStatusException(HttpStatus.BAD_REQUEST, "User already exists!") }
            ?: userRepository.save(user)

    suspend fun findAllUsers(): Flow<User> =
        userRepository.findAll()

    suspend fun findById(id: Long): User? =
        userRepository.findById(id)

    suspend fun deleteById(id: Long) {
        val foundUser = userRepository.findById(id)

        if (foundUser == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "User doesn't exists.")
        } else {
            userRepository.deleteById(id)
        }
    }

    suspend fun updateById(id: Long, requestedUser: User): User {
        val foundUser = userRepository.findById(id)

        return if (foundUser == null) {
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Can't updated user that doesn't exists.")
        } else userRepository.save(requestedUser.copy(id = foundUser.id))
    }

    suspend fun findByCompanyId(companyId: Long): Flow<User> =
        userRepository.findByCompanyId(companyId)

    suspend fun findByNameLike(name: String): Flow<User> =
        userRepository.findByNameContaining(name)
}
