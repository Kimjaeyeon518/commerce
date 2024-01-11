package com.example.commerce.repository

import com.example.commerce.domain.entity.Store
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface StoreRepository: JpaRepository<Store, Long> {
}