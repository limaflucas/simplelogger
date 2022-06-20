package br.com.lflima.simplelogger.services.dtos;

import lombok.Data;

import java.util.UUID;

@Data
public abstract class HeadersDTO {

    private final UUID correlationID;
    private final UUID transactionID;
}
