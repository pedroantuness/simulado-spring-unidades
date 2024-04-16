package br.com.fiap.unidades.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder


public class Usuario {

    private Long id;

    private String username;

    private String password;

    private Pessoa pessoa;

}
