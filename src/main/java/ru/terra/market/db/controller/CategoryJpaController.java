package ru.terra.market.db.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;

import ru.terra.market.core.AbstractJpaController;
import ru.terra.market.db.controller.exceptions.IllegalOrphanException;
import ru.terra.market.db.controller.exceptions.NonexistentEntityException;
import ru.terra.market.db.entity.Group;
import ru.terra.market.db.entity.Product;

/**
 * 
 * @author terranz
 */
public class CategoryJpaController extends AbstractJpaController<Group> implements Serializable {

	private static final long serialVersionUID = 590457729913843210L;

	public CategoryJpaController() {
		super(Group.class);
	}

	@Override
	public void create(Group entity) throws Exception {
		if (entity.getProductList() == null) {
			entity.setProductList(new ArrayList<Product>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			List<Product> attachedProductList = new ArrayList<Product>();
			for (Product productListProductToAttach : entity.getProductList()) {
				productListProductToAttach = em.getReference(productListProductToAttach.getClass(), productListProductToAttach.getId());
				attachedProductList.add(productListProductToAttach);
			}
			entity.setProductList(attachedProductList);
			em.persist(entity);
			for (Product productListProduct : entity.getProductList()) {
				Group oldCategoryOfProductListProduct = productListProduct.getGroup();
				productListProduct.setGroup(entity);
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

	@Override
	public void delete(Integer id) throws Exception {
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

	@Override
	public void update(Group entity) throws Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Group persistentCategory = em.find(Group.class, entity.getId());
			List<Product> productListOld = persistentCategory.getProductList();
			List<Product> productListNew = entity.getProductList();
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
			entity.setProductList(productListNew);
			entity = em.merge(entity);
			for (Product productListNewProduct : productListNew) {
				if (!productListOld.contains(productListNewProduct)) {
					Group oldCategoryOfProductListNewProduct = productListNewProduct.getGroup();
					productListNewProduct.setGroup(entity);
					productListNewProduct = em.merge(productListNewProduct);
					if (oldCategoryOfProductListNewProduct != null && !oldCategoryOfProductListNewProduct.equals(entity)) {
						oldCategoryOfProductListNewProduct.getProductList().remove(productListNewProduct);
						oldCategoryOfProductListNewProduct = em.merge(oldCategoryOfProductListNewProduct);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = entity.getId();
				if (get(id) == null) {
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

	//
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
