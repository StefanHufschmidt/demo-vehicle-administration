package info.deckermail.vehicle_administration.backend.register

import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.RepeatedTest

internal class RegistrationCodeGeneratorSpec {

    companion object {
        @JvmStatic
        private val expectedCodeRegex = "[1-9A-Z]{6}".toRegex()
    }

    @RepeatedTest(5)
    internal fun `should generate proper request code`() {
        // given: instance
        val instance = RegistrationCodeGenerator()

        // expect: proper request code to be generated
        assertTrue((instance.generate(null, null) as String).matches(expectedCodeRegex))
    }
}
