package dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DadosCliente {
	private Long id;
	private String nome;
    private Integer idade;
}
