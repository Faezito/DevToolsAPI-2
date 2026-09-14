package com.faezito.devToolsAPI.service.interfaces;

import com.faezito.devToolsAPI.model.DTOs.LoginRequestDTO;
import com.faezito.devToolsAPI.model.DTOs.LoginResponseDTO;

public interface IAcessoService {
    LoginResponseDTO Login(LoginRequestDTO req);
}