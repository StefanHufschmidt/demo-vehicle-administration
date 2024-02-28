package info.deckermail.vehicle_administration.backend.users

import jakarta.persistence.Entity
import jakarta.persistence.Id

enum class Role(
    val roleValue: Int,
) {
    ADMIN(1),
    USER(2),
}

@Entity(name = "roles")
data class RoleEntity(
    @Id
    val id: Int? = null,
    val roleValue: Int,
) {
    fun toRole(): Role? {
        return Role.entries.find { it.roleValue == roleValue }
    }
}
