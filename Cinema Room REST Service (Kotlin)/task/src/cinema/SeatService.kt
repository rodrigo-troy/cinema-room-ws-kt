package cinema

import org.springframework.http.ResponseEntity

interface SeatService {
    fun getSeats(): TheatreLayout
    fun purchaseSeat(request: SeatRequest): PurchaseResponse
    fun returnTicket(request: ReturnRequest): ReturnedTicket?
    fun getStatistics(password: String?): ResponseEntity<Any>
}
