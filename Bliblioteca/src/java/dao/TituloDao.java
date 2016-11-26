/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import model.Titulo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;


/**
 *
 * @author AlexSousa
 */
@ManagedBean
@ApplicationScoped
public class TituloDao implements Serializable{
    
    public List<Titulo> listaTodos() {
        
        List lista = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            lista = session.createQuery("from Titulo").list();
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
                
        return lista;
    }
    
    
    public void cadastra(Titulo titulo) {
        System.out.println("TESTE dao");
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            session.persist(titulo);
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
        
    }
    
    
    public List<Titulo> listaPorCodigo(Integer codigo) {
        
        List lista = new ArrayList<>();
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            Titulo titulo = (Titulo) session.get(Titulo.class, codigo);
            //Pessoa p = (Pessoa) session.get(Pessoa.class, codigo);
            
            if (titulo != null)
                lista.add(titulo);
            
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
                
        return lista;
    }
    
    
    public List<Titulo> listaPorTitulo(String tituloPesquisa) {
        
        List lista = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            Query query = session.createQuery("from Titulo where titulo like :titulo");
            query.setParameter("titulo", String.format("%%%s%%", tituloPesquisa));
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
    
    public List<Titulo> listaPorAutor(String autorPesquisa) {
        
        List lista = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            Query query = session.createQuery("from Titulo where autor like :autor");
            query.setParameter("autor", String.format("%%%s%%", autorPesquisa));
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
          
    
    public void atualiza(Titulo titulo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
 
        try {
            t = session.beginTransaction();
            session.merge(titulo);
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
        
    }
    
    
    public void remove(Titulo titulo) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
 
        try {
            t = session.beginTransaction();
            session.delete(titulo);
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
        
    }
    
    
    public Titulo buscaPorId(Integer id) {
        
        Titulo titulo = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            titulo = (Titulo) session.get(Titulo.class, id);
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
                
        return titulo;
    }

    /**
     * Creates a new instance of TituloDao
     */
    public TituloDao() {
    }
    
}
