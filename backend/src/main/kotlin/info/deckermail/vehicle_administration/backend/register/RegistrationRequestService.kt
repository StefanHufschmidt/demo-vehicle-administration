package info.deckermail.vehicle_administration.backend.register

import info.deckermail.vehicle_administration.backend.email.EmailSenderService
import info.deckermail.vehicle_administration.backend.users.UserRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import org.slf4j.LoggerFactory
import org.springframework.stereotype.Service

@Service
class RegistrationRequestService (
    private val userRepository: UserRepository,
    private val registrationRequestRepository: RegistrationRequestRepository,
    private val emailSenderService: EmailSenderService,
    private val asyncCoroutineDispatcher: CoroutineDispatcher,
) {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(RegistrationRequestService::class.java)
    }

    @Throws(IllegalArgumentException::class)
    fun processRegistrationRequest(requestData: RegistrationRequestData) {
        if (userRepository.existsByUsername(requestData.username)) {
            throw IllegalArgumentException("User with username ${requestData.username} already exists.")
        }

        if (registrationRequestRepository.existsByUsernameOrEmail(requestData.username, requestData.email.toString())) {
            throw IllegalArgumentException("User with username ${requestData.username} or e-mail ${requestData.email} already requested the registration.")
        }

        logger.info("Creating new entry for registration request.")
        val savedRegistrationRequest = registrationRequestRepository.save(
            RegistrationRequestEntity(
                username = requestData.username,
                password = requestData.password,
                email = requestData.email.toString(),
            )
        )

        logger.info("Sending registration code to ${requestData.email}.")
        CoroutineScope(asyncCoroutineDispatcher).launch {
            emailSenderService.sendEmail(
                requestData.email,
                "Your registration code for Vehicle Administration",
                "Here is your registration code: '${savedRegistrationRequest.code}'.",
            )
        }
    }
}