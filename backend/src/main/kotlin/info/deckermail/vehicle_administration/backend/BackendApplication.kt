package info.deckermail.vehicle_administration.backend

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class BackendApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            runApplication<BackendApplication>(*args)
        }
    }
}
