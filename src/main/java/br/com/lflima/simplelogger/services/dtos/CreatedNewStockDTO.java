package br.com.lflima.simplelogger.services.dtos;

import br.com.lflima.simplelogger.services.ServiceResponseStatus;
import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class CreatedNewStockDTO extends ResponseDTO {

    private String ticker;
    private LocalDateTime createAt;

    public CreatedNewStockDTO(UUID correlationID, UUID transactionID, ServiceResponseStatus serviceResponseStatus, HttpStatus httpStatus, String message) {
        super(correlationID, transactionID, serviceResponseStatus, httpStatus, message);
    }

    public CreatedNewStockDTO(UUID correlationID, UUID transactionID, ServiceResponseStatus serviceResponseStatus, HttpStatus httpStatus, String message, String ticker, LocalDateTime createAt) {
        super(correlationID, transactionID, serviceResponseStatus, httpStatus, message);
        this.ticker = ticker;
        this.createAt = createAt;
    }
}
