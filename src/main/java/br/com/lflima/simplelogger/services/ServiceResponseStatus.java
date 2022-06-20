package br.com.lflima.simplelogger.services;

import lombok.Data;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.http.HttpStatus;


@Getter
@RequiredArgsConstructor
public enum ServiceResponseStatus {

    SUCCESS,
    FAILURE,
    NOT_FOUND
}
