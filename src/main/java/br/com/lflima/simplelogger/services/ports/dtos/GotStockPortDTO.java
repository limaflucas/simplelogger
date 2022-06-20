package br.com.lflima.simplelogger.services.ports.dtos;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GotStockPortDTO {

    private final String name;
    private final String ticker;
    private final Long quantity;
    private final Double price;
    private final LocalDateTime createdAt;
    private final LocalDateTime updatedAt;
}
