package cinema

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.RequestParam
import java.util.*

@Service
class SeatServiceImpl(private val pricingStrategy: SeatPricingStrategy) : SeatService {
    private val totalRows = 9
    private val totalColumns = 9
    private val totalSeats = totalRows * totalColumns
    private var currentIncome = 0
    private var seats: MutableList<Seat> = generateSeats()

    private fun generateSeats() = List(totalRows) { row ->
        List(totalColumns) { col ->
            Seat.create(row + 1, col + 1, pricingStrategy)
        }
    }.flatten().toMutableList()


    override fun getSeats(): TheatreLayout {
        val availableSeats = seats.filterNot { it.purchased }
        return TheatreLayout(totalRows, totalColumns, availableSeats)
    }


    override fun purchaseSeat(request: SeatRequest): PurchaseResponse {
        val seat = seats.find { it.row == request.row && it.column == request.column }
            ?: throw SeatException("The number of a row or a column is out of bounds!")
        if (seat.purchased) throw SeatException("The ticket has been already purchased!")

        currentIncome += seat.price
        seat.purchased = true
        seat.token = UUID.randomUUID().toString() // Generate the token
        return PurchaseResponse(seat.token!!, Ticket(seat.row, seat.column, seat.price))
    }

    override fun returnTicket(request: ReturnRequest): ReturnedTicket? {
        val seat = seats.find { it.token == request.token } ?: return null
        currentIncome -= seat.price
        seat.purchased = false
        seat.token = null
        return ReturnedTicket(Ticket(seat.row, seat.column, seat.price))
    }

    override fun getStatistics(@RequestParam password: String?): ResponseEntity<Any> {
        // Check if password parameter is "super_secret"
        if (password != "super_secret") {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(ErrorResponse("The password is wrong!"))
        }

        val numberOfPurchasedTickets = seats.count { it.purchased }
        val numberOfAvailableSeats = totalSeats - numberOfPurchasedTickets

        return ResponseEntity.ok(
            mapOf(
                "current_income" to currentIncome,
                "number_of_available_seats" to numberOfAvailableSeats,
                "number_of_purchased_tickets" to numberOfPurchasedTickets
            )
        )
    }
}
