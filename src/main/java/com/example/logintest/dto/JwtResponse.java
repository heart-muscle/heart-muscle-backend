package com.example.logintest.dto;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
@Getter
public class JwtResponse{

    private final String token;
    private final String username;
}