package ru.terra.market.db.controller;

import java.io.Serializable;

import javax.persistence.EntityManager;
import javax.persistence.EntityNotFoundException;

import ru.terra.market.core.AbstractJpaController;
import ru.terra.market.db.controller.exceptions.NonexistentEntityException;
import ru.terra.market.db.entity.Photo;
import ru.terra.market.db.entity.Product;

/**
 * 
 * @author terranz
 */
public class PhotoJpaController extends AbstractJpaController<Photo> implements Serializable {

	private static final long serialVersionUID = 8812634593042868091L;

	public PhotoJpaController() {
		super(Photo.class);
	}

	@Override
	public void create(Photo entity) throws Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Product productId = entity.getProduct();
			if (productId != null) {
				productId = em.getReference(productId.getClass(), productId.getId());
				entity.setProduct(productId);
			}
			em.persist(entity);
			if (productId != null) {
				productId.getPhotoList().add(entity);
				productId = em.merge(productId);
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
			Photo photo;
			try {
				photo = em.getReference(Photo.class, id);
				photo.getId();
			} catch (EntityNotFoundException enfe) {
				throw new NonexistentEntityException("The photo with id " + id + " no longer exists.", enfe);
			}
			Product productId = photo.getProduct();
			if (productId != null) {
				productId.getPhotoList().remove(photo);
				productId = em.merge(productId);
			}
			em.remove(photo);
			em.getTransaction().commit();
		} finally {
			if (em != null) {
				em.close();
			}
		}
	}

	@Override
	public void update(Photo entity) throws Exception {
		EntityManager em = null;
		try {
			em = getEntityManager();
			em.getTransaction().begin();
			Photo persistentPhoto = em.find(Photo.class, entity.getId());
			Product productIdOld = persistentPhoto.getProduct();
			Product productIdNew = entity.getProduct();
			if (productIdNew != null) {
				productIdNew = em.getReference(productIdNew.getClass(), productIdNew.getId());
				entity.setProduct(productIdNew);
			}
			entity = em.merge(entity);
			if (productIdOld != null && !productIdOld.equals(productIdNew)) {
				productIdOld.getPhotoList().remove(entity);
				productIdOld = em.merge(productIdOld);
			}
			if (productIdNew != null && !productIdNew.equals(productIdOld)) {
				productIdNew.getPhotoList().add(entity);
				productIdNew = em.merge(productIdNew);
			}
			em.getTransaction().commit();
		} catch (Exception ex) {
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0) {
				Integer id = entity.getId();
				if (get(id) == null) {
					throw new NonexistentEntityException("The photo with id " + id + " no longer exists.");
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
