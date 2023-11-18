package com.bizanaliza.restapireactive.repository

import com.bizanaliza.restapireactive.model.Company
import kotlinx.coroutines.flow.Flow
import org.springframework.data.repository.kotlin.CoroutineCrudRepository

interface CompanyRepository: CoroutineCrudRepository<Company, Long> {

    fun findByNameContaining(name: String): Flow<Company>
}