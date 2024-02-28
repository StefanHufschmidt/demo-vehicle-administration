package info.deckermail.vehicle_administration.backend

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

@Configuration
class CorsConfig {
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val source = UrlBasedCorsConfigurationSource()
        source.apply {
            registerCorsConfiguration("/**", CorsConfiguration().apply {
                addAllowedOrigin("*")
                addAllowedMethod("*")
                addAllowedHeader("*")
                maxAge = 3600
            })
        }
        return source
    }
}