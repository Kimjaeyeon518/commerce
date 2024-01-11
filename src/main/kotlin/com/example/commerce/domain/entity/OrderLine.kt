package com.example.commerce.domain.entity

import jakarta.persistence.*

@Entity
@Table(name = "order_line")
class OrderLine(order: Order, productId: Long, amount: Long) {

    init {
        order.addOrderline(this)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderLineId: Long? = null

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "order_id", nullable = false)
    var order: Order = order

    @Column(name = "product_id")
    var productId = productId

    @Column(name = "amount")
    var amount = amount
}