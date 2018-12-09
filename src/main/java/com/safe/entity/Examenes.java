
package com.safe.entity;

import java.io.Serializable;

/**
 *
 * @author roderick
 */
public class Examenes implements Serializable {
    private long idexamen;
    private String nombreexamen;
    private long estadoexamen;
    private long plansaludidplansalud;
    private long tipoexamenidtipoexam;

    public long getIdexamen() {
        return idexamen;
    }

    public void setIdexamen(long idexamen) {
        this.idexamen = idexamen;
    }

    public String getNombreexamen() {
        return nombreexamen;
    }

    public void setNombreexamen(String nombreexamen) {
        this.nombreexamen = nombreexamen;
    }

    public long getEstadoexamen() {
        return estadoexamen;
    }

    public void setEstadoexamen(long estadoexamen) {
        this.estadoexamen = estadoexamen;
    }

    public long getPlansaludidplansalud() {
        return plansaludidplansalud;
    }

    public void setPlansaludidplansalud(long plansaludidplansalud) {
        this.plansaludidplansalud = plansaludidplansalud;
    }

    public long getTipoexamenidtipoexam() {
        return tipoexamenidtipoexam;
    }

    public void setTipoexamenidtipoexam(long tipoexamenidtipoexam) {
        this.tipoexamenidtipoexam = tipoexamenidtipoexam;
    }

    @Override
    public String toString() {
        return "Examenes{" + "idexamen=" + idexamen + ", nombreexamen=" + nombreexamen + ", estadoexamen=" + estadoexamen + ", plansaludidplansalud=" + plansaludidplansalud + ", tipoexamenidtipoexam=" + tipoexamenidtipoexam + '}';
    }        
}
