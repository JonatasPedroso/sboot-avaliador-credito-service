package dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosAvaliacao {
    private String cpf;
    private Long renda;
}
