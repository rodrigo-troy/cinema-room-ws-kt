package cinema

interface SeatService {
    fun getSeats(): TheatreLayout
    fun purchaseSeat(request: SeatRequest): PurchaseResponse
    fun returnTicket(request: ReturnRequest): ReturnedTicket?
}
