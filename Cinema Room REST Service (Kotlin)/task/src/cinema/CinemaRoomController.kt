package cinema

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

/**
 * Created with IntelliJ IDEA.
$ Project: Cinema Room REST Service (Kotlin)
 * User: rodrigotroy
 * Date: 03-06-23
 * Time: 14:22
 */
@RestController
class CinemaRoomController {

 @GetMapping("/seats")
    fun getSeats(): CinemaRoom {
    return CinemaRoom()
    }

}
