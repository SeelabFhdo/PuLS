package de.fhdo.puls.environment_service.command.api

import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.context.request.WebRequest
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler
import javax.validation.ConstraintViolationException

@ControllerAdvice
class RestResponseEntityExceptionHandler : ResponseEntityExceptionHandler() {

    @ExceptionHandler(ConstraintViolationException::class)
    fun handleConstraintViolationException(exception: Exception, request: WebRequest):
        ResponseEntity<Any> {
        val constraintViolationException = exception as ConstraintViolationException
        val errors = constraintViolationException.constraintViolations.joinToString(", ")
        return ResponseEntity(errors as Any, HttpHeaders(), HttpStatus.BAD_REQUEST)
    }
}