package info.deckermail.vehicle_administration.backend.login

import jakarta.validation.Valid
import org.slf4j.LoggerFactory
import org.springframework.web.bind.annotation.*

@RestController
@CrossOrigin(origins = [
    "http://localhost:9090",
    "http://localhost:3000",
])
@RequestMapping("/login")
class LoginController {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(LoginController::class.java)
    }

    @PostMapping
    fun login(@RequestBody @Valid loginData: LoginData) {
        logger.info("Login attempt for user ${loginData.username}.")
    }
}