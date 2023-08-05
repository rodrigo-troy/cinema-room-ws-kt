package cinema

import org.springframework.web.bind.annotation.GetMapping
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

    @GetMapping("/seats")
    fun getSeats(): TheatreLayout {
        val totalRows = 9
        val totalColumns = 9

        val availableSeats = mutableListOf<Seat>()
        for (i in 1..totalRows) {
            for (j in 1..totalColumns) {
                availableSeats.add(Seat(i, j))
            }
        }

        return TheatreLayout(totalRows, totalColumns, availableSeats)
    }
}
