package ru.terra.market.db.controller;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

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
				Group group = product.getGroup();
				if (group != null) {
					group = em.getReference(group.getClass(), group.getId());
					product.setGroup(group);
				}
				em.persist(product);
				if (group != null) {
					group.getProductList().add(product);
					group = em.merge(group);
				}
			}
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	public List<Product> findProductByGroup(Group group, Boolean all, Integer page, Integer perpage) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createNamedQuery("Product.findByGroup").setParameter("group", group);
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

	public Long getProductCountByGroup(Group group) {
		EntityManager em = getEntityManager();
		try {
			Query q = em.createNativeQuery("select count(id) from product where group_id = " + group.getId());
			return (Long) q.getSingleResult();
		} finally {
			em.close();
		}
	}

	public List<Product> findProductsByName(String name) {
		EntityManager em = getEntityManager();
		try {
			// Query q =
			// em.createNativeQuery("select * from product where name like %%" +
			// name + "%%");
			// q.setParameter(1, name);
			CriteriaBuilder cb = em.getCriteriaBuilder();
			CriteriaQuery<Product> c = cb.createQuery(Product.class);
			Root<Product> emp = c.from(Product.class);
			c.select(emp);
			c.where(cb.like(emp.<String> get("name"), "%" + name + "%"));
			return em.createQuery(c).getResultList();

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
			Group group = entity.getGroup();
			if (group != null) {
				group = em.getReference(group.getClass(), group.getId());
				entity.setGroup(group);
			}
			List<Photo> attachedPhotoList = new ArrayList<Photo>();
			for (Photo photoListPhotoToAttach : entity.getPhotoList()) {
				photoListPhotoToAttach = em.getReference(photoListPhotoToAttach.getClass(), photoListPhotoToAttach.getId());
				attachedPhotoList.add(photoListPhotoToAttach);
			}
			entity.setPhotoList(attachedPhotoList);
			em.persist(entity);
			if (group != null) {
				group.getProductList().add(entity);
				group = em.merge(group);
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
			Group group = product.getGroup();
			if (group != null) {
				group.getProductList().remove(product);
				group = em.merge(group);
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
			Group groupOld = persistentProduct.getGroup();
			Group groupNew = entity.getGroup();
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
			if (groupNew != null) {
				groupNew = em.getReference(groupNew.getClass(), groupNew.getId());
				entity.setGroup(groupNew);
			}
			List<Photo> attachedPhotoListNew = new ArrayList<Photo>();
			for (Photo photoListNewPhotoToAttach : photoListNew) {
				photoListNewPhotoToAttach = em.getReference(photoListNewPhotoToAttach.getClass(), photoListNewPhotoToAttach.getId());
				attachedPhotoListNew.add(photoListNewPhotoToAttach);
			}
			photoListNew = attachedPhotoListNew;
			entity.setPhotoList(photoListNew);
			entity = em.merge(entity);
			if (groupOld != null && !groupOld.equals(groupNew)) {
				groupOld.getProductList().remove(entity);
				groupOld = em.merge(groupOld);
			}
			if (groupNew != null && !groupNew.equals(groupOld)) {
				groupNew.getProductList().add(entity);
				groupNew = em.merge(groupNew);
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
