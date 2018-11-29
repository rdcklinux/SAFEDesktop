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
public class TipoExamen {
    private long idtipoexam;
    private String descrip_exam;
    private long estado;

    public long getIdtipoexam() {
        return idtipoexam;
    }

    public void setIdtipoexam(long idtipoexam) {
        this.idtipoexam = idtipoexam;
    }

    public String getDescrip_exam() {
        return descrip_exam;
    }

    public void setDescrip_exam(String descrip_exam) {
        this.descrip_exam = descrip_exam;
    }

    public long getEstado() {
        return estado;
    }

    public void setEstado(long estado) {
        this.estado = estado;
    }

    
    
}
