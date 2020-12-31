package com.algaworks.algafood.infrastructure.repository;

import com.algaworks.algafood.domain.model.Cidade;
import com.algaworks.algafood.domain.repository.CidadeRepository;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.Transactional;
import java.util.List;

@Component
public class CidadeRepositoryImpl implements CidadeRepository {

    @PersistenceContext
    private EntityManager manager;

    @Override
    public List<Cidade> todas() {
        return null;
    }

    @Override
    public Cidade porId(Long id) {
        return manager.find(Cidade.class, id);
    }

    @Override
    public Cidade adicionar(Cidade cidade) {
        return null;
    }

    @Transactional
    @Override
    public void remover(Long id) {
        Cidade cidade = porId(id);

        if (cidade == null) {
            throw new EmptyResultDataAccessException(1);
        }

        manager.remove(cidade);
    }
}
