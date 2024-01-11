package com.example.commerce.exception

import com.example.commerce.exception.BaseException
import com.example.commerce.web.response.ApiResponseCode

class BadRequestException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.BAD_REQUEST
    override var message: String = message
}