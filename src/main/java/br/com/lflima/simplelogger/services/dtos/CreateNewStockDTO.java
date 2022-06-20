package br.com.lflima.simplelogger.services.dtos;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
public class CreateNewStockDTO extends HeadersDTO{

    private final String name;
    private final String ticker;
    private final Long quantity;
    private final Double price;

    public CreateNewStockDTO(UUID correlationID, UUID transactionID, String name, String ticker, Long quantity, Double price) {
        super(correlationID, transactionID);
        this.name = name;
        this.ticker = ticker;
        this.quantity = quantity;
        this.price = price;
    }
}
