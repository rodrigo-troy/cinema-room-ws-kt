package cinema

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*

/**
 * Created with IntelliJ IDEA.
$ Project: Cinema Room REST Service (Kotlin)
 * User: rodrigotroy
 * Date: 05-08-23
 * Time: 18:36
 */

@RestController
class TheatreController(private val seatService: SeatService) {

    @GetMapping("/seats")
    fun getSeats(): TheatreLayout = seatService.getSeats()

    @PostMapping("/purchase")
    fun purchaseSeat(@RequestBody request: SeatRequest): ResponseEntity<PurchaseResponse> =
        ResponseEntity.ok(seatService.purchaseSeat(request))

    @PostMapping("/return")
    fun returnTicket(@RequestBody request: ReturnRequest): ResponseEntity<Any> {
        val returnedTicket = seatService.returnTicket(request)

        return if (returnedTicket != null) {
            ResponseEntity.ok(returnedTicket)
        } else {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse("Wrong token!"))
        }
    }

    @GetMapping("/stats")
    fun getStatistics(@RequestParam password: String?): ResponseEntity<Any> {
        return seatService.getStatistics(password)
    }
}
