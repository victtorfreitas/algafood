package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.exception.EntidadeNaoEncontradaException;
import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import com.algaworks.algafood.domain.service.CadastroRestauranteService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteRepository restauranteRepository;
    private final CadastroRestauranteService restauranteService;

    public RestauranteController(RestauranteRepository restauranteRepository,
                                 CadastroRestauranteService restauranteService) {
        this.restauranteRepository = restauranteRepository;
        this.restauranteService = restauranteService;
    }

    @GetMapping
    public List<Restaurante> listar() {
        return restauranteRepository.todos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id) {
        Restaurante restaurante = restauranteRepository.porId(id);
        if (restaurante != null) {
            return ResponseEntity.ok(restaurante);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<?> adicionar(@RequestBody Restaurante restaurante) {
        try {
            return ResponseEntity.ok(restauranteService.salvar(restaurante));
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> atualizar(@PathVariable Long id, @RequestBody Restaurante restaurante) {
        try {
            restaurante = restauranteService.atualizar(id, restaurante);
            return ResponseEntity.ok(restaurante);
        } catch (EntidadeNaoEncontradaException ex) {
            return ResponseEntity.badRequest().body(ex.getMessage());
        }

    }

    @PatchMapping("/{id}")
    public ResponseEntity<?> atualizarParcial(@PathVariable Long id, @RequestBody Map<String, Object> campos) {
        Restaurante restaurante = restauranteRepository.porId(id);

        if (restaurante == null) {
            return ResponseEntity.notFound().build();
        }

        merge(campos, restaurante);

        return atualizar(id, restaurante);
    }

    private void merge(Map<String, Object> campos, Restaurante restaurante) {
        Restaurante restauranteOrigem = new ObjectMapper().convertValue(campos, Restaurante.class);

        campos.forEach((nomePropriedade, valorPropriedade) -> {
            Field field = ReflectionUtils.findField(Restaurante.class, nomePropriedade);
            field.setAccessible(true);
            Object novoValor = ReflectionUtils.getField(field, restauranteOrigem);

            ReflectionUtils.setField(field, restaurante, novoValor);
        });
    }
}

