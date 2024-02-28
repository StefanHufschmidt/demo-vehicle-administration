package info.deckermail.vehicle_administration.backend.email

import jakarta.mail.internet.InternetAddress
import org.slf4j.LoggerFactory
import org.springframework.mail.MailException
import org.springframework.mail.javamail.JavaMailSender
import org.springframework.mail.javamail.MimeMessageHelper
import org.springframework.stereotype.Service

@Service
class EmailSenderService (
    private val javaMailSender: JavaMailSender,
) {

    companion object {
        @JvmStatic
        private val logger = LoggerFactory.getLogger(EmailSenderService::class.java)
    }

    suspend fun sendEmail(to: InternetAddress, subject: String, body: String) {
        val mimeMessage = javaMailSender.createMimeMessage()
        val mimeMessageHelper = MimeMessageHelper(mimeMessage)
        mimeMessageHelper.setFrom("no-reply@vehicle-administration.deckermail.info")
        mimeMessageHelper.setSubject(subject)
        mimeMessageHelper.setText(body)
        mimeMessageHelper.setTo(to)

        try {
            javaMailSender.send(mimeMessage)
            logger.info("Successfully sent e-mail with subject '$subject' to '$to'.")
        } catch (e: MailException) {
            logger.error("Could not send e-mail with subject '$subject' to '$to'.", e)
        }
    }
}