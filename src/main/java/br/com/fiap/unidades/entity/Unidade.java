package br.com.fiap.unidades.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Unidade {

    private Long id;

    private String nome;

    private String sigla;

    private String descricao;

    private Unidade macro;

}
