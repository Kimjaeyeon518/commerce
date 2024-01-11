package com.example.commerce.domain.entity

import com.example.commerce.exception.BadRequestException
import com.example.commerce.domain.enums.Category
import jakarta.persistence.*

@Entity
@Table(name = "product")
class Product(productId: Long? = null, store: Store, name: String, category: Category, description: String, price: Long, amount: Long) {

    init {
        store.addProducts(this)
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val productId: Long? = productId   // 상품 PK

    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "store_id", nullable = false)
    val store = store

    @Column(name = "name")
    var name = name   // 상품명

    @Column(name = "category")
    @Enumerated(EnumType.STRING)
    var category = category   // 상품 카테고리

    @Column(name = "description")
    var description = description   // 상품 설명

    @Column(name = "price")
    var price = price   // 상품 가격

    @Column(name = "amount")
    var amount = amount   // 잔여 상품 개수

    @Column(name = "sale_count")
    var saleCount: Long = 0  // 판매 개수

    fun purchased(amount: Long) {
        if (this.amount < amount) {
            throw BadRequestException("상품 재고가 부족합니다.")
        }
        this.amount -= amount
        this.saleCount += amount
    }

    fun update(buildProduct: Product) {
        this.name = buildProduct.name
        this.category = buildProduct.category
        this.description = buildProduct.description
        this.price = buildProduct.price
        this.amount += buildProduct.amount
    }
}