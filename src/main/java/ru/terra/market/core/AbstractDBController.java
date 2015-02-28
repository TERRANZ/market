package ru.terra.market.core;

import java.util.List;

/**
 * User: Vadim Korostelev
 * Date: 13.12.13
 * Time: 17:57
 */
public abstract class AbstractDBController<Entity> {
    protected Class<Entity> entityClass;    

    public AbstractDBController(Class<Entity> entityClass) {
        this.entityClass = entityClass;
    }

    public abstract void create(Entity entity) throws Exception;

    public abstract void delete(Integer id) throws Exception;

    public abstract void update(Entity entity) throws Exception;

    public void setEntityClass(Class<Entity> entityClass) {
        this.entityClass = entityClass;
    }

    public Class getEntityClass() {
        return entityClass;
    }

    public abstract List<Entity> list(boolean all, int page, int perpage) throws Exception;

    public abstract int count();

    public abstract Entity get(Integer id) throws Exception;
}
