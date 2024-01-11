package com.example.commerce.api

import com.example.commerce.domain.entity.Member
import com.example.commerce.web.request.LoginRequest
import com.example.commerce.web.response.SingleResponse
import com.example.commerce.service.MemberService
import com.example.commerce.web.request.ChargeRequest
import com.example.commerce.web.request.SignUpRequest
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class MemberApi(
    private val memberService: MemberService
) {
    @PostMapping("/login")
    fun signIn(
        @Valid @RequestBody request: LoginRequest
    ): ResponseEntity<SingleResponse<String>> {
        val token = memberService.login(request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("로그인 성공", token))
    }

    @PostMapping("/signup")
    fun signUp(
        @Valid @RequestBody request: SignUpRequest
    ): ResponseEntity<SingleResponse<String>> {
        memberService.signUp(request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.success("회원가입 성공"))
    }

    @PostMapping("/charge")
    fun charge(
        @AuthenticationPrincipal user: org.springframework.security.core.userdetails.User,
        @Valid @RequestBody request: ChargeRequest
    ): ResponseEntity<SingleResponse<Member>> {
        val chargedMember = memberService.charge(user.username.toLong(), request.chargePoint)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("충전 성공", chargedMember))
    }
}