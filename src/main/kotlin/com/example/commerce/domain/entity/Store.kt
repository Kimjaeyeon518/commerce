package com.example.commerce.domain.entity

import jakarta.persistence.*
import org.hibernate.validator.constraints.UniqueElements

@Entity
class Store(
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,
) {
    @OneToMany(mappedBy = "store", cascade = [CascadeType.ALL], orphanRemoval = true)
    var products: MutableList<Product> = ArrayList()

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val storeId: Long? = null

    fun addProducts(product: Product) {
        this.products.add(product)
    }
}