package info.deckermail.vehicle_administration.backend.register

import org.hibernate.engine.spi.SharedSessionContractImplementor
import org.hibernate.id.IdentifierGenerator

// Note: If you rename this class you need to adjust the RegistrationRequestData annotation for specifying the generator
class RegistrationCodeGenerator : IdentifierGenerator {
    companion object {
        @JvmStatic
        private val POSSIBLE_CHARS = (1..9) // avoid characters like 'l', 'i', '0', 'O' for good user experience
            .plus(('A'..'H'))
            .plus(listOf('K', 'M', 'N'))
            .plus(('P'..'Z'))
    }

    override fun generate(p0: SharedSessionContractImplementor?, p1: Any?): Any {
        return (1..6).map { POSSIBLE_CHARS.random() }.joinToString(separator = "")
    }
}