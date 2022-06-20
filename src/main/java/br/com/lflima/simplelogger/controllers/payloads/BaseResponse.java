package br.com.lflima.simplelogger.controllers.payloads;

import lombok.Getter;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
public abstract class BaseResponse {

    private String message;
}
