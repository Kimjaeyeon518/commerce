package com.example.commerce.service

import com.example.commerce.exception.BadRequestException
import com.example.commerce.exception.DuplicateException
import com.example.commerce.exception.NotFoundException
import com.example.commerce.domain.enums.MemberRole
import com.example.commerce.auth.JwtPlugin
import com.example.commerce.domain.entity.Member
import com.example.commerce.exception.InvalidCredentialException
import com.example.commerce.repository.MemberRepository
import com.example.commerce.web.request.LoginRequest
import com.example.commerce.web.request.SignUpRequest
import jakarta.transaction.Transactional
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtPlugin: JwtPlugin
) {
    fun login(request: LoginRequest): String {
        val member = memberRepository.findByUsername(request.username)
            ?: throw BadRequestException("아이디가 일치하지 않습니다.")

        if (!passwordEncoder.matches(request.password, member.password) ) {
            throw BadRequestException("비밀번호가 일치하지 않습니다.")
        }

        return jwtPlugin.generateToken("${member.memberId}:${member.role}")
    }

    fun signUp(request: SignUpRequest): Member {
        val member = Member(
            username = request.username,
            password = passwordEncoder.encode(request.password),
            name = request.name,
            role = request.role,
            point = request.point
        )
        try {
            return memberRepository.save(member)
        } catch (e: DataIntegrityViolationException) {
            throw DuplicateException("중복된 ID 입니다.")
        }
    }

    @Transactional
    fun charge(memberId: Long, point: Long): Member {
        val member = memberRepository.findById(memberId).orElseThrow()
        member.chargePoint(point)
        return member
    }
}