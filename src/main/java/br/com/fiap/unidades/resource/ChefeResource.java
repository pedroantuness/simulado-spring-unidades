package br.com.fiap.unidades.resource;


import br.com.fiap.unidades.dto.request.ChefeRequest;
import br.com.fiap.unidades.dto.response.ChefeResponse;
import br.com.fiap.unidades.entity.Chefe;
import br.com.fiap.unidades.repository.UnidadeRepository;
import br.com.fiap.unidades.repository.UsuarioRepository;
import br.com.fiap.unidades.service.ChefeService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.util.Collection;
import java.util.Objects;

@RestController
    @RequestMapping(value = "/api/chefe")
public class ChefeResource implements ResourceDTO<ChefeRequest, ChefeResponse> {

    @Autowired
    private ChefeService service;
    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private UnidadeRepository unidadeRepository;


    @GetMapping
    public ResponseEntity<Collection<ChefeResponse>> findAll(
            @RequestParam(name = "usuario.id", required = false) Long idUsario,
            @RequestParam(name = "substituto", required = false, defaultValue = "false") Boolean substituto,
            @RequestParam(name = "undade.id", required = false) Long idUnidade

    ) {

        var usuario = Objects.nonNull( idUsario ) ? usuarioRepository
                .findById( idUsario )
                .orElse( null ) : null;

        var unidade = Objects.nonNull( idUnidade ) ? unidadeRepository
                .findById( idUsario )
                .orElse( null ) : null;


        Chefe chefe = Chefe.builder()
                .unidade( unidade )
                .usuario( usuario )
                .substituto( substituto )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll()
                .withIgnoreNullValues()
                .withIgnoreCase();

        Example<Chefe> example = Example.of( chefe, matcher );

        var encontrados = service.findAll( example );

        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();

        var resposta = encontrados.stream()
                .map( service::toResponse )
                .toList();

        return ResponseEntity.ok( resposta );
    }

    @Override
    @GetMapping(value = "/{id}")
    public ResponseEntity<ChefeResponse> findById(Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }

    @Override
    @Transactional
    @PostMapping
    public ResponseEntity<ChefeResponse> save(@RequestBody @Valid ChefeRequest r) {
        var entity = service.toEntity( r );
        var saved = service.save( entity );
        var resposta = service.toResponse( saved );

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( saved.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( resposta );
    }
}
