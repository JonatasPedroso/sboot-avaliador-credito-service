package dev.jonataspedroso.sbootavaliadorcreditoservice.exception;

public class DadosClienteNotFoundException extends Exception {
	public DadosClienteNotFoundException() {
		super("Dados do cliente não encontrados para o cpf informado!");
	}
}
