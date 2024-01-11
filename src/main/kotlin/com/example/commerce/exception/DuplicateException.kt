package com.example.commerce.exception

import com.example.commerce.web.response.ApiResponseCode
import com.example.commerce.exception.BaseException

class DuplicateException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.DUPLICATE_ENTITY
    override var message: String = message
}