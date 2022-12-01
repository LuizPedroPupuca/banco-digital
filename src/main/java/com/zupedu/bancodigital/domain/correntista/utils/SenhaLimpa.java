package com.zupedu.bancodigital.domain.correntista.utils;

import io.jsonwebtoken.lang.Assert;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class SenhaLimpa {
        private final String senhaLimpa;

        public SenhaLimpa(@NotBlank @Size(min = 6) String senhaLimpa) {
            this.senhaLimpa = senhaLimpa;
        }

        public String getSenhaEncodada() {
            return new BCryptPasswordEncoder().encode(this.senhaLimpa);
        }

        public int getTamanhoSenha() {
            return this.senhaLimpa.length();
        }
}
