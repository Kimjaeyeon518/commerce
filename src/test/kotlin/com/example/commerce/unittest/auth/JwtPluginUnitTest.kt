package com.example.commerce.unittest.auth

import com.example.commerce.auth.JwtPlugin
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test

class JwtPluginUnitTest {

    private val jwtPlugin: JwtPlugin = JwtPlugin("MyTestSecretKeyItMustBeLongerThan512BitsBecauseOfUsingHS512Algorithm", 24, "wodus")

    @Test
    fun 토큰_생성() {
        val token = jwtPlugin.generateToken("1")
        val subject = jwtPlugin.validateTokenAndGetSubject(token)
        assertTrue(subject == "1")
    }


}