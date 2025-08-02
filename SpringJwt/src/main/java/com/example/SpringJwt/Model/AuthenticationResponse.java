package com.example.SpringJwt.Model;

public class AuthenticationResponse {

    private String jwtToken;

    public String getJwtToken() {
        return jwtToken;
    }

    public AuthenticationResponse(String jwtToken) {
        this.jwtToken = jwtToken;
    }

    public void setJwtToken(String jwtToken) {
        this.jwtToken = jwtToken;
    }
}
