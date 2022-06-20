package br.com.lflima.simplelogger.services.dtos;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@RequiredArgsConstructor
public class HeaderDTO implements Serializable {

    private final UUID transactionID;
    private final UUID correlationID;
    private final LocalDateTime receivedAt;
}
