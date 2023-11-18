package com.bizanaliza.restapireactive.service

import com.bizanaliza.restapireactive.model.Company
import com.bizanaliza.restapireactive.repository.CompanyRepository
import kotlinx.coroutines.flow.Flow
import org.springframework.http.HttpStatus
import org.springframework.stereotype.Service
import org.springframework.web.server.ResponseStatusException

@Service
class CompanyService( private val companyRepository: CompanyRepository) {


    suspend fun saveCompany(company: Company): Company? =
        companyRepository.save(company)

    suspend fun findAll(): Flow<Company> =
        companyRepository.findAll()

    suspend fun findById(id: Long): Company? =
        companyRepository.findById(id)

    suspend fun findAllCompaniesByNameLike(name: String): Flow<Company> =
        companyRepository.findByNameContaining(name)

    suspend fun deleteCompanyById(id: Long){
        val foundCompany = companyRepository.findById(id)

        if(foundCompany == null){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found.")
        } else {
            companyRepository.deleteById(id)
        }
    }

    suspend fun updateCompanyById(id: Long, requestedCompany: Company): Company {
        val foundCompany = companyRepository.findById(id)

        return  if(foundCompany == null){
            throw ResponseStatusException(HttpStatus.NOT_FOUND, "Company not found.")
        } else {
            companyRepository.save(
                requestedCompany.copy(id = foundCompany.id)
            )
        }
    }


}