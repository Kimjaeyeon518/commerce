package com.example.commerce.api

import com.example.commerce.domain.entity.Order
import com.example.commerce.service.OrderService
import com.example.commerce.web.request.OrderCreateRequest
import com.example.commerce.web.response.SingleResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class OrderApi(
    val orderService: OrderService
) {

    @PostMapping("/orders")
    fun create(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: OrderCreateRequest
    ): ResponseEntity<SingleResponse<Order>> {
        val order = orderService.create(user.username.toLong(), request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("주문 생성 완료 !", order))
    }

    @PostMapping("/orders/{orderId}/buy")
    fun buy(
        @AuthenticationPrincipal user: User,
        @PathVariable orderId: Long
    ): ResponseEntity<SingleResponse<Order>> {
        val order = orderService.buy(user.username.toLong(), orderId)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("주문 완료 !", order))
    }
}