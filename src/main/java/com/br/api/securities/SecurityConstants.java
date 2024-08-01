package com.br.api.securities;

public interface SecurityConstants {
    String SECRET_KEY = "AhaToken";
    String TOKEN_PREFIX = "Bearer ";
    String HEADER_AUTHORIZATION = "Authorization";
    String CLAIMS_ROLE = "role";
    long EXPIRATION_TIME = 864_000_000; // 10 days
}
