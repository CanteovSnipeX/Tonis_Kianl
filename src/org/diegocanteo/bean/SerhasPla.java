
package org.diegocanteo.bean;


public class SerhasPla {
    
    private int codigoServicio;
    private int codigoPlatos;

    public SerhasPla() {
    }

    public SerhasPla(int codigoServicio, int codigoPlatos) {
        this.codigoServicio = codigoServicio;
        this.codigoPlatos = codigoPlatos;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public int getCodigoPlatos() {
        return codigoPlatos;
    }

    public void setCodigoPlatos(int codigoPlatos) {
        this.codigoPlatos = codigoPlatos;
    }

    
}
