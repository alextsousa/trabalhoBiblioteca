/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.TituloDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.context.FacesContext;
import model.Titulo;

/**
 *
 * @author AlexSousa
 */
@ManagedBean
@ApplicationScoped
public class TituloController implements Serializable {

    private List<Titulo> listaTitulos;
    private Titulo titulo;
    private Titulo tituloSelecionado;

    private Integer codPesquisa;
    private String autorPesquisa;
    private String tituloPesquisa;
    
    private List<Titulo> listaTitulosPesquisa;

    @ManagedProperty("#{tituloDao}")
    private TituloDao tituloDao;

    @PostConstruct
    public void init() {
        titulo = new Titulo();
        listaTitulos = tituloDao.listaTodos();
        listaTitulosPesquisa = new ArrayList<>();
    }

    public void cadastraTitulo() {
        try {

            tituloDao.cadastra(titulo);
            titulo = new Titulo();
            listaTitulos.clear();
            listaTitulos = tituloDao.listaTodos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Info!", "Cadastro Realizado com sucesso!"));
        } catch (Exception e) {
        }

    }

    public void pesquisaTitulo() {
        
      
        if ((codPesquisa == null) && (tituloPesquisa.isEmpty()) && (autorPesquisa.isEmpty())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "É necessário informar código, título ou autor!", null));
        } else if (codPesquisa != null) {
            listaTitulosPesquisa = tituloDao.listaPorCodigo(codPesquisa);
        } else if (!tituloPesquisa.isEmpty()) {
            listaTitulosPesquisa = tituloDao.listaPorTitulo(tituloPesquisa);
        } else if (!autorPesquisa.isEmpty()) {
            listaTitulosPesquisa = tituloDao.listaPorAutor(autorPesquisa);
        }

    }

    public void atualizaTitulo() {
        try {
            System.out.println("atualiza");
            tituloDao.atualiza(tituloSelecionado);
            listaTitulos.clear();
            listaTitulosPesquisa.clear();
            listaTitulos = tituloDao.listaTodos();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Atualização registrada com sucesso!", null));
        } catch (Exception e) {
        }

    }

    public void removeTitulo() {
        try {
            tituloDao.remove(tituloSelecionado);
            listaTitulosPesquisa.remove(tituloSelecionado);
            listaTitulos = tituloDao.listaTodos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Info!", "Registro removido com sucesso!"));
        } catch (Exception e) {
        }
    }

    public List<Titulo> getListaTitulos() {
        return listaTitulos;
    }

    public void setListaTitulos(List<Titulo> listaTitulos) {
        this.listaTitulos = listaTitulos;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public Titulo getTituloSelecionado() {
        return tituloSelecionado;
    }

    public void setTituloSelecionado(Titulo tituloSelecionado) {
        this.tituloSelecionado = tituloSelecionado;
    }

    public Integer getCodPesquisa() {
        return codPesquisa;
    }

    public void setCodPesquisa(Integer codPesquisa) {
        this.codPesquisa = codPesquisa;
    }

    public String getAutorPesquisa() {
        return autorPesquisa;
    }

    public void setAutorPesquisa(String autorPesquisa) {
        this.autorPesquisa = autorPesquisa;
    }

    public String getTituloPesquisa() {
        return tituloPesquisa;
    }

    public void setTituloPesquisa(String tituloPesquisa) {
        this.tituloPesquisa = tituloPesquisa;
    }

    public List<Titulo> getListaTitulosPesquisa() {
        return listaTitulosPesquisa;
    }

    public void setListaTitulosPesquisa(List<Titulo> listaTitulosPesquisa) {
        this.listaTitulosPesquisa = listaTitulosPesquisa;
    }

    public TituloDao getTituloDao() {
        return tituloDao;
    }

    public void setTituloDao(TituloDao tituloDao) {
        this.tituloDao = tituloDao;
    }

}
