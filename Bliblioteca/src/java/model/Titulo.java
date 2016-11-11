/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.Length;




/**
 *
 * @author AlexSousa
 */
@Entity
@Table(name="titulos")
public class Titulo implements Serializable{
    
    //@OneToMany(mappedBy = "exemplares")
    //public Set<Exemplar> exemplares;
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    
    @Column(name="titulo")
    @NotNull(message="Campo 'Título' é obrigatório.")
    @Length(min=5, max=50, message="titulo deve conter entre {min} e {max} caracteres.")
    private String titulo;
    
    @Column(name="autor")
    @NotNull(message="Campo 'autor' é obrigatório.")
    @Length(min=5, max=50, message="autor deve conter entre {min} e {max} caracteres.")
    private String autor;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitulo() {
        return titulo;
    }

    public void setTitulo(String titulo) {
        this.titulo = titulo;
    }

    public String getAutor() {
        return autor;
    }

    public void setAutor(String autor) {
        this.autor = autor;
    }
    
    
    
    
}
