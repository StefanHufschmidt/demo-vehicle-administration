package info.deckermail.vehicle_administration.backend.register

import org.springframework.data.repository.CrudRepository

interface RegistrationRequestRepository: CrudRepository<RegistrationRequestEntity, RequestCode> {

    fun existsByUsernameOrEmail(username: String, email: String): Boolean

    fun findByCode(code: RequestCode): RegistrationRequestEntity?
}