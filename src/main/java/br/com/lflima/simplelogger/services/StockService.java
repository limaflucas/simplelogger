package br.com.lflima.simplelogger.services;

import br.com.lflima.simplelogger.domain.Stock;
import br.com.lflima.simplelogger.repositories.StockRepository;
import br.com.lflima.simplelogger.services.dtos.*;
import br.com.lflima.simplelogger.services.ports.dtos.GotStockPortDTO;
import br.com.lflima.simplelogger.services.ports.StockPort;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Slf4j
@Component
@RequiredArgsConstructor
public class StockService {

    private final StockRepository stockRepository;
    private final StockPort stockPort;

    public CreatedNewStockDTO createNewStock(CreateNewStockDTO createNewStockDTO) {

        Stock newStock = new Stock(createNewStockDTO.getTicker(), createNewStockDTO.getName(), createNewStockDTO.getQuantity(), createNewStockDTO.getPrice(), LocalDateTime.now(), null);
        if (!newStock.hasValidTicker()) {
            log.error("Provided ticker is invalid");
            return new CreatedNewStockDTO(createNewStockDTO.getCorrelationID(), createNewStockDTO.getCorrelationID(), ServiceResponseStatus.FAILURE, HttpStatus.BAD_REQUEST, "Provided ticker is invalid");
        }

        GotStockPortDTO gotStockPortDTO = this.stockPort.getByTicker(newStock.getTicker(), createNewStockDTO.getCorrelationID(), createNewStockDTO.getTransactionID());
        if (gotStockPortDTO.getTicker() != null) {
            log.error("Attempt to create existing stock {} - {}", gotStockPortDTO.getTicker(), gotStockPortDTO.getName());
            return new CreatedNewStockDTO(createNewStockDTO.getCorrelationID(), createNewStockDTO.getTransactionID(), ServiceResponseStatus.FAILURE, HttpStatus.FORBIDDEN, "Stock already exists");
        }

//        if (this.stockRepository.findTickerData(createNewStockDTO.getTicker()).isPresent()) {
//            log.error("Attempt to create existing stock {}", createNewStockDTO.getTicker());
//            return new CreatedNewStockDTO(ServiceResponseStatus.FAILURE, HttpStatus.BAD_REQUEST, "Stock already exists");
//        }

        if (!newStock.isValidStock()) {
            log.error("This is not a valid stock");
            return new CreatedNewStockDTO(createNewStockDTO.getCorrelationID(), createNewStockDTO.getTransactionID(), ServiceResponseStatus.FAILURE, HttpStatus.BAD_REQUEST, "This is not a valid stock");
        }

        this.stockRepository.save(new StockReposityDTO(newStock.getTicker(), newStock.getName(), newStock.getQuantity(), newStock.getPrice(), newStock.getCreatedAt(), null));
        log.debug("Stock created with success");
        return new CreatedNewStockDTO(createNewStockDTO.getCorrelationID(), createNewStockDTO.getTransactionID(), ServiceResponseStatus.SUCCESS, HttpStatus.CREATED, null, newStock.getTicker(), newStock.getCreatedAt());
    }

    public GotStockDTO getStock(GetTickerDTO getTickerDTO) {

        Stock aStock = new Stock(getTickerDTO.getTicker());
        if (!aStock.hasValidTicker()) {
            log.error("Provided ticker {} is invalid.", getTickerDTO.getTicker());
            return new GotStockDTO(getTickerDTO.getCorrelationID(), getTickerDTO.getTransactionID(), ServiceResponseStatus.FAILURE, HttpStatus.BAD_REQUEST, "Provided ticker is invalid");
        }

        return this.stockRepository.findTickerData(getTickerDTO.getTicker())
                .map(tickerData -> new GotStockDTO(getTickerDTO.getCorrelationID(), getTickerDTO.getTransactionID(), ServiceResponseStatus.SUCCESS, HttpStatus.CREATED, null, tickerData.getTicker(), tickerData.getEnterprise(), tickerData.getQuantity(), tickerData.getPrice(), tickerData.getCreatedAt(), tickerData.getUpdatedAt()))
                .orElseGet(() -> {
                    log.warn("Requested ticker {} not found", getTickerDTO.getTicker());
                    return new GotStockDTO(getTickerDTO.getCorrelationID(), getTickerDTO.getTransactionID(), ServiceResponseStatus.NOT_FOUND, HttpStatus.NOT_FOUND, "Requested ticker not found");});
    }
}
