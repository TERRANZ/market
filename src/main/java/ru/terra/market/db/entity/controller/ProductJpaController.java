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
			Category category = product.getCategory();
			if (category != null)
			{
				category = em.getReference(category.getClass(), category.getId());
				product.setCategory(category);
			}
			em.persist(product);
			if (category != null)
			{
				category.getProductList().add(product);
				category = em.merge(category);
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

	public void create(List<Product> prods)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			for (Product product : prods)
			{
				Category category = product.getCategory();
				if (category != null)
				{
					category = em.getReference(category.getClass(), category.getId());
					product.setCategory(category);
				}
				em.persist(product);
				if (category != null)
				{
					category.getProductList().add(product);
					category = em.merge(category);
				}
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

	public void edit(Product product) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Product persistentProduct = em.find(Product.class, product.getId());
			Category categoryOld = persistentProduct.getCategory();
			Category categoryNew = product.getCategory();
			if (categoryNew != null)
			{
				categoryNew = em.getReference(categoryNew.getClass(), categoryNew.getId());
				product.setCategory(categoryNew);
			}
			product = em.merge(product);
			if (categoryOld != null && !categoryOld.equals(categoryNew))
			{
				categoryOld.getProductList().remove(product);
				categoryOld = em.merge(categoryOld);
			}
			if (categoryNew != null && !categoryNew.equals(categoryOld))
			{
				categoryNew.getProductList().add(product);
				categoryNew = em.merge(categoryNew);
			}
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
			Category category = product.getCategory();
			if (category != null)
			{
				category.getProductList().remove(product);
				category = em.merge(category);
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
		return null;
	}

	public List<Product> findProductByCategory(Category cat, Integer lim)
	{
		EntityManager em = getEntityManager();
		try
		{
			Query q = em.createNamedQuery("Product.findByCategory").setParameter("category", cat);
			if (lim != -1)
			{
				q.setFirstResult(0);
				q.setMaxResults(lim);
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

}
