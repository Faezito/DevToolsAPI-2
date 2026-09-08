package com.faezito.devToolsAPI.controller;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Health", description = "Conferência do estado da API") // TAG define o nomeCompleto e descrição que aparecerão na controller (SWAGGER)
@Hidden  // Oculta uma rota ou controller
public class HealthController {
    @GetMapping("/health")
    public String health(){
        return "API Online";
    }
}
