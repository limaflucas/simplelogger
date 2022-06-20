package br.com.lflima.simplelogger.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
@RequiredArgsConstructor
public class Stock {

    private static final int TICKER_LEN = 5;
    private final String ticker;
    private String name;
    private Long quantity;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    public boolean hasValidTicker() {

        return this.ticker.length() == TICKER_LEN;
    }

    public boolean isValidStock() {

        return this.hasValidTicker() && !this.name.isBlank() && this.quantity > 0 && this.price > 0 && this.createdAt.isBefore(LocalDateTime.now());
    }
}
