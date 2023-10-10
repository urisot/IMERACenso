package com.imera.censo.Contracts.Models;

public class Item_Menu {
    public String Texto;    //Texto del Botón del Menú
    public int Id_Icono;    //Icono del Botón del Menú

    public Item_Menu(String texto, int icono){
        this.Texto = texto;
        this.Id_Icono = icono;
    }
}
