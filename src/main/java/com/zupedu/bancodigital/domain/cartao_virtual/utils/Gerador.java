package com.zupedu.bancodigital.domain.cartao_virtual.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.Random;


public class Gerador {

    public static String geraNumero(){
        Random random = new Random();
        String numero= "";
        for(int cont = 0; cont <=15; cont++){
            numero += random.nextInt(9);
            if(cont == 3 || cont == 7 || cont == 11){
                numero += "-";
            }
        }
        return numero;
    }

    public static String geraCV(){
        Random random = new Random();
        String numero= "";
        for(int cont = 0; cont <=2; cont++){
            numero += random.nextInt(9);
        }
        return numero;
    }

    public static String numeroBoleto(){
        Random random = new Random();
        String numero= "";
        for(int cont = 0; cont <=25; cont++){
            numero += random.nextInt(9);
        }
        return numero;
    }

    public static BigDecimal valorBoleto(){
        Random random = new Random();
        return BigDecimal.valueOf(random.nextDouble()*20);
    }
}
