package com.example.commerce.domain.enums

enum class OrderStatus(val value: String) {
    WAITING_PAY("결제대기"),
    COMPLETE("주문완료"),
    CONFIRMED("주문확정"),
    CANCEL("주문취소"),
}