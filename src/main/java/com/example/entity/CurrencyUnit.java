package com.example.entity;

import java.util.Random;

public enum CurrencyUnit {

    RUB, USD, EUR;

    public static CurrencyUnit getRundom() {

        int random = new Random().nextInt(1, 4);

        switch (random) {
            case (1) -> {
                return CurrencyUnit.RUB;
            }
            case (2) -> {
                return CurrencyUnit.EUR;
            }
            case (3) -> {
                return CurrencyUnit.USD;
            }
        }
        return CurrencyUnit.USD;
    }

}
