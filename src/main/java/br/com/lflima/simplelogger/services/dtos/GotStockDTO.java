package br.com.lflima.simplelogger.services.dtos;

import br.com.lflima.simplelogger.services.ServiceResponseStatus;
import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
public class GotStockDTO extends ResponseDTO {

    private String ticker;
    private String enterprise;
    private Long quantity;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public GotStockDTO(UUID correlationID, UUID transactionID, ServiceResponseStatus serviceResponseStatus, HttpStatus httpStatus, String message) {
        super(correlationID, transactionID, serviceResponseStatus, httpStatus, message);
    }

    public GotStockDTO(UUID correlationID, UUID transactionID, ServiceResponseStatus serviceResponseStatus, HttpStatus httpStatus, String message, String ticker, String enterprise, Long quantity, Double price, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super(correlationID, transactionID, serviceResponseStatus, httpStatus, message);
        this.ticker = ticker;
        this.enterprise = enterprise;
        this.quantity = quantity;
        this.price = price;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
