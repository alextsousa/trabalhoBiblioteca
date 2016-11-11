/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.validation.constraints.NotNull;

/**
 *
 * @author AlexSousa
 */
@Entity
@Table(name="exemplares")
public class Exemplar implements Serializable {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="status")
    private boolean status;
    
    @Column(name="dataCad")
    @Temporal(javax.persistence.TemporalType.DATE)
    @NotNull(message="Campo 'Data de Cadastro' é obrigatório.")
    private Date dataCad;
    
    @ManyToOne
    @JoinColumn(name = "titulo_id")
    private int tituloId;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public Date getDataCad() {
        return dataCad;
    }

    public void setDataCad(Date dataCad) {
        this.dataCad = dataCad;
    }

    public int getTituloId() {
        return tituloId;
    }

    public void setTituloId(int tituloId) {
        this.tituloId = tituloId;
    }
     
    
}
