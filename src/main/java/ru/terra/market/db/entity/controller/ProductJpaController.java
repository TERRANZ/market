/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.market.db.entity.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ru.terra.market.db.entity.Category;
import ru.terra.market.db.entity.Photo;
import ru.terra.market.db.entity.Product;
import ru.terra.market.db.entity.controller.exceptions.IllegalOrphanException;
import ru.terra.market.db.entity.controller.exceptions.NonexistentEntityException;
import ru.terra.market.db.entity.controller.exceptions.PreexistingEntityException;

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

	public void create(Product product) throws PreexistingEntityException, Exception
	{
		if (product.getPhotoList() == null)
		{
			product.setPhotoList(new ArrayList<Photo>());
		}
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
			List<Photo> attachedPhotoList = new ArrayList<Photo>();
			for (Photo photoListPhotoToAttach : product.getPhotoList())
			{
				photoListPhotoToAttach = em.getReference(photoListPhotoToAttach.getClass(), photoListPhotoToAttach.getId());
				attachedPhotoList.add(photoListPhotoToAttach);
			}
			product.setPhotoList(attachedPhotoList);
			em.persist(product);
			if (category != null)
			{
				category.getProductList().add(product);
				category = em.merge(category);
			}
			for (Photo photoListPhoto : product.getPhotoList())
			{
				Product oldProductIdOfPhotoListPhoto = photoListPhoto.getProduct();
				photoListPhoto.setProductId(product);
				photoListPhoto = em.merge(photoListPhoto);
				if (oldProductIdOfPhotoListPhoto != null)
				{
					oldProductIdOfPhotoListPhoto.getPhotoList().remove(photoListPhoto);
					oldProductIdOfPhotoListPhoto = em.merge(oldProductIdOfPhotoListPhoto);
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex)
		{
			if (findProduct(product.getId()) != null)
			{
				throw new PreexistingEntityException("Product " + product + " already exists.", ex);
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

	public void edit(Product product) throws IllegalOrphanException, NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			Product persistentProduct = em.find(Product.class, product.getId());
			Category categoryOld = persistentProduct.getCategory();
			Category categoryNew = product.getCategory();
			List<Photo> photoListOld = persistentProduct.getPhotoList();
			List<Photo> photoListNew = product.getPhotoList();
			List<String> illegalOrphanMessages = null;
			for (Photo photoListOldPhoto : photoListOld)
			{
				if (!photoListNew.contains(photoListOldPhoto))
				{
					if (illegalOrphanMessages == null)
					{
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Photo " + photoListOldPhoto + " since its productId field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null)
			{
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			if (categoryNew != null)
			{
				categoryNew = em.getReference(categoryNew.getClass(), categoryNew.getId());
				product.setCategory(categoryNew);
			}
			List<Photo> attachedPhotoListNew = new ArrayList<Photo>();
			for (Photo photoListNewPhotoToAttach : photoListNew)
			{
				photoListNewPhotoToAttach = em.getReference(photoListNewPhotoToAttach.getClass(), photoListNewPhotoToAttach.getId());
				attachedPhotoListNew.add(photoListNewPhotoToAttach);
			}
			photoListNew = attachedPhotoListNew;
			product.setPhotoList(photoListNew);
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
			for (Photo photoListNewPhoto : photoListNew)
			{
				if (!photoListOld.contains(photoListNewPhoto))
				{
					Product oldProductIdOfPhotoListNewPhoto = photoListNewPhoto.getProduct();
					photoListNewPhoto.setProductId(product);
					photoListNewPhoto = em.merge(photoListNewPhoto);
					if (oldProductIdOfPhotoListNewPhoto != null && !oldProductIdOfPhotoListNewPhoto.equals(product))
					{
						oldProductIdOfPhotoListNewPhoto.getPhotoList().remove(photoListNewPhoto);
						oldProductIdOfPhotoListNewPhoto = em.merge(oldProductIdOfPhotoListNewPhoto);
					}
				}
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

	public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException
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
			List<String> illegalOrphanMessages = null;
			List<Photo> photoListOrphanCheck = product.getPhotoList();
			for (Photo photoListOrphanCheckPhoto : photoListOrphanCheck)
			{
				if (illegalOrphanMessages == null)
				{
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Photo " + photoListOrphanCheckPhoto
						+ " in its photoList field has a non-nullable productId field.");
			}
			if (illegalOrphanMessages != null)
			{
				throw new IllegalOrphanException(illegalOrphanMessages);
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

	public Long getProductCount(Category cat)
	{
		EntityManager em = getEntityManager();
		try
		{
			Query q = em.createNativeQuery("select count(id) from product where category = " + cat.getId());
			return (Long) q.getSingleResult();
		} finally
		{
			em.close();
		}
	}

}
