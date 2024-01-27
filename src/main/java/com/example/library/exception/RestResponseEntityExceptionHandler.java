package com.example.library.exception;

import com.example.library.api.response.error.Error;
import com.example.library.api.response.error.ErrorResponse;
import com.example.library.exception.custom.BusinessRuleViolationException;
import com.example.library.exception.custom.NotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Collections;
import java.util.List;
import java.util.Objects;


@ControllerAdvice
public class RestResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

    private static final Logger LOGGER = LoggerFactory.getLogger(RestResponseEntityExceptionHandler.class);

    @ExceptionHandler(value = {BusinessRuleViolationException.class})
    protected ResponseEntity<Object> handleBusinessException(BusinessRuleViolationException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                getErrorResponse(HttpStatus.BAD_REQUEST.toString(), ex.getMessage()),
                new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(value = {NotFoundException.class})
    protected ResponseEntity<Object> handleNotFoundException(NotFoundException ex, WebRequest request) {
        return handleExceptionInternal(ex,
                getErrorResponse(HttpStatus.NOT_FOUND.toString(), ex.getMessage()),
                new HttpHeaders(), HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = {Exception.class})
    protected ResponseEntity<Object> handleUnknown(Exception ex, WebRequest request) {
        ex.printStackTrace();
        return handleExceptionInternal(ex,
                getErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.toString(), "Internal server error!"),
                new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @ExceptionHandler(value = RuntimeException.class)
    protected ResponseEntity<Object> handleRunTimeException(RuntimeException ex, WebRequest request) {
        LOGGER.error("[RestResponseEntityExceptionHandler] handleRunTimeException", ex);

        var errorResponse = getErrorResponse(HttpStatus.BAD_REQUEST.toString(),
                Objects.nonNull(ex.getCause()) ? ex.getCause().getMessage() : ex.getMessage());
        return handleExceptionInternal(ex, errorResponse, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, HttpHeaders headers,
                                                                  HttpStatusCode status, WebRequest request) {

        LOGGER.error("[RestResponseEntityExceptionHandler] handleMethodArgumentNotValid", ex);
        List<Error> errorList = ex.getFieldErrors()
                .stream()
                .map(f -> f.getField() + ": " + f.getDefaultMessage())
                .map(errorMessage -> getError(HttpStatus.BAD_REQUEST.toString(), errorMessage))
                .toList();

        return handleExceptionInternal(ex, getErrorResponse(errorList), new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    private ErrorResponse getErrorResponse(String code, String message) {
        var error = getError(code, message);
        return new ErrorResponse().errors(Collections.singletonList(error));
    }

    private ErrorResponse getErrorResponse(List<Error> errors) {
        return new ErrorResponse().errors(errors);
    }

    private Error getError(String code, String message) {
        return new Error().code(code).message(message);
    }
}