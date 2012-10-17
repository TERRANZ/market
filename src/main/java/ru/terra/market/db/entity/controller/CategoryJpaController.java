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
import ru.terra.market.db.entity.Category;
import ru.terra.market.db.entity.controller.exceptions.NonexistentEntityException;

/**
 * 
 * @author terranz
 */
public class CategoryJpaController implements Serializable
{

	public CategoryJpaController(EntityManagerFactory emf)
	{
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(Category category)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(category);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void edit(Category category) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			category = em.merge(category);
			em.getTransaction().commit();
		} catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = category.getId();
				if (findCategory(id) == null)
				{
					throw new NonexistentEntityException("The category with id " + id + " no longer exists.");
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
			Category category;
			try
			{
				category = em.getReference(Category.class, id);
				category.getId();
			} catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The category with id " + id + " no longer exists.", enfe);
			}
			em.remove(category);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public List<Category> findCategoryEntities()
	{
		return findCategoryEntities(true, -1, -1);
	}

	public List<Category> findCategoryEntities(int maxResults, int firstResult)
	{
		return findCategoryEntities(false, maxResults, firstResult);
	}

	private List<Category> findCategoryEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Category.class));
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

	public Category findCategory(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(Category.class, id);
		} catch (NoResultException e)
		{
			return null;
		} finally
		{
			em.close();
		}
	}

	public int getCategoryCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Category> rt = cq.from(Category.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally
		{
			em.close();
		}
	}

	public List<Category> findCategoryByParent(Integer parentId)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.createNamedQuery("Category.findByParent").setParameter("parent", parentId).getResultList();
		} catch (NoResultException e)
		{
			return null;
		} finally
		{
			em.close();
		}
	}

}
