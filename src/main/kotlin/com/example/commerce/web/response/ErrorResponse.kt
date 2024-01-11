package com.example.commerce.web.response

import com.example.commerce.web.response.ApiResponseCode
import lombok.Getter
import lombok.Setter

@Getter
@Setter
data class ErrorResponse(
    var code: ApiResponseCode? = null,
    var message: String? = null
)