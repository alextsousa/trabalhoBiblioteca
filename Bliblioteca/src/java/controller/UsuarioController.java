/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import dao.UsuarioDao;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Usuario;

/**
 *
 * @author AlexSousa
 */
@ManagedBean
@ViewScoped
public class UsuarioController implements Serializable {

    private List<Usuario> listaUsuarios;
    private Usuario usuario;
    private Usuario usuarioSelecionado;

    private Integer codPesquisa;
    private String nomePesquisa;
    private String emailPesquisa;
    
    private List<Usuario> listaUsuariosPesquisa;

    @ManagedProperty("#{usuarioDao}")
    private UsuarioDao usuarioDao;

    @PostConstruct
    public void init() {
        usuario = new Usuario();
        listaUsuarios= usuarioDao.listaTodos();
        listaUsuariosPesquisa = new ArrayList<>();
    }

    public void cadastra() {
        try {

            usuarioDao.cadastra(usuario);
            usuario = new Usuario();
            listaUsuarios.clear();
            listaUsuarios = usuarioDao.listaTodos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Info!", "Cadastro Realizado com sucesso!"));
        } catch (Exception e) {
        }

    }

    public void pesquisa() {
        
      
        if ((codPesquisa == null) && (nomePesquisa.isEmpty()) && (emailPesquisa.isEmpty())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "É necessário informar código, título ou autor!", null));
        } else if (codPesquisa != null) {
            listaUsuariosPesquisa = usuarioDao.listaPorCodigo(codPesquisa);
        } else if (!nomePesquisa.isEmpty()) {
            listaUsuariosPesquisa = usuarioDao.listaPorNome(nomePesquisa);
        } else if (!emailPesquisa.isEmpty()) {
            listaUsuariosPesquisa = usuarioDao.listaPorEmail(emailPesquisa);
        }

    }

    public void atualiza() {
        try {
            usuarioDao.atualiza(usuarioSelecionado);
            listaUsuarios.clear();
            listaUsuariosPesquisa.clear();
            listaUsuarios = usuarioDao.listaTodos();
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                            "Atualização registrada com sucesso!", null));
        } catch (Exception e) {
        }

    }

    public void remove() {
        try {
            usuarioDao.remove(usuarioSelecionado);
            listaUsuariosPesquisa.remove(usuarioSelecionado);
            listaUsuarios = usuarioDao.listaTodos();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Info!", "Registro removido com sucesso!"));
        } catch (Exception e) {
        }
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Usuario getUsuarioSelecionado() {
        return usuarioSelecionado;
    }

    public void setUsuarioSelecionado(Usuario usuarioSelecionado) {
        this.usuarioSelecionado = usuarioSelecionado;
    }

    public Integer getCodPesquisa() {
        return codPesquisa;
    }

    public void setCodPesquisa(Integer codPesquisa) {
        this.codPesquisa = codPesquisa;
    }

    public String getNomePesquisa() {
        return nomePesquisa;
    }

    public void setNomePesquisa(String nomePesquisa) {
        this.nomePesquisa = nomePesquisa;
    }

    public String getEmailPesquisa() {
        return emailPesquisa;
    }

    public void setEmailPesquisa(String emailPesquisa) {
        this.emailPesquisa = emailPesquisa;
    }

    public List<Usuario> getListaUsuariosPesquisa() {
        return listaUsuariosPesquisa;
    }

    public void setListaUsuariosPesquisa(List<Usuario> listaUsuariosPesquisa) {
        this.listaUsuariosPesquisa = listaUsuariosPesquisa;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    
}
