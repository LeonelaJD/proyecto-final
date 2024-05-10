package com.jimenez.app.services;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class Test {
    void test() throws IOException {
        BufferedReader br = new BufferedReader(
                new InputStreamReader(System.in));
        String entrada = br.readLine();
        String[] array = entrada.split(",");
        List<Integer> lista = new ArrayList<>();
        for(int i = 0; i < array.length; i++){
            lista.add(Integer.parseInt(array[i]));
        }
    }
}
