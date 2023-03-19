package dev.jonataspedroso.sbootavaliadorcreditoservice.service;

import dev.jonataspedroso.sbootavaliadorcreditoservice.client.CartoesResourceClient;
import dev.jonataspedroso.sbootavaliadorcreditoservice.client.ClienteResourceClient;
import dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity.*;
import dev.jonataspedroso.sbootavaliadorcreditoservice.exception.DadosClienteNotFoundException;
import dev.jonataspedroso.sbootavaliadorcreditoservice.exception.ErroComunicacaoMicroservicesException;
import dev.jonataspedroso.sbootavaliadorcreditoservice.exception.ErroSolicitacaoCartaoException;
import dev.jonataspedroso.sbootavaliadorcreditoservice.infraestructure.SolicitacaoEmissaoCartaoPublisher;
import feign.FeignException;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class AvaliadorCreditoService {

	private final ClienteResourceClient clientesClient;
	private final CartoesResourceClient cartoesClient;
	private final SolicitacaoEmissaoCartaoPublisher emissaoCartaoPublisher;

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

	public RetornoAvaliacaoCliente realizarAvaliacao(String cpf, Long renda)
			throws DadosClienteNotFoundException, ErroComunicacaoMicroservicesException {
		try {
			ResponseEntity<DadosCliente> dadosClienteResponse = clientesClient.dadosCliente(cpf);
			ResponseEntity<List<Cartao>> cartoesResponse = cartoesClient.getCartoesRendaAteh(renda);

			List<Cartao> cartaos = cartoesResponse.getBody();
			var cartoesAprovados = cartaos.stream()
					.map(cartao -> {
						DadosCliente dadosCliente = dadosClienteResponse.getBody();

						BigDecimal limiteBasico = cartao.getLimiteBasico();
						BigDecimal idadeBD = BigDecimal.valueOf(dadosCliente.getIdade());
						var fator = idadeBD.divide(BigDecimal.valueOf(10));
						BigDecimal limiteAprovado = fator.multiply(limiteBasico);

						return CartaoAprovado.builder()
								.cartao(cartao.getNome())
								.bandeira(cartao.getBandeira())
								.limiteAprovado(limiteAprovado)
								.build();
					})
					.collect(Collectors.toList());

			return RetornoAvaliacaoCliente.builder()
					.cartoes(cartoesAprovados)
					.build();
		} catch (FeignException.FeignClientException e) {
			int status = e.status();
			if (HttpStatus.NOT_FOUND.value() == status) {
				throw new DadosClienteNotFoundException();
			}

			throw new ErroComunicacaoMicroservicesException(e.getMessage(), status);
		}
	}

	public ProtocoloSolicitacaoCartao solicitarEmissaoCartao(DadosSolicitacaoEmissaoCartao dados) {
		try {
			emissaoCartaoPublisher.solicitarCartao(dados);
			var protocolo = UUID.randomUUID().toString();
			return ProtocoloSolicitacaoCartao.builder().protocolo(protocolo).build();
		} catch (Exception e) {
			throw new ErroSolicitacaoCartaoException(e.getMessage());
		}
	}
}
