package br.com.lflima.simplelogger.services.dtos;

import br.com.lflima.simplelogger.services.ServiceResponseStatus;
import lombok.Data;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.util.UUID;


@Getter
public abstract class ResponseDTO extends HeadersDTO {

    private final ServiceResponseStatus serviceResponseStatus;
    private final HttpStatus httpStatus;
    private final String message;

    public ResponseDTO(UUID correlationID, UUID transactionID, ServiceResponseStatus serviceResponseStatus, HttpStatus httpStatus, String message) {
        super(correlationID, transactionID);
        this.serviceResponseStatus = serviceResponseStatus;
        this.httpStatus = httpStatus;
        this.message = message;
    }
}
