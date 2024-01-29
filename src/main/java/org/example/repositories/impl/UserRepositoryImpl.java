package org.example.repositories.impl;

import org.example.entities.User;
import org.example.repositories.UserRepository;
import org.example.utils.JpaUtil;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class UserRepositoryImpl implements UserRepository {

    @Override
    public void saveUser(User user) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        entityManager.getTransaction().begin();
        entityManager.persist(user);
        entityManager.getTransaction().commit();
        entityManager.close();
    }

    @Override
    public User getUserByID(int id) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        User user = entityManager.find(User.class, id);
        entityManager.close();
        return user;
    }

    @Override
    public User getUserByUsername(String username) {
        EntityManager entityManager = JpaUtil.getEntityManager();

        try {
            String jpql = "SELECT u FROM User u WHERE u.username = :username";
            TypedQuery<User> query = entityManager.createQuery(jpql, User.class);
            query.setParameter("username", username);
            return query.getSingleResult();
        } catch (NoResultException e) {
            return null;
        } finally {
            entityManager.close();
        }
    }

    @Override
    public void deleteUser(int id) {
        EntityManager entityManager = JpaUtil.getEntityManager();
        entityManager.getTransaction().begin();
        User user = entityManager.find(User.class, id);
        if (user != null) {
            entityManager.remove(user);
        }
        entityManager.getTransaction().commit();
        entityManager.close();
    }
}
