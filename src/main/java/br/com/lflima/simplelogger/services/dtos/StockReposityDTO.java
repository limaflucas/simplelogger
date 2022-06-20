package br.com.lflima.simplelogger.services.dtos;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
public class StockReposityDTO {

    private String ticker;
    private String enterprise;
    private Long quantity;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
