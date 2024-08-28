package com.seungdols.company.sample

import com.linecorp.armeria.server.annotation.ExceptionHandler
import com.linecorp.armeria.server.annotation.Get
import com.linecorp.armeria.server.annotation.Param
import org.springframework.stereotype.Component
import org.springframework.validation.annotation.Validated

@Component
@Validated
@ExceptionHandler(ValidationExceptionHandler::class)
class HelloAnnotatedService {
    @Get("/hello")
    fun hello(
        @Param("name") name: String,
    ): String {
        return "Hello, $name!"
    }
}
