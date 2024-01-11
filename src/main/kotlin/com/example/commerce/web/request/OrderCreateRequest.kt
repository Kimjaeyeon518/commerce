package com.example.commerce.web.request

import com.example.commerce.domain.entity.valueobject.AddressInfo
import jakarta.validation.constraints.NotEmpty
import jakarta.validation.constraints.NotNull

data class OrderCreateRequest(
    @field:NotEmpty
    var orderLines: List<OrderLineRequest>,
    @field:NotNull
    var totalPrice: Long,
    var address1: String,
    var address2: String,
    var zipCode: String,
) {
    fun getAddress(): AddressInfo {
        return AddressInfo(address1, address2, zipCode)
    }
}