package cn.mauth.ccrm.server;

import cn.mauth.ccrm.rep.BaseRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.*;
import org.springframework.data.jpa.domain.Specification;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.Serializable;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public abstract class BaseServer<T,R extends BaseRepository>{

    private static final int SIZE=10;
    private static final Sort SORT=Sort.by(Sort.Direction.ASC,"dbid");

    private final R repository;

    @Autowired
    private EntityManager entityManager;

    public Page<T> pageSql(String sql, Map<String,Object> param,Pageable pageable,Sort sort){

        Query query= entityManager.createNamedQuery(sql);

        for (Map.Entry<String,Object> entry:param.entrySet()) {
            query=query.setParameter(entry.getKey(),entry.getValue());
        }

        int total=query.getResultList().size();
        pageable=getPageRequest(pageable,sort);
        int page=pageable.getPageNumber();
        int size=pageable.getPageSize();
        int start=(page-1)*size;

        List<T> list=query.setFirstResult(start).setMaxResults(size).getResultList();

        return new PageImpl<>(list,pageable,total);
    }

    public List<T> sql(String sql, Map<String,Object> param){
        Query query= entityManager.createNamedQuery(sql);

        for (Map.Entry<String,Object> entry:param.entrySet()) {
            query=query.setParameter(entry.getKey(),entry.getValue());
        }
        return query.getResultList();
    }

    public BaseServer(R repository) {
        this.repository = repository;
    }

    public R getRepository() {
        return repository;
    }

    public T get(Serializable id){
        return findById(id).get();
    }

    public List<T> findAll() {
        return repository.findAll();
    }

    
    public List<T> findAll(Sort sort) {
        return repository.findAll(sort);
    }

    
    public List<T> findAllById(Iterable<Serializable> iterable) {
        return repository.findAllById(iterable);
    }

    
    public <S extends T> List<S> saveAll(Iterable<S> iterable) {
        return repository.saveAll(iterable);
    }

    
    public void flush() {
        repository.flush();
    }

    
    public <S extends T> S saveAndFlush(S s) {
        return (S)repository.saveAndFlush(s);
    }

    
    public void deleteInBatch(Iterable<T> iterable) {
        repository.deleteInBatch(iterable);
    }

    
    public void deleteAllInBatch() {
        repository.deleteAllInBatch();
    }

    
    public T getOne(Serializable id) {
        return (T)repository.getOne(id);
    }

    
    public <S extends T> List<S> findAll(Example<S> example) {
        return repository.findAll(example);
    }

    
    public <S extends T> List<S> findAll(Example<S> example, Sort sort) {
        return repository.findAll(example,sort);
    }

    
    public Optional<T> findOne(Specification<T> specification) {
        return repository.findOne(specification);
    }

    
    public List<T> findAll(Specification<T> specification) {
        return repository.findAll(specification);
    }

    
    public Page<T> findAll(Specification<T> specification, Pageable pageable) {
        return repository.findAll(specification,pageable);
    }

    
    public List<T> findAll(Specification<T> specification, Sort sort) {
        return repository.findAll(specification,sort);
    }

    
    public long count(Specification<T> specification) {
        return repository.count(specification);
    }

    
    public Page<T> findAll(Pageable pageable) {
        return repository.findAll(pageable);
    }

    
    public <S extends T> S save(S s) {
        return (S)repository.saveAndFlush(s);
    }

    
    public Optional<T> findById(Serializable id) {
        return repository.findById(id);
    }

    
    public boolean existsById(Serializable id) {
        return repository.existsById(id);
    }

    
    public long count() {
        return repository.count();
    }

    
    public void deleteById(Serializable id) {
        repository.deleteById(id);
    }

    
    public void delete(T t) {
        repository.delete(t);
    }

    
    public void deleteAll(Iterable<? extends T> iterable) {
        repository.deleteAll(iterable);
    }

    
    public void deleteAll() {
        repository.deleteAll();
    }

    
    public <S extends T> Optional<S> findOne(Example<S> example) {
        return repository.findOne(example);
    }

    
    public <S extends T> Page<S> findAll(Example<S> example, Pageable pageable) {
        return repository.findAll(example,pageable);
    }

    
    public <S extends T> long count(Example<S> example) {
        return repository.count(example);
    }

    
    public <S extends T> boolean exists(Example<S> example) {
        return repository.exists(example);
    }


    public String like(String like){
        return "%"+like+"%";
    }

    public  Specification<T> specification(T t){
        return null;
    };

    public PageRequest getPageRequest(Pageable pageable) {
        return getPageRequest(pageable,SORT);
    }

    public PageRequest getPageRequest(Pageable pageable, Sort sort) {
        if (pageable != null) {
            int page = pageable.getPageNumber() > 0 ? pageable.getPageNumber() : 0;
            Sort s = pageable.getSort() != null ? pageable.getSort() : sort;
            int size = pageable.getPageSize() > 0 ? pageable.getPageSize() : SIZE;
            return PageRequest.of(page, size, s);
        } else {
            return PageRequest.of(0, SIZE);
        }
    }

    public PageRequest getPageRequest(int page, Integer size,Sort sort){
        return getPageRequest(PageRequest.of(page,size),sort);
    }
}
