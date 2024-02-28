package info.deckermail.vehicle_administration.backend.register

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/register/request")
class RegistrationRequestController(
    private val registrationRequestService: RegistrationRequestService,
) {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(RegistrationRequestController::class.java)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    @ResponseStatus(HttpStatus.CONFLICT)
    fun handleIllegalStateException(e: IllegalArgumentException) {
        logger.warn(e.message)
        logger.debug(e.message, e)
    }

    @PostMapping
    @Throws(IllegalArgumentException::class)
    fun requestRegistration(@RequestBody @Valid registrationRequest: RegistrationRequestData) {
        registrationRequestService.processRegistrationRequest(registrationRequest)
    }
}