package com.seungdols.company.sample

import io.grpc.stub.StreamObserver

open class HelloGrpcService : HelloServiceGrpc.HelloServiceImplBase() {
    override fun hello(
        request: Hello.HelloRequest,
        responseObserver: StreamObserver<Hello.HelloReply>,
    ) {
        val reply = Hello.HelloReply.newBuilder().setMessage("Hello, ${request.name}!").build()
        responseObserver.onNext(reply)
        responseObserver.onCompleted()
    }
}
