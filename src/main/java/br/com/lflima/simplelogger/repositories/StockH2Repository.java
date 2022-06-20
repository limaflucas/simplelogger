package br.com.lflima.simplelogger.repositories;

import br.com.lflima.simplelogger.repositories.entities.StockEntity;
import org.springframework.data.repository.CrudRepository;

public interface StockH2Repository extends CrudRepository<StockEntity, String> {
}
