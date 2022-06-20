package br.com.lflima.simplelogger.services.ports;

import br.com.lflima.simplelogger.services.ports.dtos.GotStockPortDTO;

import java.util.UUID;

public interface StockPort {

    GotStockPortDTO getByTicker(String ticker, UUID correlationHeader, UUID transactionHeader);
}
