/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.market.db.controller;

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

import ru.terra.market.db.controller.exceptions.IllegalOrphanException;
import ru.terra.market.db.controller.exceptions.NonexistentEntityException;
import ru.terra.market.db.entity.Group;
import ru.terra.market.db.entity.Product;

/**
 * 
 * @author terranz
 */
public class CategoryJpaController implements Serializable {

	public CategoryJpaController(EntityManagerFactory emf) {
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public void create(Group category) {
		if (category.getProductList() == null) {
			category.setProductList(new ArrayList<Product>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			List<Product> attachedProductList = new ArrayList<Product>();
			for (Product productListProductToAttach : category.getProductList()) {
				productListProductToAttach = em.getReference(productListProductToAttach.getClass(), productListProductToAttach.getId());
				attachedProductList.add(productListProductToAttach);
			}
			category.setProductList(attachedProductList);
			em.persist(category);
			for (Product productListProduct : category.getProductList()) {
				Group oldCategoryOfProductListProduct = productListProduct.getGroup();
				productListProduct.setGroup(category);
				productListProduct = em.merge(productListProduct);
				if (oldCategoryOfProductListProduct != null) {
					oldCategoryOfProductListProduct.getProductList().remove(productListProduct);
					oldCategoryOfProductListProduct = em.merge(oldCategoryOfProductListProduct);
				}
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void create(List<Group> cats) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			for (Group category : cats) {
				if (category.getProductList() == null) {
					category.setProductList(new ArrayList<Product>());
				}
				List<Product> attachedProductList = new ArrayList<Product>();
				for (Product productListProductToAttach : category.getProductList()) {
					productListProductToAttach = em.getReference(productListProductToAttach.getClass(), productListProductToAttach.getId());
					attachedProductList.add(productListProductToAttach);
				}
				category.setProductList(attachedProductList);
				em.persist(category);
				for (Product productListProduct : category.getProductList()) {
					Group oldCategoryOfProductListProduct = productListProduct.getGroup();
					productListProduct.setGroup(category);
					productListProduct = em.merge(productListProduct);
					if (oldCategoryOfProductListProduct != null) {
						oldCategoryOfProductListProduct.getProductList().remove(productListProduct);
						oldCategoryOfProductListProduct = em.merge(oldCategoryOfProductListProduct);
					}
				}
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

	public void edit(Group category) throws IllegalOrphanException, NonexistentEntityException, Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Group persistentCategory = em.find(Group.class, category.getId());
			List<Product> productListOld = persistentCategory.getProductList();
			List<Product> productListNew = category.getProductList();
			List<String> illegalOrphanMessages = null;
			for (Product productListOldProduct : productListOld) {
				if (!productListNew.contains(productListOldProduct)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Product " + productListOldProduct + " since its category field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			List<Product> attachedProductListNew = new ArrayList<Product>();
			for (Product productListNewProductToAttach : productListNew) {
				productListNewProductToAttach = em.getReference(productListNewProductToAttach.getClass(), productListNewProductToAttach.getId());
				attachedProductListNew.add(productListNewProductToAttach);
			}
			productListNew = attachedProductListNew;
			category.setProductList(productListNew);
			category = em.merge(category);
			for (Product productListNewProduct : productListNew) {
				if (!productListOld.contains(productListNewProduct)) {
					Group oldCategoryOfProductListNewProduct = productListNewProduct.getGroup();
					productListNewProduct.setGroup(category);
					productListNewProduct = em.merge(productListNewProduct);
					if (oldCategoryOfProductListNewProduct != null && !oldCategoryOfProductListNewProduct.equals(category)) {
						oldCategoryOfProductListNewProduct.getProductList().remove(productListNewProduct);
						oldCategoryOfProductListNewProduct = em.merge(oldCategoryOfProductListNewProduct);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = category.getId();
				if (findCategory(id) == null) {
					throw new NonexistentEntityException("The category with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public void destroy(Integer id) throws IllegalOrphanException, NonexistentEntityException {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Group category;
			try {
				category = em.getReference(Group.class, id);
				category.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The category with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			List<Product> productListOrphanCheck = category.getProductList();
			for (Product productListOrphanCheckProduct : productListOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Category (" + category + ") cannot be destroyed since the Product " + productListOrphanCheckProduct
						+ " in its productList field has a non-nullable category field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			em.remove(category);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Group> findCategoryEntities() {
		return findCategoryEntities(true, -1, -1);
	}

	public List<Group> findCategoryEntities(int maxResults, int firstResult) {
		return findCategoryEntities(false, maxResults, firstResult);
	}

	private List<Group> findCategoryEntities(boolean all, int maxResults, int firstResult) {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(Group.class));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(maxResults);
				q.setFirstResult(firstResult);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	public Group findCategory(Integer id) {
		EntityManager em = getEntityManager();
		try {
			return em.find(Group.class, id);
		} finally {
			em.close();
		}
	}

	public int getCategoryCount() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<Group> rt = cq.from(Group.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}

	public List<Group> findCategoryByParent(Integer parentId) {
		EntityManager em = getEntityManager();
		try {
			return em.createNamedQuery("Group.findByParent").setParameter("parent", parentId).getResultList();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}

}
