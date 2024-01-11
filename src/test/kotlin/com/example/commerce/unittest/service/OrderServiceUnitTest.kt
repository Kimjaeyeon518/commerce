package com.example.commerce.unittest.service

import com.example.commerce.util.EasyRandomPlugin
import com.example.commerce.domain.entity.Order
import com.example.commerce.exception.BadRequestException
import com.example.commerce.exception.NotFoundException
import com.example.commerce.repository.MemberRepository
import com.example.commerce.repository.OrderLineRepository
import com.example.commerce.repository.OrderRepository
import com.example.commerce.repository.ProductRepository
import com.example.commerce.service.OrderService
import com.example.commerce.web.request.OrderLineRequest
import io.mockk.every
import io.mockk.mockk
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class OrderServiceUnitTest {
    private val memberRepository: MemberRepository = mockk()
    private val productRepository: ProductRepository = mockk()
    private val orderRepository: OrderRepository = mockk()
    private val orderLineRepository: OrderLineRepository = mockk()
    private val orderService: OrderService = OrderService(memberRepository, productRepository, orderRepository, orderLineRepository)

    @Test
    fun 주문_생성() {
        val products = EasyRandomPlugin.getProductList()
        val order = Order(1, EasyRandomPlugin.getAddressInfo(), 10000)
        val orderLines = mutableListOf(
            OrderLineRequest(products[0].productId!!, 10),
            OrderLineRequest(products[1].productId!!, 20),
            OrderLineRequest(products[2].productId!!, 30),
            OrderLineRequest(products[3].productId!!, 40),
        )
        every { productRepository.findByProductIdIn(any()) } returns products

        val buildOrderLines = orderService.buildOrderLines(order, orderLines)

        assertTrue(buildOrderLines.size == 4)
        assertTrue(buildOrderLines[0].productId == products[0].productId)
        assertThat(buildOrderLines[0].amount).isEqualTo(10)
        assertTrue(buildOrderLines[1].productId == products[1].productId)
        assertThat(buildOrderLines[1].amount).isEqualTo(20)
        assertTrue(buildOrderLines[2].productId == products[2].productId)
        assertThat(buildOrderLines[2].amount).isEqualTo(30)
        assertTrue(buildOrderLines[3].productId == products[3].productId)
        assertThat(buildOrderLines[3].amount).isEqualTo(40)
    }

    @Test
    fun 오류_존재하지_않는_상품_주문_생성() {
        val products = EasyRandomPlugin.getProductList()
        val order = Order(1, EasyRandomPlugin.getAddressInfo(), 10000)
        val orderLines = mutableListOf(
            OrderLineRequest(1, 10),
            OrderLineRequest(2, 10)
        )
        every { productRepository.findByProductIdIn(any()) } returns products

        assertThrows<BadRequestException>(message = "유효하지 않은 상품이 존재합니다.") {
            orderService.buildOrderLines(order, orderLines)
        }
    }

//    @ParameterizedTest
//    @MethodSource("amounts")
//    @CsvSource(
//        "1, 3, 6, 9",
//        "2, 5, 6, 7",
//        "30, 50, 60, 70"
//    )
//    fun 오류_상품_재고를_초과하는_주문() {
//        val products = mutableListOf(Product(1, "상품1", Category.TOP, "", 1000, 100))
//        val order = Order(1, EasyRandomPlugin.getRandomAddressInfo(), 10000)
//        val orderLines = mutableListOf(
//            OrderLineRequest(1, 1000)
//        )
//
//        assertThrows<BadRequestException>(message = "상품 재고가 부족합니다.") {
//            orderService.buildOrderLines(order, products, orderLines)
//        }
//    }
//
//    companion object {
//        @JvmStatic
//        fun amounts() = listOf(
//            Arguments.of(1, 3, 6, 9),
//            Arguments.of(2, 5, 6, 7),
//            Arguments.of(30, 50, 60, 70),
//        )
//    }

}