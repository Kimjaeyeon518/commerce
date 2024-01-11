package com.example.commerce.jpatest

import com.example.commerce.domain.entity.Member
import com.example.commerce.domain.enums.MemberRole
import com.example.commerce.repository.MemberRepository
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertThrows
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest
import org.springframework.dao.DataIntegrityViolationException

@DataJpaTest
class MemberJpaTest {

    @Autowired
    lateinit var memberRepository: MemberRepository

    @Test
    fun 아이디_중복시_에러발생() {
        val member1 = Member("jae5181@naver.com", "wodus123", "재연", MemberRole.CUSTOMER, 100000)
        val member2 = Member("jae5181@naver.com", "wodus123", "재연", MemberRole.CUSTOMER, 100000)

        memberRepository.save(member1)

        assertThrows<DataIntegrityViolationException> {
            memberRepository.save(member2)
        }

    }
}