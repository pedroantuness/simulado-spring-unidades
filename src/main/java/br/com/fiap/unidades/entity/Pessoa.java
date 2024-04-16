package br.com.fiap.unidades.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder

public class Pessoa {


    private Long id;

    private String nome;

    private String sobrenome;

    private String email;

    private LocalDate nascimento;

    @Enumerated(EnumType.STRING)
    @Column(name = "TP_PESSOA", nullable = false)
    private Tipo tipo;

}
