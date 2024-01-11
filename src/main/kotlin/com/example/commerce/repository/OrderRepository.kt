package com.example.commerce.repository

import com.example.commerce.domain.entity.Order
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository
import java.time.LocalDateTime

@Repository
interface OrderRepository: JpaRepository<Order, Long> {

    fun findByCreatedAtIsAfter(date: LocalDateTime): List<Order>

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select o from Order o where o.orderId = :orderId")
    fun findByIdWithPessimisticLock(orderId: Long): Order?
}