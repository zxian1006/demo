package com.zx.springboot.demo.entity;


public class LoginResponse {
    private String username;
    private String name;
    private String state;
    private String commonAuthId;
    private String opbs;
    private String bearer;
    private String defaultToken;
    private String clientId;
    private String amRefToken;
    private String jSessionId;
    private String expires;
    private String email;
    private char authCodeAccess;

    private String amIdTOkenDefaultP2;
    private String amIdTOkenDefaultP1;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCommonAuthId() {
        return commonAuthId;
    }

    public void setCommonAuthId(String commonAuthId) {
        this.commonAuthId = commonAuthId;
    }

    public String getOpbs() {
        return opbs;
    }

    public void setOpbs(String opbs) {
        this.opbs = opbs;
    }

    public String getBearer() {
        return bearer;
    }

    public void setBearer(String bearer) {
        this.bearer = bearer;
    }

    public String getDefaultToken() {
        return defaultToken;
    }

    public void setDefaultToken(String defaultToken) {
        this.defaultToken = defaultToken;
    }

    public String getClientId() {
        return clientId;
    }

    public void setClientId(String clientId) {
        this.clientId = clientId;
    }

    public String getAmRefToken() {
        return amRefToken;
    }

    public void setAmRefToken(String amRefToken) {
        this.amRefToken = amRefToken;
    }

    public String getjSessionId() {
        return jSessionId;
    }

    public void setjSessionId(String jSessionId) {
        this.jSessionId = jSessionId;
    }

    public String getExpires() {
        return expires;
    }

    public void setExpires(String expires) {
        this.expires = expires;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public char getAuthCodeAccess() {
        return authCodeAccess;
    }

    public void setAuthCodeAccess(char authCodeAccess) {
        this.authCodeAccess = authCodeAccess;
    }

    public String getAmIdTOkenDefaultP2() {
        return amIdTOkenDefaultP2;
    }

    public void setAmIdTOkenDefaultP2(String amIdTOkenDefaultP2) {
        this.amIdTOkenDefaultP2 = amIdTOkenDefaultP2;
    }

    public String getAmIdTOkenDefaultP1() {
        return amIdTOkenDefaultP1;
    }

    public void setAmIdTOkenDefaultP1(String amIdTOkenDefaultP1) {
        this.amIdTOkenDefaultP1 = amIdTOkenDefaultP1;
    }
}
