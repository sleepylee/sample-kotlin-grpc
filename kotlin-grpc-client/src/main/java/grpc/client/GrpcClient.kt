package grpc.client

import io.grpc.ManagedChannelBuilder
import services.UserRequest
import services.UserServiceGrpc
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {

    val channel = ManagedChannelBuilder.forAddress("localhost", 15001).usePlaintext(true).build()
    val blockingStub = UserServiceGrpc.newBlockingStub(channel)

    try {
        val ids = listOf(1, 2, 3, 5)
        for (id in ids) {
            val request = UserRequest.newBuilder().setPassId(id).build()
            val userResponse = blockingStub.getUser(request)
            println("Response from server:\n${userResponse}isActive: ${userResponse.active}\n")
        }

    } finally {
        channel.shutdown().awaitTermination(5, TimeUnit.SECONDS)
    }
}
