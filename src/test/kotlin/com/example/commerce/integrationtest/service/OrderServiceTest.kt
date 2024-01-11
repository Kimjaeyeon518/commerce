package com.example.commerce.integrationtest.service

import com.example.commerce.integrationtest.IntegrationTest
import com.example.commerce.domain.enums.OrderStatus
import com.example.commerce.repository.ProductRepository
import com.example.commerce.repository.MemberRepository
import com.example.commerce.repository.OrderRepository
import com.example.commerce.service.OrderService
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class OrderServiceTest(
    @Autowired
    private var orderService: OrderService,
    @Autowired
    private var productRepository: ProductRepository,
    @Autowired
    private var memberRepository: MemberRepository,
    @Autowired
    private var orderRepository: OrderRepository,
): IntegrationTest() {

    @Test
    fun 상품_동시주문_동시성_테스트1() {
        val numberOfThreads = 4
        val executor = Executors.newFixedThreadPool(numberOfThreads)
        val latch = CountDownLatch(4)

        executor.execute {
            orderService.buy(1, 1)
            latch.countDown()
        }
        executor.execute {
            orderService.buy(2, 2)
            latch.countDown()
        }
        executor.execute {
            orderService.buy(3, 3)
            latch.countDown()
        }
        executor.execute {
            orderService.buy(4, 4)
            latch.countDown()
        }
        latch.await()

        val product = productRepository.findById(1).orElseThrow()
        val user1 = memberRepository.findById(1).orElseThrow()
        val user2 = memberRepository.findById(2).orElseThrow()
        val user3 = memberRepository.findById(3).orElseThrow()
        val user4 = memberRepository.findById(4).orElseThrow()
        val order1 = orderRepository.findById(1).orElseThrow()
        val order2 = orderRepository.findById(2).orElseThrow()
        val order3 = orderRepository.findById(3).orElseThrow()
        val order4 = orderRepository.findById(4).orElseThrow()

        assertThat(user1.point).isEqualTo(990000)
        assertThat(user2.point).isEqualTo(990000)
        assertThat(user3.point).isEqualTo(990000)
        assertThat(user4.point).isEqualTo(990000)
        assertThat(product.amount).isEqualTo(9960)
        assertThat(product.saleCount).isEqualTo(40)
        assertThat(order1.status).isEqualTo(OrderStatus.COMPLETE)
        assertThat(order2.status).isEqualTo(OrderStatus.COMPLETE)
        assertThat(order3.status).isEqualTo(OrderStatus.COMPLETE)
        assertThat(order4.status).isEqualTo(OrderStatus.COMPLETE)
    }

    @Test
    fun 상품_동시주문_동시성_테스트2() {
        val numberOfThreads = 4
        val executor = Executors.newFixedThreadPool(numberOfThreads)
        val latch = CountDownLatch(4)

        executor.execute {
            orderService.buy(1, 1)
            latch.countDown()
        }
        executor.execute {
            orderService.buy(2, 2)
            latch.countDown()
        }
        executor.execute {
            orderService.buy(3, 3)
            latch.countDown()
        }
        executor.execute {
            orderService.buy(4, 4)
            latch.countDown()
        }
        latch.await()

        val product = productRepository.findById(1).orElseThrow()
        val user1 = memberRepository.findById(1).orElseThrow()
        val user2 = memberRepository.findById(2).orElseThrow()
        val user3 = memberRepository.findById(3).orElseThrow()
        val user4 = memberRepository.findById(4).orElseThrow()
        val order1 = orderRepository.findById(1).orElseThrow()
        val order2 = orderRepository.findById(2).orElseThrow()
        val order3 = orderRepository.findById(3).orElseThrow()
        val order4 = orderRepository.findById(4).orElseThrow()

        assertThat(user1.point).isEqualTo(990000)
        assertThat(user2.point).isEqualTo(990000)
        assertThat(user3.point).isEqualTo(990000)
        assertThat(user4.point).isEqualTo(990000)
        assertThat(product.amount).isEqualTo(9960)
        assertThat(product.saleCount).isEqualTo(40)
        assertThat(order1.status).isEqualTo(OrderStatus.COMPLETE)
        assertThat(order2.status).isEqualTo(OrderStatus.COMPLETE)
        assertThat(order3.status).isEqualTo(OrderStatus.COMPLETE)
        assertThat(order4.status).isEqualTo(OrderStatus.COMPLETE)
    }
}