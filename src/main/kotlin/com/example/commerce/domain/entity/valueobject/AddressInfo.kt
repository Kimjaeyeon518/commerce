package com.example.commerce.domain.entity.valueobject

import jakarta.persistence.Column
import jakarta.persistence.Embeddable

@Embeddable
data class AddressInfo (
    @Column
    var address1: String,
    @Column
    var address2: String,
    @Column(name = "zip_code")
    var zipCode: String
)