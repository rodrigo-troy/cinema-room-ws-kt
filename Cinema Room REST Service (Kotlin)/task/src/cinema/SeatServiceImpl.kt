package cinema

import org.springframework.stereotype.Service
import java.util.*

@Service
class SeatServiceImpl(private val pricingStrategy: SeatPricingStrategy) : SeatService {
    private val totalRows = 9
    private val totalColumns = 9
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

        seat.purchased = true
        seat.token = UUID.randomUUID().toString() // Generate the token
        return PurchaseResponse(seat.token!!, Ticket(seat.row, seat.column, seat.price))
    }

    override fun returnTicket(request: ReturnRequest): ReturnedTicket? {
        val seat = seats.find { it.token == request.token } ?: return null
        seat.purchased = false
        seat.token = null
        return ReturnedTicket(Ticket(seat.row, seat.column, seat.price))
    }
}
