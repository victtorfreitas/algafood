package com.algaworks.algafood.api.controller;

import com.algaworks.algafood.domain.model.Restaurante;
import com.algaworks.algafood.domain.repository.RestauranteRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private final RestauranteRepository restauranteRepository;

    public RestauranteController(RestauranteRepository restauranteRepository) {
        this.restauranteRepository = restauranteRepository;
    }

    @GetMapping
    public List<Restaurante> listar(){
        return restauranteRepository.todos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Restaurante> buscar(@PathVariable Long id){
        Restaurante restaurante = restauranteRepository.porId(id);
        if(restaurante!=null){
            return ResponseEntity.ok(restaurante);
        }
        return ResponseEntity.notFound().build();
    }
}
