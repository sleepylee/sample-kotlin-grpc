package grpc.service.kv

import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import services.GetRequest
import services.GetResponse
import services.KeyValueServiceGrpcKt.KeyValueServiceImplBase
import services.PutRequest
import services.PutResponse

class KeyValueService : KeyValueServiceImplBase() {

    val ids = listOf(1, 2, 3, 5, 6)
    val names = listOf("timon", "kante", "yo", "antoni")

    internal var store: MutableMap<String, String> = hashMapOf(
            "${ids[0]}.name" to "${names[0]}",
            "${ids[0]}.email" to "${names[0]}@gmail.com",
            "${ids[0]}.country" to "US",
            "${ids[0]}.active" to "true",
            "${ids[1]}.name" to "${names[1]}",
            "${ids[1]}.email" to "${names[1]}@hotmail.com",
            "${ids[1]}.country" to "France",
            "${ids[1]}.active" to "true",
            "${ids[2]}.name" to "${names[2]}",
            "${ids[2]}.email" to "${names[2]}@gmail.com",
            "${ids[2]}.country" to "Columbia",
            "${ids[2]}.active" to "false",
            "${ids[3]}.name" to "${names[3]}",
            "${ids[3]}.email" to "${names[3]}@gmail.com",
            "${ids[3]}.country" to "Vietnam",
            "${ids[3]}.active" to "false"
    )

    override fun put(request: PutRequest): Deferred<PutResponse> = async {
        store.put(request.key ?: throw IllegalArgumentException("key can not be null"),
                request.value ?: throw IllegalArgumentException("value can not be null")
        )
        PutResponse.getDefaultInstance()
    }

    override fun get(request: GetRequest): Deferred<GetResponse> = async {
        GetResponse
                .newBuilder()
                .setValue(
                        store[request.key ?: throw IllegalArgumentException("key can not be null")]
                                ?: throw IllegalArgumentException("${request.key} key not found")
                ).build()
    }

}
