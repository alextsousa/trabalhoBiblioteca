package controller;

import dao.EmprestimoDao;
import dao.ExemplarDao;
import dao.TituloDao;
import dao.UsuarioDao;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Emprestimo;
import model.Exemplar;
import model.Usuario;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;

@ManagedBean
@ViewScoped
public class EmprestimoController implements Serializable {

    private Integer idUsuPesq;
    private String usuPesq;
    private String emailPesq;

    private Integer idExePesq;
    private String exePesq;

    private Integer idEmpPesq;
    private String titPesq;
    private Date dtInicial;
    private Date dtFinal;
    ;

    private Emprestimo emprestimo;
    private Exemplar exemplar;
    private Usuario usuario;

    private Exemplar exemSelecionado;
    private Emprestimo emprestimoSelecionado;

    private List<Emprestimo> listaEmprestimos;
    private List<Usuario> listaUsuarios;
    private List<Exemplar> listaExemplares;

    @ManagedProperty("#{usuarioDao}")
    private UsuarioDao usuarioDao;

    @ManagedProperty("#{exemplarDao}")
    private ExemplarDao exemplarDao;

    @ManagedProperty("#{emprestimoDao}")
    private EmprestimoDao emprestimoDao;

    @ManagedProperty("#{tituloDao}")
    private TituloDao tituloDao;

    private Integer tabAtiva = 0;

    @PostConstruct
    public void init() {
        listaEmprestimos = new ArrayList<>();

        usuario = new Usuario();
        exemplar = new Exemplar();

    }

    public void onDateSelect(SelectEvent event) {
        FacesContext facesContext = FacesContext.getCurrentInstance();
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Date Selected", format.format(event.getObject())));
    }

