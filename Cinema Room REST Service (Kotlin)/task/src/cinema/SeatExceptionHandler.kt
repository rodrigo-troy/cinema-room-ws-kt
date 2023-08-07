package cinema

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.RestControllerAdvice

/**
 * Created with IntelliJ IDEA.
$ Project: Cinema Room REST Service (Kotlin)
 * User: rodrigotroy
 * Date: 06-08-23
 * Time: 21:55
 */
@RestControllerAdvice
class GlobalExceptionHandler {

    @ExceptionHandler(SeatException::class)
    fun handleSeatException(ex: SeatException): ResponseEntity<ErrorResponse> {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(ErrorResponse(ex.message ?: "Unknown error"))
    }
}

