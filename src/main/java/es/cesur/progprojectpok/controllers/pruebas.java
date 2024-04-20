package es.cesur.progprojectpok.controllers;

import java.io.File;

public class pruebas {

    public static void main(String[] args) {


        File archivo = new File(("/images/pokemons/arbok-front.png"));
        String rutaAbsoluta = archivo.getAbsolutePath();


       String aaa = archivo.toString();

        System.out.println(aaa);


    }
}
