package com.example.commerce.integrationtest.service

import com.example.commerce.domain.enums.Category
import com.example.commerce.domain.enums.OrderStatus
import com.example.commerce.integrationtest.IntegrationTest
import com.example.commerce.repository.MemberRepository
import com.example.commerce.repository.OrderRepository
import com.example.commerce.repository.ProductRepository
import com.example.commerce.repository.StoreRepository
import com.example.commerce.service.OrderService
import com.example.commerce.service.ProductService
import com.example.commerce.web.request.ProductUpsertRequest
import org.assertj.core.api.Assertions
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import java.util.concurrent.CountDownLatch
import java.util.concurrent.Executors

class ProductServiceTest(
    @Autowired
    private var orderService: OrderService,
    @Autowired
    private var productService: ProductService,
    @Autowired
    private var productRepository: ProductRepository,
): IntegrationTest() {

    @Test
    fun 상품_재고_수정시_상품_주문_동시성_테스트() {
        val request = ProductUpsertRequest(1L, "상품1", 1L, Category.OUTER, "", 1000, 5000)
        val numberOfThreads = 5
        val executor = Executors.newFixedThreadPool(numberOfThreads)
        val latch = CountDownLatch(5)

        executor.execute {
            productService.upsert(1, request)
            latch.countDown()
        }
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

        Assertions.assertThat(product.amount).isEqualTo(14960)
    }
}
