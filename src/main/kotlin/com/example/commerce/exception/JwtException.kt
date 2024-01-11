package com.example.commerce.exception

import com.example.commerce.exception.BaseException
import com.example.commerce.web.response.ApiResponseCode

class JwtException(
    override var code: ApiResponseCode = ApiResponseCode.UNAUTHORIZED,
    override var message: String
): BaseException()