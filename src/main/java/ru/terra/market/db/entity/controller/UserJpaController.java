/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package ru.terra.market.db.entity.controller;

import java.io.Serializable;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.EntityNotFoundException;
import javax.persistence.NoResultException;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import ru.terra.market.db.entity.User;
import ru.terra.market.db.entity.controller.exceptions.NonexistentEntityException;

/**
 * 
 * @author terranz
 */
public class UserJpaController implements Serializable
{

	public UserJpaController(EntityManagerFactory emf)
	{
		this.emf = emf;
	}

	private EntityManagerFactory emf = null;

	public EntityManager getEntityManager()
	{
		return emf.createEntityManager();
	}

	public void create(User users)
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			em.persist(users);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public void edit(User users) throws NonexistentEntityException, Exception
	{
		EntityManager em = null;
		try
		{
			em = getEntityManager();
			em.getTransaction().begin();
			users = em.merge(users);
			em.getTransaction().commit();
		} catch (Exception ex)
		{
			String msg = ex.getLocalizedMessage();
			if (msg == null || msg.length() == 0)
			{
				Integer id = users.getId();
				if (findUser(id) == null)
				{
					throw new NonexistentEntityException("The users with id " + id + " no longer exists.");
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
			User users;
			try
			{
				users = em.getReference(User.class, id);
				users.getId();
			} catch (EntityNotFoundException enfe)
			{
				throw new NonexistentEntityException("The users with id " + id + " no longer exists.", enfe);
			}
			em.remove(users);
			em.getTransaction().commit();
		} finally
		{
			if (em != null)
			{
				em.close();
			}
		}
	}

	public List<User> findUsersEntities()
	{
		return findUsersEntities(true, -1, -1);
	}

	public List<User> findUsersEntities(int maxResults, int firstResult)
	{
		return findUsersEntities(false, maxResults, firstResult);
	}

	private List<User> findUsersEntities(boolean all, int maxResults, int firstResult)
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			cq.select(cq.from(User.class));
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

	public User findUser(Integer id)
	{
		EntityManager em = getEntityManager();
		try
		{
			return em.find(User.class, id);
		} catch (NoResultException e)
		{
			return null;
		} finally
		{
			em.close();
		}
	}

	public int getUsersCount()
	{
		EntityManager em = getEntityManager();
		try
		{
			CriteriaQuery cq = em.getCriteriaBuilder().createQuery();
			Root<User> rt = cq.from(User.class);
			cq.select(em.getCriteriaBuilder().count(rt));
			Query q = em.createQuery(cq);
			return ((Long) q.getSingleResult()).intValue();
		} finally
		{
			em.close();
		}
	}

	public User findUser(String login)
	{
		EntityManager em = getEntityManager();
		try
		{
			Query q = em.createNamedQuery("User.findByLogin").setParameter("login", login);
			return (User) q.getSingleResult();
		} catch (NoResultException e)
		{
			return null;
		} finally
		{
			em.close();
		}
	}

	public User findUser(String login, String password)
	{
		EntityManager em = getEntityManager();
		try
		{
			Query q = em.createNamedQuery("User.findByLoginAndPassword").setParameter("login", login).setParameter("password", password);
			return (User) q.getSingleResult();
		} catch (NoResultException e)
		{
			return null;
		} finally
		{
			em.close();
		}
	}

}
