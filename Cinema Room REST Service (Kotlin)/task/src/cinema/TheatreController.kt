package cinema

import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

/**
 * Created with IntelliJ IDEA.
$ Project: Cinema Room REST Service (Kotlin)
 * User: rodrigotroy
 * Date: 05-08-23
 * Time: 18:36
 */

@RestController
class TheatreController {
    private val totalRows = 9
    private val totalColumns = 9
    private var seats: MutableList<Seat> = List(totalRows) { row ->
        List(totalColumns) { col ->
            Seat(row + 1, col + 1)
        }
    }.flatten().toMutableList()


    @GetMapping("/seats")
    fun getSeats(): TheatreLayout {
        val availableSeats = seats.filterNot { it.purchased }
        return TheatreLayout(totalRows, totalColumns, availableSeats)
    }

    @PostMapping("/purchase")
    fun purchaseSeat(@RequestBody request: SeatRequest): ResponseEntity<Any> {
        val seat = seats.find { it.row == request.row && it.column == request.column }

        return when {
            seat == null || request.row > totalRows || request.column > totalColumns ->
                ResponseEntity.badRequest().body(mapOf("error" to "The number of a row or a column is out of bounds!"))

            seat.purchased ->
                ResponseEntity.badRequest().body(mapOf("error" to "The ticket has been already purchased!"))

            else -> {
                seat.purchased = true
                ResponseEntity.ok(SeatResponse(seat.row, seat.column, seat.price))
            }
        }
    }
}
