package com.example.commerce.domain.entity

import com.example.commerce.domain.entity.valueobject.AddressInfo
import jakarta.persistence.*

@Entity
class Address(
    @ManyToOne(fetch = FetchType.EAGER, cascade = [CascadeType.ALL])
    @JoinColumn(name = "member_id", nullable = false)
    val member: Member,
    addressInfo: AddressInfo
) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val addressId: Long? = null

    @get:Embedded
    var addressInfo = addressInfo
        protected set
}