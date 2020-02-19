package com.devexperts.config;

import com.devexperts.exception.ErrorInfo;
import com.devexperts.exception.RestResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.stream.Collectors;

import static com.devexperts.exception.BaseMessagesError.INVALID_ARGUMENT;
import static com.devexperts.exception.BaseMessagesError.MISSING_PARAMETER;


@RestControllerAdvice
public class ErrorHandlingControllerAdvice {

    private Logger LOG = LoggerFactory.getLogger(ErrorHandlingControllerAdvice.class);

    @ExceptionHandler(RestResponseException.class)
    public ResponseEntity<ErrorInfo> handleRestResponseException(RestResponseException exception) {
        LOG.error("Handling exception in controller advice. Message: " + exception.getMessage());

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(exception.getMessage());
        errorInfo.setTraceId(exception.getTraceId());
        errorInfo.setStatus(String.valueOf(exception.getHttpStatus().value()));
        errorInfo.setError(exception.getHttpStatus().getReasonPhrase());
        errorInfo.setCode(exception.getErrorCode());

        return ResponseEntity.status(exception.getHttpStatus()).body(errorInfo);
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ErrorInfo> handleMethodArgumentNotValidException(MethodArgumentNotValidException exception) {
        LOG.error("Handling exception in controller advice. Message: " + exception.getMessage());

        String errorMsg = exception.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("\n"));

        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(errorMsg);
        errorInfo.setTraceId(MDC.get("traceId"));
        errorInfo.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errorInfo.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());
        errorInfo.setCode(INVALID_ARGUMENT.code());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorInfo);
    }

    @ExceptionHandler(MissingServletRequestParameterException.class)
    public ResponseEntity<ErrorInfo> handleMissingServletRequestParameterException(MissingServletRequestParameterException exception) {
        LOG.error("Handling exception in controller advice. Message: " + exception.getMessage());
        ErrorInfo errorInfo = getErrorInfo(exception);
        errorInfo.setCode(MISSING_PARAMETER.code());

        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errorInfo);
    }

    private ErrorInfo getErrorInfo(Exception exception) {
        ErrorInfo errorInfo = new ErrorInfo();
        errorInfo.setMessage(exception.getLocalizedMessage());
        errorInfo.setTraceId(MDC.get("traceId"));
        errorInfo.setStatus(String.valueOf(HttpStatus.BAD_REQUEST.value()));
        errorInfo.setError(HttpStatus.BAD_REQUEST.getReasonPhrase());

        return errorInfo;
    }

}

