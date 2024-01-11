package com.example.commerce.util

import com.example.commerce.domain.entity.Order
import com.example.commerce.domain.entity.Product
import com.example.commerce.domain.entity.Store
import com.example.commerce.domain.entity.valueobject.AddressInfo
import org.jeasy.random.EasyRandom
import org.jeasy.random.EasyRandomParameters
import org.jeasy.random.randomizers.range.LongRangeRandomizer
import java.util.stream.Collectors


class EasyRandomPlugin {

    companion object {
        private val parameters = EasyRandomParameters()
            .collectionSizeRange(4, 4)
            .stringLengthRange(5, 15)
            .randomize(Long::class.java, LongRangeRandomizer(50L, 100L))

        private val generator: EasyRandom = EasyRandom(parameters)

        fun getProduct(): Product {
            return generator.nextObject(Product::class.java)
        }

        fun getProductList(): List<Product> {
            return generator.objects(Product::class.java, 4)
                .collect(Collectors.toList())
        }

        fun getOrder(): Order {
            val order = generator.nextObject(Order::class.java)
            order.orderLines = order.orderLines
                .distinctBy { it.productId }
                .sortedBy { it.amount }
                .toMutableList()
            return order
        }

        fun getAddressInfo(): AddressInfo {
            return generator.nextObject(AddressInfo::class.java)
        }

        fun getStore(): Store {
            return generator.nextObject(Store::class.java)
        }
    }
}