package com.nodj;

public class StationNotFoundException extends Exception{
    StationNotFoundException(int i){
        super("Не найден автобус по месту "+ i);
    }
}
