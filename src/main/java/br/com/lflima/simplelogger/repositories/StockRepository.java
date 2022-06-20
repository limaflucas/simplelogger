package br.com.lflima.simplelogger.repositories;

import br.com.lflima.simplelogger.repositories.entities.StockEntity;
import br.com.lflima.simplelogger.services.dtos.StockReposityDTO;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class StockRepository {

    private final StockH2Repository stockH2Repository;

    public void save(StockReposityDTO stockReposityDTO) {

        StockEntity stockEntity = new StockEntity(stockReposityDTO.getTicker(), stockReposityDTO.getEnterprise(),
                stockReposityDTO.getQuantity(), stockReposityDTO.getPrice(), stockReposityDTO.getCreatedAt(),
                stockReposityDTO.getUpdatedAt());

        this.stockH2Repository.save(stockEntity);
    }

    public Optional<StockReposityDTO> findTickerData(String ticker) {

        return this.stockH2Repository.findById(ticker)
                .map(r -> new StockReposityDTO(
                        r.getTicker(), r.getEnterprise(), r.getQuantity(), r.getPrice(),
                        r.getCreatedAt(), r.getUpdatedAt()));
    }
}
