package com.example.commerce.integrationtest.service

import com.example.commerce.exception.BadRequestException
import com.example.commerce.integrationtest.IntegrationTest
import com.example.commerce.service.MemberService
import com.example.commerce.web.request.LoginRequest
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired

class MemberServiceTest(
    @Autowired
    private val memberService: MemberService,
): IntegrationTest() {

    @Test
    fun 아이디_오류() {
        val request = LoginRequest("jae518@naver.comzxcvzcxvzcx", "zxcvzxcvzcxv")

        assertThrows<BadRequestException>(message = "아이디가 일치하지 않습니다.") {
            memberService.login(request)
        }
    }

    @Test
    fun 비밀번호_오류() {
        val request = LoginRequest("jae518@naver.com", "wodus123123")

        assertThrows<BadRequestException>(message = "비밀번호가 일치하지 않습니다.") {
            memberService.login(request)
        }
    }

    @Test
    fun 로그인_성공() {
        val request = LoginRequest("jae518@naver.com", "wodus123")

        val token = memberService.login(request)

        assertThat(token).isNotNull()
    }
}