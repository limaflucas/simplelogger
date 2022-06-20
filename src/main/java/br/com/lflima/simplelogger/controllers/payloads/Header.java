package br.com.lflima.simplelogger.controllers.payloads;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Header implements Serializable {

    private UUID corrID;
    private UUID tranID;
    private LocalDateTime timestamp;

}
