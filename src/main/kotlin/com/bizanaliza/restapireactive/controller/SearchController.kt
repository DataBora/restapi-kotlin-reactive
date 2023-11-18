package com.bizanaliza.restapireactive.controller

import com.bizanaliza.restapireactive.dto.IdNameTypeResponse
import com.bizanaliza.restapireactive.dto.ResultType
import com.bizanaliza.restapireactive.model.Company
import com.bizanaliza.restapireactive.model.User
import com.bizanaliza.restapireactive.service.CompanyService
import com.bizanaliza.restapireactive.service.UserService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.merge
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/search")
class SearchController(
    private val userService: UserService,
    private val companyService: CompanyService
) {

    @GetMapping
    suspend fun searchByName(@RequestParam("query") query: String): Flow<IdNameTypeResponse> {
        val usersFlow = userService.findByNameLike(query)
            .map(User::toIdNameTypeResponse)
        val companyFlow = companyService.findAllCompaniesByNameLike(query)
            .map(Company::toIdNameTypeResponse)

        return merge(usersFlow, companyFlow)
    }
}


private fun User.toIdNameTypeResponse(): IdNameTypeResponse =
    IdNameTypeResponse(
        id = this.id!!,
        name = this.name,
        type = ResultType.USER
    )

private fun Company.toIdNameTypeResponse(): IdNameTypeResponse =
    IdNameTypeResponse(
        id = this.id!!,
        name = this.name,
        type = ResultType.COMPANY
    )