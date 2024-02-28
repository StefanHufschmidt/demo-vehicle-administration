package info.deckermail.vehicle_administration.backend.users

import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service


@Service
class UserDetailServiceImpl(
    private val userRepository: UserRepository,
): UserDetailsService {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(UserDetailServiceImpl::class.java)
    }


    override fun loadUserByUsername(username: String?): UserDetails {
        logger.info("Loading user for username '$username'.")
        val userEntity = username?.let {
            userRepository.findByUsername(it)
        } ?: throw UsernameNotFoundException("No user with name '$username' found.")

        return User.withDefaultPasswordEncoder()
            .username(username)
            .password(userEntity.password)
            .roles(*userEntity.roles.mapNotNull { it.toRole()?.name }.toTypedArray())
            .build()
    }
}