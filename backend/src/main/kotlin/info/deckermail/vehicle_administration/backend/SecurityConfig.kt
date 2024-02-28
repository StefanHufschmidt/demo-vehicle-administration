package info.deckermail.vehicle_administration.backend

import jakarta.servlet.DispatcherType
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.ProviderManager
import org.springframework.security.authentication.dao.DaoAuthenticationProvider
import org.springframework.security.config.Customizer
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.factory.PasswordEncoderFactories
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun filterChain(
        http: HttpSecurity,
    ): SecurityFilterChain {
        return http
            .csrf { it.disable() }//No CSRF-token
            .formLogin { it.disable() }//No Form Login
            .logout { it.disable() }//No Logout
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } //No Session pls
            .authorizeHttpRequests {
                it.requestMatchers("/login").permitAll()
                    .requestMatchers("/web/**").authenticated()
                    .dispatcherTypeMatchers(DispatcherType.ERROR).permitAll()
            }
            .cors(Customizer.withDefaults())
            .httpBasic(Customizer.withDefaults())
            .build()
    }

    @Bean
    fun authenticationManager(
        userDetailsService: UserDetailsService,
        passwordEncoder: PasswordEncoder
    ): AuthenticationManager {
        val authenticationProvider = DaoAuthenticationProvider()
        authenticationProvider.setUserDetailsService(userDetailsService)
        authenticationProvider.setPasswordEncoder(passwordEncoder)

        val providerManager = ProviderManager(authenticationProvider)
        return providerManager
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder()
    }
}