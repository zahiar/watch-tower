import software.amazon.awssdk.services.cloudtrail.CloudTrailClient
import software.amazon.awssdk.services.cloudtrail.model.Event
import software.amazon.awssdk.services.cloudtrail.model.LookupAttribute
import software.amazon.awssdk.services.cloudtrail.model.LookupAttributeKey
import java.time.Instant
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

const val IGNORED_INTERNAL_AWS_USERNAMES_REGEX = "ecs-service-scheduler|i-[a-z0-9]{17}|dynamodb|AutoScaling-.*"
fun shouldEventBeIgnored(event: Event) = Regex(IGNORED_INTERNAL_AWS_USERNAMES_REGEX).matches(event.username())

fun printHeader() = println("Time\tUsername\tEvent\tResource")
fun printRow(event: Event) = println("${event.eventTime()}\t${event.username()}\t${event.eventName()}\t${event.resources()}")

fun main() {
    val dateToSearch = System.getenv("DATE_TO_SEARCH") ?: LocalDateTime.now().format(DateTimeFormatter.ISO_DATE)
    val eventsList = mutableListOf<Event>()

    val ctClient = CloudTrailClient.create()

    ctClient
        .lookupEventsPaginator {
            it
                .startTime(Instant.parse("${dateToSearch}T00:00:00.00Z"))
                .endTime(Instant.parse("${dateToSearch}T23:59:59.59Z"))
                .lookupAttributes(
                    LookupAttribute.builder()
                        .attributeKey(LookupAttributeKey.READ_ONLY)
                        .attributeValue("false")
                        .build()
                )
        }
        .stream()
        .forEach {
            eventsList.addAll(it.events().filter { event -> !shouldEventBeIgnored(event) })
        }

    printHeader()
    eventsList
        .sortedBy { it.eventTime() }
        .sortedBy { it.username() }
        .forEach { printRow(it) }
}
