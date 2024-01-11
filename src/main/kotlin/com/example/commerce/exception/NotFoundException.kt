package com.example.commerce.exception

import com.example.commerce.exception.BaseException
import com.example.commerce.web.response.ApiResponseCode

class NotFoundException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.NOT_FOUND
    override var message: String = message
}