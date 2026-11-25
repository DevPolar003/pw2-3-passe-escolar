package br.com.etechas.pw2_3_passe_escolar.models;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
@Table
@Entity(name = "Carteirinha")
public class SolicitacaoCarteirinha {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "nome_estudante")
    private String nomeEstudante;

    @Column(name = "matricula")
    private String matricula;

    @Column(name = "linha_onibus")
    private String linhaOnibus;

    @Column(name = "data_solicitacao")
    private LocalDate dataSolicitacao;

    @Column(name = "data_retirada_prevista")
    private LocalDate dataRetiradaPrevista;

    @Column(name = "status")
    @Enumerated(EnumType.STRING)
    private Status status;
}
