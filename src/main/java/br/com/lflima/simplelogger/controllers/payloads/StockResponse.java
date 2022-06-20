package br.com.lflima.simplelogger.controllers.payloads;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Getter
@SuperBuilder
public class StockResponse extends BaseResponse {

    private String name;
    private String ticker;
    private Long quantity;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
