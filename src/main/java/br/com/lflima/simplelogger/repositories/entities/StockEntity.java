package br.com.lflima.simplelogger.repositories.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.time.LocalDateTime;

@Entity(name = "stock")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class StockEntity {

    @Id private String ticker;
    private String enterprise;
    private Long quantity;
    private Double price;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}
