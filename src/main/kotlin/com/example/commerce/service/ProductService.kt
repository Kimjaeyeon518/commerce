package com.example.commerce.service

import com.example.commerce.domain.entity.Member
import com.example.commerce.domain.entity.Product
import com.example.commerce.domain.entity.Store
import com.example.commerce.domain.enums.Category
import com.example.commerce.exception.BadRequestException
import com.example.commerce.exception.NotFoundException
import com.example.commerce.repository.MemberRepository
import com.example.commerce.repository.ProductRepository
import com.example.commerce.repository.StoreRepository
import com.example.commerce.web.request.ProductUpsertRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class ProductService(
    val storeRepository: StoreRepository,
    val productRepository: ProductRepository,
    val memberRepository: MemberRepository
) {

    @Transactional
    fun upsert(memberId: Long, request: ProductUpsertRequest): Product {
        val member = memberRepository.findById(memberId)
            .orElseThrow { NotFoundException("사용자를 찾을 수 없습니다.") }
        val store = storeRepository.findById(request.storeId)
            .orElseThrow { NotFoundException("상점이 존재하지 않습니다") }

        checkValidRelation(member, store)

        return if (request.productId == null) create(request, store) else update(request, store)
    }

    private fun create(request: ProductUpsertRequest, store: Store): Product {
        val product = buildProduct(request, store)
        return productRepository.save(product)
    }

    private fun update(request: ProductUpsertRequest, store: Store): Product {
        val product = productRepository.findByIdWithPessimisticLock(request.productId!!)
            ?: throw NotFoundException("상품이 존재하지 않습니다")

        product.update(buildProduct(request, store))

        return product
    }

    private fun buildProduct(
        request: ProductUpsertRequest,
        store: Store
    ): Product {
        return Product(
            name = request.name,
            store = store,
            category = request.category,
            description = request.description,
            price = request.price,
            amount = request.amount
        )
    }

    private fun checkValidRelation(
        member: Member,
        store: Store
    ) {
        if (member.memberId != store.member.memberId) {
            throw BadRequestException("자신의 상점에만 상품을 등록할 수 있습니다.")
        }
    }

    fun findByCategory(category: Category): List<Product> {
        return productRepository.findByCategory(category)
    }

}