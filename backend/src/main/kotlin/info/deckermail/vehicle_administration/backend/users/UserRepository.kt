package info.deckermail.vehicle_administration.backend.users

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface UserRepository: CrudRepository<UserEntity, UUID> {

    fun findByUsername(username: String): UserEntity?
    fun existsByUsername(username: String): Boolean
}