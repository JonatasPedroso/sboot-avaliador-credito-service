package dev.jonataspedroso.sbootavaliadorcreditoservice.service;

import dev.jonataspedroso.sbootavaliadorcreditoservice.client.CartoesResourceClient;
import dev.jonataspedroso.sbootavaliadorcreditoservice.client.ClienteResourceClient;
import dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity.CartaoCliente;
import dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity.DadosCliente;
import dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity.SituacaoCliente;
import dev.jonataspedroso.sbootavaliadorcreditoservice.exception.DadosClienteNotFoundException;
import dev.jonataspedroso.sbootavaliadorcreditoservice.exception.ErroComunicacaoMicroservicesException;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class AvaliadorCreditoService {

	private final ClienteResourceClient clientesClient;
	private final CartoesResourceClient cartoesClient;

	public SituacaoCliente obterSituacaoCliente(String cpf)
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		try {
			ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
			ResponseEntity<List<CartaoCliente>> cartoesResponse = cartoesClient.getCartoesByCliente(cpf);

			return SituacaoCliente.builder()
					.cliente(dadosClienteResponse.getBody())
					.cartoes(cartoesResponse.getBody())
					.build();
		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}

			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
	}
}
