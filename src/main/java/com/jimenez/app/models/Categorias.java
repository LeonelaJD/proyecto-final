package com.jimenez.app.models;

public enum Categorias {

    ARTE(21),
    PAPELERIA(22),
    OFICINA(23),
    ESCOLAR(24),
    ZONAGAMERS(25);

    private int id;

    Categorias(int id){
         this.id = id;
     }

    public int getId() {
        return id;
    }
}
