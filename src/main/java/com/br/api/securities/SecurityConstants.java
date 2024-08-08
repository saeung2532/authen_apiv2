package com.br.api.securities;

public interface SecurityConstants {
    String SECRET_KEY = "AhaToken";
    String HEADER_REQUEST_TOKEN = "x-access-token";
    String HEADER_REQUEST_UUID = "x-access-uuid";
    String MDC_UUID_KEY = "UUID";
    String TOKEN_PREFIX = "Bearer ";
    String CLAIMS_ROLE = "role";
//    long EXPIRATION_TIME = 3000;
    long EXPIRATION_TIME = 864_000_000; // 10 days
}
