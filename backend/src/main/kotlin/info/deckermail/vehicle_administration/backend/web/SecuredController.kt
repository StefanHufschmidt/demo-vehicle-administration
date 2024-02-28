package info.deckermail.vehicle_administration.backend.web

import org.slf4j.LoggerFactory
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/web/ping")
class SecuredController {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(SecuredController::class.java)
    }

    @GetMapping
    fun test() {
        logger.info("Pong! ${SecurityContextHolder.getContext().authentication.name}")
    }
}