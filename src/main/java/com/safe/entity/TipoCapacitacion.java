/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.safe.entity;

/**
 *
 * @author familia
 */
public class TipoCapacitacion {
    private long idtipocap;
    private String descripcap;
    private long estado;
    
    @Override
    public String toString(){
        return descripcap;
    }
    
    public long getIdtipocap() {
        return idtipocap;
    }

    public void setIdtipocap(long idtipocap) {
        this.idtipocap = idtipocap;
    }

    public String getDescripcap() {
        return descripcap;
    }

    public void setDescripcap(String descripcap) {
        this.descripcap = descripcap;
    }

    public long getEstado() {
        return estado;
    }

    public void setEstado(long estado) {
        this.estado = estado;
    }
    
    public int getEstadoIndex() {
        return (int)estado - 1;
    }

    public void setEstadoIndex(int estado) {
        this.estado = estado + 1;
    }
    
    
}
