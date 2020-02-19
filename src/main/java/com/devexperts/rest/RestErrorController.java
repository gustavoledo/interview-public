package com.devexperts.rest;

import com.devexperts.exception.RestResponseException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

import static com.devexperts.exception.BaseMessagesError.UNKNOWN;

@RestController
public class RestErrorController implements ErrorController {


    private Logger LOG = LoggerFactory.getLogger(RestErrorController.class);

    private static final String ERROR_PATH = "/error";

    @RequestMapping(value = ERROR_PATH)
    private void error(HttpServletRequest request) throws RestResponseException {
        LOG.error("Handling error in rest controller");
        String msg = String.valueOf(request.getAttribute(RequestDispatcher.ERROR_MESSAGE));

        throw new RestResponseException(msg) {
            @Override
            public HttpStatus getHttpStatus() {
                Object errorCode = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
                return httpStatus(errorCode);
            }

            @Override
            public String getErrorCode() {
                return UNKNOWN.code();
            }
        };
    }

    @Override
    public String getErrorPath() {
        return ERROR_PATH;
    }

    private HttpStatus httpStatus(Object statusCode) {
        if (statusCode != null) {
            if (statusCode instanceof Integer) {
                return HttpStatus.valueOf((Integer) statusCode);
            }

            try {
                return HttpStatus.valueOf(String.valueOf(statusCode));
            } catch (Exception ex) {
                LOG.error(" Error get path", ex);
            }
        }
        return HttpStatus.INTERNAL_SERVER_ERROR;
    }
}

