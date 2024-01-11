package com.example.commerce.repository

import com.example.commerce.domain.entity.Product
import com.example.commerce.domain.enums.Category
import jakarta.persistence.LockModeType
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Lock
import org.springframework.data.jpa.repository.Query
import org.springframework.stereotype.Repository

@Repository
interface ProductRepository: JpaRepository<Product, Long> {

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.productId in :ids")
    fun findByIdInWithPessimisticLock(ids: List<Long>): List<Product>

    @Lock(value = LockModeType.PESSIMISTIC_WRITE)
    @Query("select p from Product p where p.productId = :id")
    fun findByIdWithPessimisticLock(id: Long): Product?

    fun findByCategory(category: Category): List<Product>

    fun findTop3ByOrderBySaleCount(): List<Product>

    fun findByProductIdIn(ids: List<Long>): List<Product>
}