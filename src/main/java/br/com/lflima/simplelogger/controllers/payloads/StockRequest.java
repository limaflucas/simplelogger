package br.com.lflima.simplelogger.controllers.payloads;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class StockRequest extends Header {

    private String name;
    private String ticker;
    private Long quantity;
    private Double price;

}
