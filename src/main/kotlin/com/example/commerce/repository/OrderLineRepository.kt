package com.example.commerce.repository

import com.example.commerce.domain.entity.Order
import com.example.commerce.domain.entity.OrderLine
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface OrderLineRepository: JpaRepository<OrderLine, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select ol from OrderLine ol where ol.order = :order")
    fun findByOrderIdWithPessimisticLock(order: Order): List<OrderLine>

}