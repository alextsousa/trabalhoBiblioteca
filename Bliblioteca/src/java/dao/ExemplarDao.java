package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import model.Exemplar;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class ExemplarDao implements Serializable {
    
    public List<Exemplar> listaTodos() {
        
        List lista = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            lista = session.createQuery("from Exemplar").list();
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
                
        return lista;
    }
    
    
    public void cadastra(Exemplar exemplar) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
 
        try {
            t = session.beginTransaction();
            session.persist(exemplar);
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
        
    }
    
    public List<Exemplar> listaPorCodigo(Integer codigo) {
        
        List lista = new ArrayList<>();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            Exemplar exemplar = (Exemplar) session.get(Exemplar.class, codigo);
            //Pessoa p = (Pessoa) session.get(Pessoa.class, codigo);
            
            if (exemplar != null)
                lista.add(exemplar);
            
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
                
        return lista;
    }
    
    public List<Exemplar> listaExemPorTitulo(String tituloPesquisa) {
        
        List lista = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            Query query = session.createQuery("from Exemplar where titulo.titulo like :t");
            query.setParameter("t", String.format("%%%s%%", tituloPesquisa));
            lista = query.list();
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
                
        return lista;
    }
    
}
