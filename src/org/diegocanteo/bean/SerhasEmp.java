
package org.diegocanteo.bean;

import java.util.Date;


public class SerhasEmp {
    private int codigoServicioshasEmpleado;
    private Date fechaEvento;
    private String horaEvento;
    private String lugarEvento;
    private int codigoServicio;
    private int codigoEmpleado;

    public SerhasEmp() {
    }

    public SerhasEmp(int codigoServicioshasEmpleado, Date fechaEvento, String horaEvento, String lugarEvento, int codigoServicio, int codigoEmpleado) {
        this.codigoServicioshasEmpleado = codigoServicioshasEmpleado;
        this.fechaEvento = fechaEvento;
        this.horaEvento = horaEvento;
        this.lugarEvento = lugarEvento;
        this.codigoServicio = codigoServicio;
        this.codigoEmpleado = codigoEmpleado;
    }

    public int getCodigoServicioshasEmpleado() {
        return codigoServicioshasEmpleado;
    }

    public void setCodigoServicioshasEmpleado(int codigoServicioshasEmpleado) {
        this.codigoServicioshasEmpleado = codigoServicioshasEmpleado;
    }

    public Date getFechaEvento() {
        return fechaEvento;
    }

    public void setFechaEvento(Date fechaEvento) {
        this.fechaEvento = fechaEvento;
    }

    public String getHoraEvento() {
        return horaEvento;
    }

    public void setHoraEvento(String horaEvento) {
        this.horaEvento = horaEvento;
    }

    public String getLugarEvento() {
        return lugarEvento;
    }

    public void setLugarEvento(String lugarEvento) {
        this.lugarEvento = lugarEvento;
    }

    public int getCodigoServicio() {
        return codigoServicio;
    }

    public void setCodigoServicio(int codigoServicio) {
        this.codigoServicio = codigoServicio;
    }

    public int getCodigoEmpleado() {
        return codigoEmpleado;
    }

    public void setCodigoEmpleado(int codigoEmpleado) {
        this.codigoEmpleado = codigoEmpleado;
    }
    
    
      
    
    
}


