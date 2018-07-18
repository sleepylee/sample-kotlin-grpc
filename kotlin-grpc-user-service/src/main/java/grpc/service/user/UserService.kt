package grpc.service.user

import com.google.common.base.Preconditions
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.async
import services.GetRequest
import services.KeyValueServiceGrpcKt.KeyValueServiceKtStub
import services.UserRequest
import services.UserResponse
import services.UserServiceGrpcKt.UserServiceImplBase

class UserService(keyValue: KeyValueServiceKtStub) : UserServiceImplBase() {

    private val keyValue = Preconditions.checkNotNull(keyValue)

    override fun getUser(request: UserRequest): Deferred<UserResponse> = async {
        fun getValue(key: String) = keyValue.get(
            GetRequest
                .newBuilder()
                .setKey(request.passId.toString() + key)
                .build() ?: throw IllegalArgumentException("key not found")
        )

        val name = getValue(".name")
        val email = getValue(".email")
        val country = getValue(".country")
        val active = getValue(".active")
        System.out.println("active = ${active.await().value}")
        UserResponse
            .newBuilder()
            .setPassId(if (request.passId < 0)
                throw IllegalArgumentException("name can not be null") else request.passId)
            .setName(name.await().value)
            .setActive(active.await().value.toBoolean())
            .setEmailAddress(email.await().value)
            .setCountry(country.await().value)
            .build()
    }

}
