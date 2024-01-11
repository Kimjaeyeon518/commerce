package com.example.commerce.exception

import com.example.commerce.exception.BaseException
import com.example.commerce.web.response.ApiResponseCode

class ServerException(message: String): BaseException() {
    override var code: ApiResponseCode = ApiResponseCode.INTERNAL_SERVER_ERROR
    override var message: String = message
}