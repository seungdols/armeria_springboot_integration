package com.seungdols.company.sample

import com.fasterxml.jackson.annotation.JsonCreator
import com.fasterxml.jackson.annotation.JsonProperty
import com.linecorp.armeria.common.HttpRequest
import com.linecorp.armeria.common.HttpResponse
import com.linecorp.armeria.common.HttpStatus
import com.linecorp.armeria.server.ServiceRequestContext
import com.linecorp.armeria.server.annotation.ExceptionHandlerFunction
import io.micrometer.core.instrument.config.validate.ValidationException
import java.time.Instant

class ValidationExceptionHandler : ExceptionHandlerFunction {
    override fun handleException(
        ctx: ServiceRequestContext,
        req: HttpRequest,
        cause: Throwable,
    ): HttpResponse {
        return if (cause is ValidationException) {
            val status = HttpStatus.BAD_REQUEST
            HttpResponse.ofJson(
                status,
                ErrorResponse(
                    status.reasonPhrase(),
                    cause.message ?: "Unknown error",
                    req.path(),
                    status.code(),
                    Instant.now().toString(),
                ),
            )
        } else {
            ExceptionHandlerFunction.fallthrough()
        }
    }

    data class ErrorResponse
        @JsonCreator
        constructor(
            @JsonProperty("error") val error: String,
            @JsonProperty("message") val message: String,
            @JsonProperty("path") val path: String,
            @JsonProperty("status") val status: Int,
            @JsonProperty("timestamp") val timestamp: String,
        )
}
