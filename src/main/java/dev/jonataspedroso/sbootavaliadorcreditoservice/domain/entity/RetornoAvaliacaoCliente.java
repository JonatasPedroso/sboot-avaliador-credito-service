package dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity;

import lombok.*;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RetornoAvaliacaoCliente {
    private List<CartaoAprovado> cartoes;
}
