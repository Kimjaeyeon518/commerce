package com.example.commerce.unittest.entity

import com.example.commerce.domain.entity.Member
import com.example.commerce.domain.entity.Product
import com.example.commerce.domain.entity.Store
import com.example.commerce.domain.enums.Category
import com.example.commerce.domain.enums.MemberRole
import com.example.commerce.exception.BadRequestException
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows

class MemberUnitTest {

    @Test
    fun 사용자_잔여_포인트_차감() {
        val member = Member("jae5181@naver.com", "wodus123", "재연", MemberRole.CUSTOMER, 100000)
        member.deductPoint(10000)
        assertTrue(member.point == 90000L)
    }

    @Test
    fun 오류_사용자_잔여_포인트보다_큰_차감() {
        val member = Member("jae5181@naver.com", "wodus123", "재연", MemberRole.CUSTOMER, 100000)
        assertThrows<BadRequestException>(message = "포인트가 부족합니다.") {
            member.deductPoint(1000000)
        }
    }
}