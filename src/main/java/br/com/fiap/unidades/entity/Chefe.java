package br.com.fiap.unidades.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Chefe {

    private Long id;

    private Boolean substituto;

    private Usuario usuario;

    private Unidade unidade;

    private LocalDateTime inicio;

    private LocalDateTime fim;

}
