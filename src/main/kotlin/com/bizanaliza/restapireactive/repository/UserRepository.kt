package com.bizanaliza.restapireactive.repository

import com.bizanaliza.restapireactive.model.User
import kotlinx.coroutines.flow.Flow
import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface UserRepository: CoroutineCrudRepository<User, Long> {

    fun findByNameContaining(name: String): Flow<User>
    fun findByCompanyId(companyId: Long): Flow<User>

    @Query("SELECT * FROM app_user WHERE email = :email")
    fun byEmail(email: String): Flow<User>



}