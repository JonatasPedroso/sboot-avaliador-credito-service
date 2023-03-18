package dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SituacaoCliente {
	private DadosCliente cliente;
	private List<CartaoCliente> cartoes;
}
