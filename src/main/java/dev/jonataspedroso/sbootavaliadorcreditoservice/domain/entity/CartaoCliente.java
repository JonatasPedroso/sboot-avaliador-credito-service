package dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CartaoCliente {
	private String nome;
	private String bandeira;
	private BigDecimal limiteLiberado;
}
