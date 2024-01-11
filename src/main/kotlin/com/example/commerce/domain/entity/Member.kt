package com.example.commerce.domain.entity

import com.example.commerce.domain.enums.MemberRole
import com.example.commerce.exception.BadRequestException
import jakarta.persistence.*
import org.hibernate.validator.constraints.UniqueElements

@Entity
class Member(username: String, password: String, name: String, role: MemberRole, point: Long = 0) {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val memberId: Long? = null

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var carts: MutableList<Cart> = ArrayList()

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var favorites: MutableList<Favorite> = ArrayList()

    @OneToMany(mappedBy = "member", cascade = [CascadeType.ALL], orphanRemoval = true)
    var addresses: MutableList<Address> = ArrayList()

    @Column(name = "username", unique = true)
    var username: String = username   // 회원 ID

    @Column(name = "password")
    var password: String = password   // 회원 비밀번호

    @Column(name = "name")
    var name: String = name   // 회원 이름

    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    var role: MemberRole = role   // 회원 이름

    @Column(name = "point")
    var point: Long = point // 회원 포인트

    fun chargePoint(point: Long) {
        this.point += point
    }

    fun deductPoint(point: Long) {
        if (this.point < point) {
            throw BadRequestException("포인트가 부족합니다.")
        }
        this.point -= point
    }
}