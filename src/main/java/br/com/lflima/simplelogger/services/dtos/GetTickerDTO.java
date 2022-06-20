package br.com.lflima.simplelogger.services.dtos;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
public class GetTickerDTO extends HeadersDTO {

    private final String ticker;

    public GetTickerDTO(UUID correlationID, UUID transactionID, String ticker) {
        super(correlationID, transactionID);
        this.ticker = ticker;
    }
}
