/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.market.db.entity.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.terra.market.db.entity.Settings;
import ru.terra.market.db.entity.controller.exceptions.NonexistentEntityException;
import ru.terra.market.db.entity.controller.exceptions.PreexistingEntityException;

/**
 *
 * @author terranz
 */
public class SettingsJpaController implements Serializable
{

    public SettingsJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Settings settings) throws PreexistingEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            em.persist(settings);
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            if (findSettings(settings.getKey()) != null)
            {
                throw new PreexistingEntityException("Settings " + settings + " already exists.", ex);
            }
            throw ex;
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void edit(Settings settings) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            settings = em.merge(settings);
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                String id = settings.getKey();
                if (findSettings(id) == null)
                {
                    throw new NonexistentEntityException("The settings with id " + id + " no longer exists.");
                }
            }
            throw ex;
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void destroy(String id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Settings settings;
            try
            {
                settings = em.getReference(Settings.class, id);
                settings.getKey();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The settings with id " + id + " no longer exists.", enfe);
            }
            em.remove(settings);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Settings> findSettingsEntities()
    {
        return findSettingsEntities(true, -1, -1);
    }

    public List<Settings> findSettingsEntities(int maxResults, int firstResult)
    {
        return findSettingsEntities(false, maxResults, firstResult);
    }

    private List<Settings> findSettingsEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Settings.class));
            Query q = em.createQuery(cq);
            if (!all)
            {
                q.setMaxResults(maxResults);
                q.setFirstResult(firstResult);
            }
            return q.getResultList();
        } finally
        {
            em.close();
        }
    }

    public Settings findSettings(String id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Settings.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getSettingsCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Settings> rt = cq.from(Settings.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }
    
}
