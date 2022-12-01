package com.zupedu.bancodigital.application.autenticacao.input;

public class CredenciaisRequest {
    private String login;
    private String senha;

    public CredenciaisRequest(String login, String senha) {
        this.login = login;
        this.senha = senha;
    }

    public String getLogin() {
        return login;
    }

    public String getSenha() {
        return senha;
    }
}
