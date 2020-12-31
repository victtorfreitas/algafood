package com.algaworks.algafood.domain.service;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Cozinha;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.CozinhaRepository;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CadastroRestauranteService {

    private final RestauranteRepository restauranteRepository;
    private final CozinhaRepository cozinhaRepository;

    public CadastroRestauranteService(RestauranteRepository restauranteRepository,
                                      CozinhaRepository cozinhaRepository) {
        this.restauranteRepository = restauranteRepository;
        this.cozinhaRepository = cozinhaRepository;
    }

    public Restaurante salvar(Restaurante restaurante) {
        Long idCozinha = restaurante.getCozinha().getId();

        Optional<Cozinha> cozinha = cozinhaRepository.findById(idCozinha);
        if (cozinha.isEmpty()) {
            throw new EntidadeNaoEncontradaException(
                    String.format("Não existe cadastro de cozinha com o código %d", idCozinha));
        }
        restaurante.setCozinha(cozinha.get());
        return restauranteRepository.save(restaurante);
    }

    public Restaurante atualizar(Long id, Restaurante restaurante) {
        Optional<Restaurante> restauranteBd = restauranteRepository.findById(id);
        if (isNotRestaurante(restauranteBd)) {
            BeanUtils.copyProperties(restaurante, restauranteBd.get(), "id");
            return salvar(restauranteBd.get());
        }
        throw new EntidadeNaoEncontradaException(
                String.format("Não existe cadastro de Restaurante com o código $d", id));
    }

    private boolean isNotRestaurante(Optional<Restaurante> restauranteBd) {
        return !restauranteBd.isEmpty();
    }
}
