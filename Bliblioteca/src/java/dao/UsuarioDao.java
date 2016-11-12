/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.util.ArrayList;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import model.Usuario;
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
public class UsuarioDao {

    public List<Usuario> listaTodos() {

        List lista = null;

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;

        try {
            t = session.beginTransaction();
            lista = session.createQuery("from Usuario").list();
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

        return lista;
    }

    public void cadastra(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;

        try {
            t = session.beginTransaction();
            session.persist(usuario);
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

    }

    public List<Usuario> listaPorCodigo(Integer codigo) {

        List lista = new ArrayList<>();

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;

        try {
            t = session.beginTransaction();
            Usuario usuario = (Usuario) session.get(Usuario.class, codigo);

            if (usuario != null) {
                lista.add(usuario);
            }

            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

        return lista;
    }

    public List<Usuario> listaPorNome(String nomePesquisa) {

        List lista = null;

        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;

        try {
            t = session.beginTransaction();
            Query query = session.createQuery("from Usuario where nome like :nome");
            query.setParameter("nome", String.format("%%%s%%", nomePesquisa));
            lista = query.list();
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

        return lista;
    }
    
    
     public List<Usuario> listaPorEmail(String emailPesquisa) {
        
        List lista = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            Query query = session.createQuery("from Usuario where nome like :email");
            query.setParameter("email", String.format("%%%s%%", emailPesquisa));
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

    public void atualiza(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;

        try {
            t = session.beginTransaction();
            session.merge(usuario);
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

    }

    public void remove(Usuario usuario) {
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;

        try {
            t = session.beginTransaction();
            session.delete(usuario);
            t.commit();
        } catch (Exception e) {
            if (t != null) {
                t.rollback();
            }
            throw e;
        } finally {
            session.close();
        }

    }

}
