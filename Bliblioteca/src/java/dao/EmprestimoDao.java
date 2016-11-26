package dao;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ApplicationScoped;
import javax.faces.bean.ManagedBean;
import model.Emprestimo;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.Transaction;
import util.HibernateUtil;

@ManagedBean
@ApplicationScoped
public class EmprestimoDao implements Serializable {
    
    public List<Emprestimo> listaTodos() {
        
        List lista = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            lista = session.createQuery("from Emprestimo").list();
            t.commit();
        } catch (Exception e) {
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
        }
                
        return lista;
    }
    
    
    public boolean cadastra(List<Emprestimo> listamprestimos) {
        boolean status = false;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            
            for (Emprestimo e : listamprestimos) 
                
                session.persist(e);
            t.commit();
            status = true;
        } catch (Exception e) {
            status = false;
            if (t != null) t.rollback();
            throw e;
        } finally {
            session.close();
            
        }
        return status;
    }
    
    public List<Emprestimo> listaEmpPorData(Date dtInicial, Date dtFinal) {
        
        List lista = null;
        
        Session session = HibernateUtil.getSessionFactory().openSession();
        Transaction t = null;
        
        try {
            t = session.beginTransaction();
            Query query = session.createQuery("from Emprestimo where dataemp :dtIniial and :dtFinal");
            query.setParameter("dtIniial", String.format("%s", dtInicial));
            query.setParameter("dtFinal", String.format("%s", dtFinal));
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
