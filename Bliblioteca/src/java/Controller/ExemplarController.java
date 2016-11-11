package Controller;

import dao.ExemplarDao;
import dao.TituloDao;
import javax.annotation.PostConstruct;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ManagedProperty;
import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import model.Exemplar;
import model.Titulo;

@ManagedBean
@ViewScoped
public class ExemplarController implements Serializable {

    private Integer tituloIdPes;
    private boolean status;
    private Date dataCad;

    private Exemplar exemplar;
    private Titulo titulo;

    private List<Exemplar> listaExemplares;

    @ManagedProperty("#{tituloDao}")
    private TituloDao tituloDao;

    @ManagedProperty("#{exemplarDao}")
    private ExemplarDao exemplarDao;

    @PostConstruct
    public void init() {
        exemplar = new Exemplar();
        exemplar.setDataCad(getDataHoje());
        titulo = new Titulo();

        listaExemplares = exemplarDao.listaTodos();
    }

    public void cadastra() {

        if (titulo == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "É necessário selecionar um Título!", null));

            return;
        }

        // Seta a categoria do produto
        exemplar.setTituloId(tituloIdPes);

        exemplarDao.cadastra(exemplar);

        // Reseta o modelo e a lista
        exemplar = new Exemplar();
        listaExemplares = exemplarDao.listaTodos();
    }

    public void pesquisaTitulo() {

        if (tituloIdPes == null) {
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_ERROR,
                            "É necessário informar o código do título", null));
        } else {
            titulo = tituloDao.buscaPorId(tituloIdPes);

            if (titulo == null) {
                FacesContext.getCurrentInstance().addMessage(null,
                        new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Título não encontrado!", null));
            }
        }

    }
    
    public static Date getDataHoje() {
        return new Date();
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

    public Integer getTituloIdPes() {
        return tituloIdPes;
    }

    public void setTituloIdPes(Integer tituloIdPes) {
        this.tituloIdPes = tituloIdPes;
    }

    public Exemplar getExemplar() {
        return exemplar;
    }

    public void setExemplar(Exemplar exemplar) {
        this.exemplar = exemplar;
    }

    public Titulo getTitulo() {
        return titulo;
    }

    public void setTitulo(Titulo titulo) {
        this.titulo = titulo;
    }

    public List<Exemplar> getListaExemplares() {
        return listaExemplares;
    }

    public void setListaExemplares(List<Exemplar> listaExemplares) {
        this.listaExemplares = listaExemplares;
    }

    public TituloDao getTituloDao() {
        return tituloDao;
    }

    public void setTituloDao(TituloDao tituloDao) {
        this.tituloDao = tituloDao;
    }

    public ExemplarDao getExemplarDao() {
        return exemplarDao;
    }

    public void setExemplarDao(ExemplarDao exemplarDao) {
        this.exemplarDao = exemplarDao;
    }

}
