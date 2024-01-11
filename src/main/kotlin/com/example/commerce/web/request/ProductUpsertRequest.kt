package com.example.commerce.web.request

import com.example.commerce.domain.enums.Category
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.NotNull

data class ProductUpsertRequest(
    val productId: Long?,

    @field:NotBlank
    val name: String,

    @field:NotNull
    val storeId: Long,

    @field:NotNull
    val category: Category,

    var description: String,

    @field:NotNull
    val price: Long,

    @field:NotNull
    val amount: Long
)