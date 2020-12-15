package com.nodj;

public class StationOverflowException extends Exception{
    StationOverflowException(){
        super("На автовокзале нет свободных мест");
    }
}
