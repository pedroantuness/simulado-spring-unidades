package br.com.fiap.unidades.resource;


import br.com.fiap.unidades.dto.request.UsuarioRequest;
import br.com.fiap.unidades.dto.response.UsuarioResponse;
import br.com.fiap.unidades.entity.Pessoa;
import br.com.fiap.unidades.entity.Tipo;
import br.com.fiap.unidades.entity.Usuario;
import br.com.fiap.unidades.repository.PessoaRepository;
import br.com.fiap.unidades.service.UsuarioService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping(value = "/usuario")
public class UsuarioResource {


    @Autowired
    private UsuarioService service;

    @Autowired
    private PessoaRepository pessoaRepository;


    @GetMapping
    public ResponseEntity<Collection<UsuarioResponse>> findall(
            @RequestParam(name = "username", required = false) String username,
            @RequestParam(name = "pessoa.id", required = false) Long idPessoa,
            @RequestParam(name = "pessoa.nome", required = false) String pessoaNome,
            @RequestParam(name = "pessoa.sobrenome", required = false) String pessoaSobrenome,
            @RequestParam(name = "pessoa.nascimento", required = false) LocalDate pessoaNascimento,
            @RequestParam(name = "pessoa.tipo", required = false) Tipo pessoaTipo,
            @RequestParam(name = "pessoa.email", required = false) String pessoaEmail
    ) {

        Pessoa pessoa = null;
        if (Objects.nonNull( idPessoa ) && idPessoa > 0) {
            pessoa = pessoaRepository.findById( idPessoa ).orElse( null );
        } else {
            pessoa = Pessoa.builder()
                    .tipo( pessoaTipo )
                    .nome( pessoaNome )
                    .email( pessoaEmail )
                    .sobrenome( pessoaSobrenome )
                    .nascimento( pessoaNascimento )
                    .build();
        }

        var usuario = Usuario.builder()
                .pessoa( pessoa )
                .username( username )
                .build();

        ExampleMatcher matcher = ExampleMatcher.matchingAll().withIgnoreNullValues().withIgnoreCase();


        Example<Usuario> example = Example.of( usuario, matcher );

        List<Usuario> encontrados = service.findAll( example );

        if (encontrados.isEmpty()) return ResponseEntity.notFound().build();

        var resposta = encontrados.stream().map( service::toResponse ).toList();

        return ResponseEntity.ok( resposta );

    }


    @GetMapping(value = "/{id}")
    public ResponseEntity<UsuarioResponse> findById(Long id) {
        var encontrado = service.findById( id );
        if (encontrado == null) return ResponseEntity.notFound().build();
        var resposta = service.toResponse( encontrado );
        return ResponseEntity.ok( resposta );
    }


    @Transactional
    @PostMapping
    public ResponseEntity<UsuarioResponse> save(@RequestBody @Valid UsuarioRequest r) {
        var entity = service.toEntity( r );
        var saved = service.save( entity );
        var resposta = service.toResponse( saved );

        var uri = ServletUriComponentsBuilder
                .fromCurrentRequestUri()
                .path( "/{id}" )
                .buildAndExpand( saved.getId() )
                .toUri();

        return ResponseEntity.created( uri ).body( resposta ); //201
    }


}
