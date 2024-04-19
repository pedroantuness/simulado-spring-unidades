package br.com.fiap.unidades.dto.response;

import lombok.Builder;

import java.time.LocalDateTime;


@Builder
public record ChefeResponse(

        Long id,
        Boolean substituto,
        UsuarioResponse usuario,
        UnidadeResponse unidade,
        LocalDateTime inicio,
        LocalDateTime fim

) {
}