    public void pesquisaUsuario() {
        if ((idUsuPesq == null) && (usuPesq.isEmpty()) && (emailPesq.isEmpty())) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Verifique",
                            "É necessário informar código, nome ou email!"));
            if (listaUsuarios != null) {
                listaUsuarios.clear();
            }

        } else if (idUsuPesq != null) {
            listaUsuarios = usuarioDao.listaPorCodigo(idUsuPesq);
        } else if (!usuPesq.isEmpty()) {
            listaUsuarios = usuarioDao.listaPorNome(usuPesq);
        } else if (!emailPesq.isEmpty()) {
            listaUsuarios = usuarioDao.listaPorEmail(emailPesq);
        }
    }

    public void pesquisaExemplar() {

        if ((idExePesq == null) && (exePesq.isEmpty()) && emailPesq.isEmpty()) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Verifique",
                            "É necessário informar código ou título!"));

        } else if (idExePesq != null) {
            listaExemplares = exemplarDao.listaPorCodigo(idExePesq);
        } else if (!exePesq.isEmpty()) {

            listaExemplares = exemplarDao.listaExemPorTitulo(exePesq);

        }
    }

    public void finaliza() {

        if (listaEmprestimos.isEmpty()) {

            tabAtiva = 0;
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Verifique!",
                            "Preencha todas as etapas!"));
        } else {

            boolean finalizado = false;
            finalizado = emprestimoDao.cadastra(listaEmprestimos);
            if (finalizado) {
                emprestimo = new Emprestimo();
                usuario = new Usuario();
                exemSelecionado = new Exemplar();

                listaEmprestimos.clear();
                listaExemplares.clear();
                listaUsuarios.clear();

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso!",
                                "Emprestimo realizado com sucesso!"));
            } else {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "Verifique!",
                                "Emprestimo não realizado!"));
            }

        }

    }

    public void pesquisaEmprestimo() {
        System.out.println("idEm:" + idEmpPesq);
        System.out.println("titu" + titPesq);
        System.out.println("usu : " + usuPesq);
        
        /*
        if ((idEmpPesq == null) && (titPesq.isEmpty()) && usuPesq.isEmpty() && dtInicial == null && dtFinal == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Verifique",
                            "informa um filtro!"));

        } else if (idEmpPesq != null) {
            //listaEmprestimos = emprestimoDao.listaPorCodigo(idEmpPesq);
        } else if (!usuPesq.isEmpty()) {
            //listaEmprestimos = emprestimoDao.listaEmpPorUsuario(usuPesq);
        } else if (!titPesq.isEmpty()) {
            //listaEmprestimos = emprestimoDao.listaEmpPorTitulo(titPesq);
        } else if (dtInicial != null && dtFinal != null) {
            listaEmprestimos = emprestimoDao.listaEmpPorData(dtInicial, dtFinal);
        }*/
    }

    public void pag1() {
        tabAtiva = 1;
    }

    public void pag2() {
        tabAtiva = 2;
    }

    public void adicionaExempar() {

        if (usuario.getNome() == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, "Verifique! ",
                            "É necessário selecionar um Usuário!"));
            tabAtiva = 0;

        } else {

            if (listaEmprestimos.size() >= 5) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Verifique! ", "Você só pode selecionar 5 exemplares."));
                tabAtiva = 2;
                return;
            } else {
                for (Emprestimo e : listaEmprestimos) {
                    if (e.getExemplar().getId().equals(exemSelecionado.getId())) {
                        FacesContext.getCurrentInstance().addMessage(null,
                                new FacesMessage(FacesMessage.SEVERITY_ERROR, "Verifique! ",
                                        "Exemplar já selecionado ao emprestimo."));
                        return;
                    }
                }
                emprestimo = new Emprestimo();
                emprestimo.setDataemp(getDataHoje());
                emprestimo.setUsuario(usuario);
                emprestimo.setExemplar(exemSelecionado);

                listaEmprestimos.add(emprestimo);

                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Total Emprestimos: ",
                                listaEmprestimos.size() + " Exemplar(es) selecionado."));
            }
            pag1();
        }
    }

    public void removerExemplar() {
        int index = -1;
        for (Emprestimo e : listaEmprestimos) {
            index++;
            if (e.getExemplar().getId().equals(exemSelecionado.getId())) {
                listaEmprestimos.remove(index);
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "Sucesso! ",
                                "Registro removido com sucesso."));
                tabAtiva = 2;
                return;
            }
        }
    }

    public void onTabChange(TabChangeEvent event) {
        TabView tv = (TabView) event.getComponent();
        tabAtiva = tv.getActiveIndex();
    }

    public List<Exemplar> getListaExemplares() {
        return listaExemplares;
    }

    public void setListaExemplares(List<Exemplar> listaExemplares) {
        this.listaExemplares = listaExemplares;
    }

    public List<Usuario> getListaUsuarios() {
        return listaUsuarios;
    }

    public void setListaUsuarios(List<Usuario> listaUsuarios) {
        this.listaUsuarios = listaUsuarios;
    }

    public static Date getDataHoje() {
        return new Date();
    }

    public Emprestimo getEmprestimo() {
        return emprestimo;
    }

    public void setEmprestimo(Emprestimo emprestimo) {
        this.emprestimo = emprestimo;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public List<Emprestimo> getListaEmprestimos() {
        return listaEmprestimos;
    }

    public void setListaEmprestimos(List<Emprestimo> listaEmprestimos) {
        this.listaEmprestimos = listaEmprestimos;
    }

    public EmprestimoDao getEmprestimoDao() {
        return emprestimoDao;
    }

    public void setEmprestimoDao(EmprestimoDao emprestimoDao) {
        this.emprestimoDao = emprestimoDao;
    }

    public UsuarioDao getUsuarioDao() {
        return usuarioDao;
    }

    public void setUsuarioDao(UsuarioDao usuarioDao) {
        this.usuarioDao = usuarioDao;
    }

    public ExemplarDao getExemplarDao() {
        return exemplarDao;
    }

    public void setExemplarDao(ExemplarDao exemplarDao) {
        this.exemplarDao = exemplarDao;
    }

    public Integer getTabAtiva() {
        return tabAtiva;
    }

    public void setTabAtiva(Integer tabAtiva) {
        this.tabAtiva = tabAtiva;
    }

    public String getUsuPesq() {
        return usuPesq;
    }

    public void setUsuPesq(String usuPesq) {
        this.usuPesq = usuPesq;
    }

    public String getEmailPesq() {
        return emailPesq;
    }

    public void setEmailPesq(String emailPesq) {
        this.emailPesq = emailPesq;
    }

    public Integer getIdUsuPesq() {
        return idUsuPesq;
    }

    public void setIdUsuPesq(Integer idUsuPesq) {
        this.idUsuPesq = idUsuPesq;
    }

    public Integer getIdTitPesq() {
        return idExePesq;
    }

    public void setIdTitPesq(Integer idTitPesq) {
        this.idExePesq = idTitPesq;
    }

    public String getTitPesq() {
        return exePesq;
    }

    public void setTitPesq(String titPesq) {
        this.exePesq = titPesq;
    }

    public Integer getIdExePesq() {
        return idExePesq;
    }

    public void setIdExePesq(Integer idExePesq) {
        this.idExePesq = idExePesq;
    }

    public String getExePesq() {
        return exePesq;
    }

    public void setExePesq(String exePesq) {
        this.exePesq = exePesq;
    }

    public TituloDao getTituloDao() {
        return tituloDao;
    }

    public void setTituloDao(TituloDao tituloDao) {
        this.tituloDao = tituloDao;
    }

    public Exemplar getExemSelecionado() {
        return exemSelecionado;
    }

    public void setExemSelecionado(Exemplar exemSelecionado) {
        this.exemSelecionado = exemSelecionado;
    }

    public Emprestimo getEmprestimoSelecionado() {
        return emprestimoSelecionado;
    }

    public void setEmprestimoSelecionado(Emprestimo emprestimoSelecionado) {
        this.emprestimoSelecionado = emprestimoSelecionado;
    }

    public Integer getIdEmpPesq() {
        return idEmpPesq;
    }

    public void setIdEmpPesq(Integer idEmpPesq) {
        this.idEmpPesq = idEmpPesq;
    }

    public Date getDtInicial() {
        return dtInicial;
    }

    public void setDtInicial(Date dtInicial) {
        this.dtInicial = dtInicial;
    }

    public Date getDtFinal() {
        return dtFinal;
    }

    public void setDtFinal(Date dtFinal) {
        this.dtFinal = dtFinal;
    }

}
