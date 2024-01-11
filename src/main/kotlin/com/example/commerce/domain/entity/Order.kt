package com.example.commerce.domain.entity

import com.example.commerce.domain.entity.common.BaseTimeEntity
import com.example.commerce.domain.entity.valueobject.AddressInfo
import com.example.commerce.domain.enums.OrderStatus
import com.example.commerce.exception.BadRequestException
import jakarta.persistence.*

@Entity
@Table(name = "orders")
class Order(
    memberId: Long,
    addressInfo: AddressInfo,
    totalPrice: Long = 0
): BaseTimeEntity() {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val orderId: Long? = null

    @OneToMany(mappedBy = "order", cascade = [CascadeType.ALL], orphanRemoval = true)
    var orderLines: MutableList<OrderLine> = ArrayList()

    @Column(name = "member_id")
    val memberId = memberId

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    var status: OrderStatus = OrderStatus.WAITING_PAY   // 초기 주문 상태 (결제 대기)

    @Column(name = "total_price")
    var totalPrice = totalPrice

    @get:Embedded
    var addressInfo = addressInfo
        protected set

    fun addOrderline(orderLine: OrderLine) {
        this.orderLines.add(orderLine)
    }

    fun cancel() {
        if (this.status != OrderStatus.COMPLETE) {
            throw BadRequestException("주문을 취소할 수 없습니다. 주문 상태 >> " + this.status.value)
        }
        this.status = OrderStatus.CANCEL
    }

    fun toComplete() {
        if (this.status != OrderStatus.WAITING_PAY) {
            throw BadRequestException("주문을 완료할 수 없습니다. 주문 상태 >> " + this.status.value)
        }
        this.status = OrderStatus.COMPLETE
    }
}