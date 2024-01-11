package com.example.commerce.unittest.entity

import com.example.commerce.domain.entity.Member
import com.example.commerce.domain.entity.Product
import com.example.commerce.domain.entity.Store
import com.example.commerce.domain.enums.Category
import com.example.commerce.domain.enums.MemberRole
import com.example.commerce.exception.BadRequestException
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class ProductUnitTest {

    @Test
    fun 주문시_상품_재고_감소() {
        val member = Member("jae518@naver.com", "password", "재연", MemberRole.ADMIN)
        val store = Store(member)
        val product = Product(1, store, "상품1", Category.TOP, "", 1000, 100)

        product.purchased(10L)

        assertTrue(product.amount == 90L)
    }

    @Test
    fun 상품_재고보다_큰_차감() {
        val member = Member("jae518@naver.com", "password", "재연", MemberRole.ADMIN)
        val store = Store(member)
        val product = Product(1, store, "상품1", Category.TOP, "", 1000, 100)

        assertThrows<BadRequestException>(message = "상품 재고가 부족합니다.") {
            product.purchased(1000L)
        }
    }

}