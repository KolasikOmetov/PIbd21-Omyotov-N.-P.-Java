package com.nodj;

public class StationAlreadyHaveException extends Exception{
    StationAlreadyHaveException(){
        super("На автовокзале уже есть такой автобус");
    };
}
