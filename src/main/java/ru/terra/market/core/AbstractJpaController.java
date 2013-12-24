package ru.terra.market.core;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.List;

/**
 * User: Vadim Korostelev Date: 11.09.13 Time: 10:33
 */
public abstract class AbstractJpaController<Entity> extends AbstractDBController<Entity> {

	protected EntityManagerFactory emf = null;

	public AbstractJpaController(Class entityClass) {
		super(entityClass);
		this.emf = Persistence.createEntityManagerFactory("market-dbPU");
	}

	public EntityManager getEntityManager() {
		return emf.createEntityManager();
	}

	public Entity get(Integer id) throws Exception {
		EntityManager em = getEntityManager();
		try {
			return em.find(entityClass, id);
		} finally {
			em.close();
		}
	}

	@Override
	public List<Entity> list(boolean all, int page, int perpage) throws Exception {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(entityClass));
			Query q = em.createQuery(cq);
			if (!all) {
				q.setMaxResults(perpage);
				q.setFirstResult(page * perpage);
			}
			return q.getResultList();
		} finally {
			em.close();
		}
	}

	@Override
	public int count() {
		EntityManager em = getEntityManager();
		try {
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root rt = cq.from(entityClass);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally {
			em.close();
		}
	}
}
