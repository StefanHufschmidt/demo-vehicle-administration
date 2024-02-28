package info.deckermail.vehicle_administration.backend

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class AsyncCoroutinesTestDispatcherConfig {

    @Bean
    fun asyncCoroutineDispatcher(): CoroutineDispatcher = Dispatchers.Unconfined

}