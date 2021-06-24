/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.diegocanteo.bean;

/**
 *
 * @author DiegoCanteo
 */
public class ProhasPla {
   private int codigoPlatos;
   private int codigoProducto;

    public ProhasPla() {
    }

    public ProhasPla(int codigoPlatos, int codigoProducto) {
        this.codigoPlatos = codigoPlatos;
        this.codigoProducto = codigoProducto;
    }

    public int getCodigoPlatos() {
        return codigoPlatos;
    }

    public void setCodigoPlatos(int codigoPlatos) {
        this.codigoPlatos = codigoPlatos;
    }

    public int getCodigoProducto() {
        return codigoProducto;
    }

    public void setCodigoProducto(int codigoProducto) {
        this.codigoProducto = codigoProducto;
    }

   
   
   
   
   
}
