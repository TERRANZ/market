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
import ru.terra.market.db.entity.Photo;
import ru.terra.market.db.entity.Product;
import ru.terra.market.db.entity.controller.exceptions.NonexistentEntityException;

/**
 *
 * @author terranz
 */
public class PhotoJpaController implements Serializable
{

    public PhotoJpaController(EntityManagerFactory emf)
    {
        this.emf = emf;
    }
    private EntityManagerFactory emf = null;

    public EntityManager getEntityManager()
    {
        return emf.createEntityManager();
    }

    public void create(Photo photo)
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Product productId = photo.getProduct();
            if (productId != null)
            {
                productId = em.getReference(productId.getClass(), productId.getId());
                photo.setProductId(productId);
            }
            em.persist(photo);
            if (productId != null)
            {
                productId.getPhotoList().add(photo);
                productId = em.merge(productId);
            }
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public void edit(Photo photo) throws NonexistentEntityException, Exception
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Photo persistentPhoto = em.find(Photo.class, photo.getId());
            Product productIdOld = persistentPhoto.getProduct();
            Product productIdNew = photo.getProduct();
            if (productIdNew != null)
            {
                productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
                photo.setProductId(productIdNew);
            }
            photo = em.merge(photo);
            if (productIdOld != null && !productIdOld.equals(productIdNew))
            {
                productIdOld.getPhotoList().remove(photo);
                productIdOld = em.merge(productIdOld);
            }
            if (productIdNew != null && !productIdNew.equals(productIdOld))
            {
                productIdNew.getPhotoList().add(photo);
                productIdNew = em.merge(productIdNew);
            }
            em.getTransaction().commit();
        } catch (Exception ex)
        {
            String msg = ex.getLocalizedMessage();
            if (msg == null || msg.length() == 0)
            {
                Integer id = photo.getId();
                if (findPhoto(id) == null)
                {
                    throw new NonexistentEntityException("The photo with id " + id + " no longer exists.");
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

    public void destroy(Integer id) throws NonexistentEntityException
    {
        EntityManager em = null;
        try
        {
            em = getEntityManager();
            em.getTransaction().begin();
            Photo photo;
            try
            {
                photo = em.getReference(Photo.class, id);
                photo.getId();
            } catch (EntityNotFoundException enfe)
            {
                throw new NonexistentEntityException("The photo with id " + id + " no longer exists.", enfe);
            }
            Product productId = photo.getProduct();
            if (productId != null)
            {
                productId.getPhotoList().remove(photo);
                productId = em.merge(productId);
            }
            em.remove(photo);
            em.getTransaction().commit();
        } finally
        {
            if (em != null)
            {
                em.close();
            }
        }
    }

    public List<Photo> findPhotoEntities()
    {
        return findPhotoEntities(true, -1, -1);
    }

    public List<Photo> findPhotoEntities(int maxResults, int firstResult)
    {
        return findPhotoEntities(false, maxResults, firstResult);
    }

    private List<Photo> findPhotoEntities(boolean all, int maxResults, int firstResult)
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            cq.select(cq.from(Photo.class));
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

    public Photo findPhoto(Integer id)
    {
        EntityManager em = getEntityManager();
        try
        {
            return em.find(Photo.class, id);
        } finally
        {
            em.close();
        }
    }

    public int getPhotoCount()
    {
        EntityManager em = getEntityManager();
        try
        {
            CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
            Root<Photo> rt = cq.from(Photo.class);
            cq.select(em.getCriteriaBuilder().count(rt));
            Query q = em.createQuery(cq);
            return ((Long) q.getSingleResult()).intValue();
        } finally
        {
            em.close();
        }
    }
    
}
