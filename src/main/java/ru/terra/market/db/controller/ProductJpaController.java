package ru.terra.market.db.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;

import ru.terra.market.core.AbstractJpaController;
import ru.terra.market.db.controller.exceptions.IllegalOrphanException;
import ru.terra.market.db.controller.exceptions.NonexistentEntityException;
import ru.terra.market.db.controller.exceptions.PreexistingEntityException;
import ru.terra.market.db.entity.Group;
import ru.terra.market.db.entity.Photo;
import ru.terra.market.db.entity.Product;

/**
 * 
 * @author terranz
 */
public class ProductJpaController extends AbstractJpaController<Product> implements Serializable {
	public ProductJpaController() {
		super(Product.class);
	}

	private static final long serialVersionUID = -6591200996197051814L;

	public void create(List<Product> prods) {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			for (Product product : prods) {
				Group category = product.getGroup();
				if (category != null) {
					category = em.getReference(category.getClass(), category.getId());
					product.setGroup(category);
				}
				em.persist(product);
				if (category != null) {
					category.getProductList().add(product);
					category = em.merge(category);
				}
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Product> findProductByCategory(Group cat, Boolean all, Integer page, Integer perpage) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createNamedQuery("Product.findByGroup").setParameter("group", cat);
			if (!all) {
				q.setMaxResults(perpage);
				q.setFirstResult(page * perpage);
			}
			return q.getResultList();
		} catch (NoResultException e) {
			return null;
		} finally {
			em.close();
		}
	}

	public Long getProductCount(Group cat) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createNativeQuery("select count(id) from product where group_id = " + cat.getId());
			return (Long) q.getSingleResult();
		} finally {
			em.close();
		}
	}

	public List<Product> findProductsByName(String name) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createNativeQuery("select * from product where name like %%" + name + "%%");
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public void create(Product entity) throws Exception {
		if (entity.getPhotoList() == null) {
			entity.setPhotoList(new ArrayList<Photo>());
		}
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Group category = entity.getGroup();
			if (category != null) {
				category = em.getReference(category.getClass(), category.getId());
				entity.setGroup(category);
			}
			List<Photo> attachedPhotoList = new ArrayList<Photo>();
			for (Photo photoListPhotoToAttach : entity.getPhotoList()) {
				photoListPhotoToAttach = em.getReference(photoListPhotoToAttach.getClass(), photoListPhotoToAttach.getId());
				attachedPhotoList.add(photoListPhotoToAttach);
			}
			entity.setPhotoList(attachedPhotoList);
			em.persist(entity);
			if (category != null) {
				category.getProductList().add(entity);
				category = em.merge(category);
			}
			for (Photo photoListPhoto : entity.getPhotoList()) {
				Product oldProductIdOfPhotoListPhoto = photoListPhoto.getProduct();
				photoListPhoto.setProduct(entity);
				photoListPhoto = em.merge(photoListPhoto);
				if (oldProductIdOfPhotoListPhoto != null) {
					oldProductIdOfPhotoListPhoto.getPhotoList().remove(photoListPhoto);
					oldProductIdOfPhotoListPhoto = em.merge(oldProductIdOfPhotoListPhoto);
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			if (get(entity.getId()) != null) {
				throw new PreexistingEntityException("Product " + entity + " already exists.", ex);
			}
			throw ex;
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
			Product product;
			try {
				product = em.getReference(Product.class, id);
				product.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The product with id " + id + " no longer exists.", enfe);
			}
			List<String> illegalOrphanMessages = null;
			List<Photo> photoListOrphanCheck = product.getPhotoList();
			for (Photo photoListOrphanCheckPhoto : photoListOrphanCheck) {
				if (illegalOrphanMessages == null) {
					illegalOrphanMessages = new ArrayList<String>();
				}
				illegalOrphanMessages.add("This Product (" + product + ") cannot be destroyed since the Photo " + photoListOrphanCheckPhoto
						+ " in its photoList field has a non-nullable productId field.");
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			Group category = product.getGroup();
			if (category != null) {
				category.getProductList().remove(product);
				category = em.merge(category);
			}
			em.remove(product);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public void update(Product entity) throws Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Product persistentProduct = em.find(Product.class, entity.getId());
			Group categoryOld = persistentProduct.getGroup();
			Group categoryNew = entity.getGroup();
			List<Photo> photoListOld = persistentProduct.getPhotoList();
			List<Photo> photoListNew = entity.getPhotoList();
			List<String> illegalOrphanMessages = null;
			for (Photo photoListOldPhoto : photoListOld) {
				if (!photoListNew.contains(photoListOldPhoto)) {
					if (illegalOrphanMessages == null) {
						illegalOrphanMessages = new ArrayList<String>();
					}
					illegalOrphanMessages.add("You must retain Photo " + photoListOldPhoto + " since its productId field is not nullable.");
				}
			}
			if (illegalOrphanMessages != null) {
				throw new IllegalOrphanException(illegalOrphanMessages);
			}
			if (categoryNew != null) {
				categoryNew = em.getReference(categoryNew.getClass(), categoryNew.getId());
				entity.setGroup(categoryNew);
			}
			List<Photo> attachedPhotoListNew = new ArrayList<Photo>();
			for (Photo photoListNewPhotoToAttach : photoListNew) {
				photoListNewPhotoToAttach = em.getReference(photoListNewPhotoToAttach.getClass(), photoListNewPhotoToAttach.getId());
				attachedPhotoListNew.add(photoListNewPhotoToAttach);
			}
			photoListNew = attachedPhotoListNew;
			entity.setPhotoList(photoListNew);
			entity = em.merge(entity);
			if (categoryOld != null && !categoryOld.equals(categoryNew)) {
				categoryOld.getProductList().remove(entity);
				categoryOld = em.merge(categoryOld);
			}
			if (categoryNew != null && !categoryNew.equals(categoryOld)) {
				categoryNew.getProductList().add(entity);
				categoryNew = em.merge(categoryNew);
			}
			for (Photo photoListNewPhoto : photoListNew) {
				if (!photoListOld.contains(photoListNewPhoto)) {
					Product oldProductIdOfPhotoListNewPhoto = photoListNewPhoto.getProduct();
					photoListNewPhoto.setProduct(entity);
					photoListNewPhoto = em.merge(photoListNewPhoto);
					if (oldProductIdOfPhotoListNewPhoto != null && !oldProductIdOfPhotoListNewPhoto.equals(entity)) {
						oldProductIdOfPhotoListNewPhoto.getPhotoList().remove(photoListNewPhoto);
						oldProductIdOfPhotoListNewPhoto = em.merge(oldProductIdOfPhotoListNewPhoto);
					}
				}
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = entity.getId();
				if (get(id) == null) {
					throw new NonexistentEntityException("The product with id " + id + " no longer exists.");
				}
			}
			throw ex;
		} finally {
			if (em != null) {
				em.close();
			}
		}

	}

}
