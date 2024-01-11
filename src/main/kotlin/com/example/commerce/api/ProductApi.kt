package com.example.commerce.api

import com.example.commerce.domain.entity.Product
import com.example.commerce.domain.enums.Category
import com.example.commerce.exception.BadRequestException
import com.example.commerce.service.ProductService
import com.example.commerce.web.request.ProductUpsertRequest
import com.example.commerce.web.response.ListResponse
import com.example.commerce.web.response.SingleResponse
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.*

@RestController
class ProductApi(
    val productService: ProductService,
) {

    @PostMapping("/products")
    @PreAuthorize("hasRole('ADMIN')")
    fun create(
        @AuthenticationPrincipal user: User,
        @Valid @RequestBody request: ProductUpsertRequest
    ): ResponseEntity<SingleResponse<Product>> {
        val product = productService.upsert(user.username.toLong(), request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("상품 생성 완료 !", product))
    }

    @PutMapping("/products/{productId}")
    @PreAuthorize("hasRole('ADMIN')")
    fun update(
        @AuthenticationPrincipal user: User,
        @PathVariable productId: Long,
        @Valid @RequestBody request: ProductUpsertRequest
    ): ResponseEntity<SingleResponse<Product>> {
        if (productId != request.productId!!) {
            throw BadRequestException("수정하려는 상품이 아닙니다.")
        }
        val product = productService.upsert(user.username.toLong(), request)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(SingleResponse.successOf("상품 생성 완료 !", product))
    }

    @GetMapping("/products")
    fun find(
        @RequestParam category: Category
    ): ResponseEntity<ListResponse<Product>> {
        val products = productService.findByCategory(category)
        return ResponseEntity
            .status(HttpStatus.OK)
            .body(ListResponse.successOf(products))
    }
}