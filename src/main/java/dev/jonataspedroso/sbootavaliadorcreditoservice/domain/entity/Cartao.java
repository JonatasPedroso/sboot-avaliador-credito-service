package dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity;

import lombok.*;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Cartao {
    private Long id;
    private String nome;
    private String bandeira;
    private BigDecimal limiteBasico;
}
