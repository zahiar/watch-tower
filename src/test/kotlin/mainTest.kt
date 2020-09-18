import org.junit.jupiter.api.Test
import software.amazon.awssdk.services.cloudtrail.model.Event
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class MainTest {

    @Test
    fun `Events triggered by internal AWS users should be ignored`() {
        assertTrue { shouldEventBeIgnored(Event.builder().username("ecs-service-scheduler").build()) }
        assertTrue { shouldEventBeIgnored(Event.builder().username("i-abcdefghijklmnopq").build()) }
        assertTrue { shouldEventBeIgnored(Event.builder().username("dynamodb").build()) }
        assertTrue { shouldEventBeIgnored(Event.builder().username("AutoScaling-Update").build()) }
    }

    @Test
    fun `Events triggered by external AWS users should not be ignored`() {
        assertFalse { shouldEventBeIgnored(Event.builder().username("z@a.com").build()) }
        assertFalse { shouldEventBeIgnored(Event.builder().username("z a").build()) }
    }

}
