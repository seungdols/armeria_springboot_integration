package com.seungdols.company.configuration

import com.linecorp.armeria.common.grpc.GrpcSerializationFormats
import com.linecorp.armeria.server.ServerBuilder
import com.linecorp.armeria.server.docs.DocService
import com.linecorp.armeria.server.grpc.GrpcService
import com.linecorp.armeria.server.logging.AccessLogWriter
import com.linecorp.armeria.server.logging.LoggingService
import com.linecorp.armeria.spring.ArmeriaServerConfigurator
import com.seungdols.company.sample.HelloAnnotatedService
import com.seungdols.company.sample.HelloGrpcService
import com.seungdols.company.sample.HelloServiceGrpc
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ArmeriaConfiguration {
    @Bean
    fun armeriaServerConfigurator(
        helloAnnotatedService: HelloAnnotatedService,
    ): ArmeriaServerConfigurator {
        return ArmeriaServerConfigurator { builder: ServerBuilder ->
            builder.serviceUnder("/docs", DocService())
            builder.decorator(LoggingService.newDecorator())
            builder.accessLogWriter(AccessLogWriter.combined(), false)

            val services =
                listOf(
                    helloAnnotatedService,
                )

            services.forEach { service ->
                builder.annotatedService(service)
            }

            builder.service(
                GrpcService.builder()
                    .addService(HelloServiceGrpc.bindService(HelloGrpcService()))
                    .supportedSerializationFormats(GrpcSerializationFormats.values())
                    .enableUnframedRequests(true)
                    .build(),
            )
        }
    }
}
