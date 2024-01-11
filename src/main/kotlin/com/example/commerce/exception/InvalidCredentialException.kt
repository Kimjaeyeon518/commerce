package com.example.commerce.exception

import com.example.commerce.web.response.ApiResponseCode

class InvalidCredentialException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.FORBIDDEN
    override var message: String = message
}