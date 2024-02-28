package info.deckermail.vehicle_administration.backend.users

import org.slf4j.LoggerFactory
import org.springframework.context.ApplicationListener
import org.springframework.context.event.ContextRefreshedEvent
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Component

@Component
class StartupRoleInitializer(
    private val roleRepository: RoleRepository,
    private val userRepository: UserRepository,
): ApplicationListener<ContextRefreshedEvent> {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(StartupRoleInitializer::class.java)
    }

    override fun onApplicationEvent(event: ContextRefreshedEvent) {
        roleRepository.findByIdOrNull(1) ?: roleRepository.save(RoleEntity(1, Role.ADMIN.roleValue)).also {
            logger.info("Initialized admin role. It did not exist before.")
        }
        roleRepository.findByIdOrNull(2) ?: roleRepository.save(RoleEntity(2, Role.USER.roleValue)).also {
            logger.info("Initialized user role. It did not exist before.")
        }

        // Additionally saving our admin user here just for convenience
        userRepository.save(UserEntity(
            username = "MrT",
            password = "aTeam",
            roles = listOf(
                RoleEntity(1, Role.ADMIN.roleValue),
                RoleEntity(2, Role.USER.roleValue),
            )
        ))
    }
}