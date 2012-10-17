/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.market.db.entity.controller;

import java.io.Serializable;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.EntityNotFoundException;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import ru.terra.market.db.entity.Product;
import ru.terra.market.db.entity.controller.exceptions.NonexistentEntityException;

/**
 * 
 * @author terranz
 */
public class ProductJpaController implements Serializable
{

	public ProductJpaController(EntityManagerFactory emf)
	{
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(Product product)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(product);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void edit(Product product) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			product = em.merge(product);
			em.getTransaction().commit();
		} catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = product.getId();
				if (findProduct(id) == null)
				{
					throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
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
			Product product;
			try
			{
				product = em.getReference(Product.class, id);
				product.getId();
			} catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
			}
			em.remove(product);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public List<Product> findProductEntities()
	{
		return findProductEntities(true, -1, -1);
	}

	public List<Product> findProductEntities(int maxResults, int firstResult)
	{
		return findProductEntities(false, maxResults, firstResult);
	}

	private List<Product> findProductEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Product.class));
			Query q = em.createQuery(cq);
			if (!all)
			{
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} catch (NoResultException e)
		{
			return null;
		} finally
		{
			em.close();
		}
	}

	public Product findProduct(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(Product.class, id);
		} catch (NoResultException e)
		{
			return null;
		} finally
		{
			em.close();
		}
	}

	public int getProductCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Product> rt = cq.from(Product.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally
		{
			em.close();
		}
	}

	public List<Product> findProductByCategory(Integer categoryId)
	{

		EntityManager em = getEntityManager();
		try
		{
			return em.createNamedQuery("Product.findByCategory").setParameter("category", categoryId).getResultList();
		} catch (NoResultException e)
		{
			return null;
		} finally
		{
			em.close();
		}
	}

}
