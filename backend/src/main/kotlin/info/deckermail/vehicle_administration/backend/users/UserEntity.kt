package info.deckermail.vehicle_administration.backend.users

import jakarta.persistence.*
import java.time.LocalDateTime
import java.time.temporal.ChronoUnit
import java.util.*

@Entity(name = "users")
data class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    var id: UUID? = null,

    @Column(nullable = false, unique = true, updatable = false, length = 255)
    val username: String,

    @Column(nullable = false, length = 255)
    val password: String,

    @Column(nullable = false)
    val created: LocalDateTime = LocalDateTime.now().truncatedTo(ChronoUnit.MILLIS),

    @ManyToMany(cascade = [CascadeType.MERGE], fetch = FetchType.EAGER)
    @JoinTable(name="users_roles",
        joinColumns = [JoinColumn(name="users_id", referencedColumnName="id")],
        inverseJoinColumns = [JoinColumn(name="roles_id", referencedColumnName="id")]
    )
    val roles: List<RoleEntity>
)
