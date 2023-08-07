package cinema

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

data class Seat(
    val row: Int,
    val column: Int,
    @JsonIgnore var purchased: Boolean = false, // Added this field
    val price: Int = if (row <= 4) 10 else 8
)

data class SeatRequest(val row: Int, val column: Int)

data class TheatreLayout(
    @JsonProperty("total_rows") val numberOfRows: Int,
    @JsonProperty("total_columns") val numberOfColumns: Int,
    @JsonProperty("available_seats") val availableSeats: List<Seat>
)

data class ErrorResponse(val error: String)

class SeatException(message: String) : RuntimeException(message)
