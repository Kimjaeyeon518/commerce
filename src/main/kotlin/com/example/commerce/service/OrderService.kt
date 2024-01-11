package com.example.commerce.service

import com.example.commerce.domain.entity.Member
import com.example.commerce.domain.entity.Order
import com.example.commerce.domain.entity.OrderLine
import com.example.commerce.exception.BadRequestException
import com.example.commerce.exception.NotFoundException
import com.example.commerce.repository.OrderLineRepository
import com.example.commerce.repository.OrderRepository
import com.example.commerce.repository.ProductRepository
import com.example.commerce.repository.MemberRepository
import com.example.commerce.web.request.OrderCreateRequest
import com.example.commerce.web.request.OrderLineRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class OrderService(
    val memberRepository: MemberRepository,
    val productRepository: ProductRepository,
    val orderRepository: OrderRepository,
    val orderLineRepository: OrderLineRepository
) {

    @Transactional
    fun create(memberId: Long, request: OrderCreateRequest): Order {
        val order = orderRepository.save(Order(memberId, request.getAddress(), request.totalPrice))
        orderLineRepository.saveAll(
            request.orderLines
                .map { OrderLine(order, it.productId, it.amount) }
                .toList()
        )

        return order
    }

    @Transactional
    fun buy(memberId: Long, orderId: Long): Order {
        val order = orderRepository.findById(orderId)
            .orElseThrow{ NotFoundException("주문을 찾을 수 없습니다.") }
        val member = memberRepository.findById(memberId)
            .orElseThrow{ throw NotFoundException("사용자를 찾을 수 없습니다.") }

        completeOrder(order, member)

        return order
    }

    fun completeOrder(
        order: Order,
        member: Member
    ) {
        order.toComplete()
        val productIds = order.orderLines.map { it.productId }.distinct().toList()
        val products = productRepository.findByIdInWithPessimisticLock(productIds)
        for (p in products) {
            for (ol in order.orderLines) {
                if (p.productId == ol.productId) {
                    p.purchased(ol.amount)
                    member.deductPoint(p.price * ol.amount)
                }
            }
        }
    }

    fun buildOrderLines(
        order: Order,
        orderLines: List<OrderLineRequest>
    ): List<OrderLine> {
        val productIds = orderLines.map { it.productId }.distinct().toList()
        val products = productRepository.findByProductIdIn(productIds)

        if (products.size != orderLines.size) {
            throw BadRequestException("유효하지 않은 상품이 존재합니다.")
        }
        val orderLineList = mutableListOf<OrderLine>()
        for (product in products) {
            for (orderLine in orderLines) {
                if (product.productId == orderLine.productId) {
                    orderLineList.add(OrderLine(order, product.productId!!, orderLine.amount))
                }
            }
        }
        return orderLineList
    }

}