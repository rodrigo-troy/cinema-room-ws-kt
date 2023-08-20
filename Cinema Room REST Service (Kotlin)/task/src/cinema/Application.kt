package cinema

import com.fasterxml.jackson.annotation.JsonIgnore
import com.fasterxml.jackson.annotation.JsonProperty
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component

@SpringBootApplication
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}

interface SeatPricingStrategy {
    fun calculatePrice(row: Int): Int
}

@Component
class StandardSeatPricing : SeatPricingStrategy {
    override fun calculatePrice(row: Int): Int = if (row <= 4) 10 else 8
}

data class Seat(
    val row: Int,
    val column: Int,
    @JsonIgnore var purchased: Boolean = false,
    val price: Int,
    @JsonIgnore var token: String? = null
) {
    companion object {
        fun create(row: Int, column: Int, pricingStrategy: SeatPricingStrategy): Seat {
            return Seat(row, column, false, pricingStrategy.calculatePrice(row), null)
        }
    }
}

data class SeatRequest(val row: Int, val column: Int)

data class Ticket(
    val row: Int,
    val column: Int,
    val price: Int
)

data class PurchaseResponse(
    val token: String,
    val ticket: Ticket
)

data class ReturnRequest(val token: String)

data class ReturnedTicket(
    @JsonProperty("returned_ticket") val returnedTicket: Ticket
)

data class TheatreLayout(
    @JsonProperty("total_rows") val numberOfRows: Int,
    @JsonProperty("total_columns") val numberOfColumns: Int,
    @JsonProperty("available_seats") val availableSeats: List<Seat>
)

data class ErrorResponse(val error: String)

class SeatException(message: String) : RuntimeException(message)
