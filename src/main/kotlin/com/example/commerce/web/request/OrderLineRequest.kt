package com.example.commerce.web.request

import jakarta.validation.constraints.NotNull

data class OrderLineRequest(
    @field:NotNull
    var productId: Long,
    @field:NotNull
    var amount: Long
)