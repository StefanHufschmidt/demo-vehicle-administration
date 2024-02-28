package info.deckermail.vehicle_administration.backend

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("!integrationTest")
class AsyncCoroutinesDispatcherConfig {

    @Bean
    fun asyncCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.IO
}