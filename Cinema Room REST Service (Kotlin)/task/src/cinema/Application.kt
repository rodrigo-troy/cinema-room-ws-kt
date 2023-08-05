package cinema

import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

data class Seat(
    @JsonProperty("row") val row: Int,
    @JsonProperty("column") val column: Int
)

data class TheatreLayout(
    @JsonProperty("total_rows") val numberOfRows: Int,
    @JsonProperty("total_columns") val numberOfColumns: Int,
    @JsonProperty("available_seats") val availableSeats: List<Seat>
)
