package com.clicks.project_monitoring.exceptions;

import com.clicks.project_monitoring.utils.CustomResponse;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@ResponseBody
public class CustomExceptionHandler {

    @ResponseStatus(HttpStatus.NOT_FOUND)
    @ExceptionHandler(ResourceNotFoundException.class)
    public CustomResponse handleNotFoundException(RuntimeException exception) {
        return new CustomResponse(false, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    @ExceptionHandler(UnauthorizedUserException.class)
    public CustomResponse handleUnauthorizedException(RuntimeException exception) {
        return new CustomResponse(false, exception.getMessage());
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ResourceExistsException.class)
    public CustomResponse handleResourceExistsException(RuntimeException exception) {
        return new CustomResponse(false, exception.getMessage());
    }
}
