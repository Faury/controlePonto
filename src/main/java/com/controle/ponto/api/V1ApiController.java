package com.controle.ponto.api;

import java.util.Optional;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("${openapi.controleDePonto.base-path:}")
public class V1ApiController implements V1Api {

    private final V1ApiDelegate delegate;

    public V1ApiController(@org.springframework.beans.factory.annotation.Autowired(required = false) V1ApiDelegate delegate) {
        this.delegate = Optional.ofNullable(delegate).orElse(new V1ApiDelegate() {});
    }

    @Override
    public V1ApiDelegate getDelegate() {
        return delegate;
    }

}
