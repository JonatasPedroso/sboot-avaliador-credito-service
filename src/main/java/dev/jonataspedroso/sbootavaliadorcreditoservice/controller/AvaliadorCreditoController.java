package dev.jonataspedroso.sbootavaliadorcreditoservice.controller;

import dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity.*;
import dev.jonataspedroso.sbootavaliadorcreditoservice.exception.DadosClienteNotFoundException;
import dev.jonataspedroso.sbootavaliadorcreditoservice.exception.ErroComunicacaoMicroservicesException;
import dev.jonataspedroso.sbootavaliadorcreditoservice.exception.ErroSolicitacaoCartaoException;
import dev.jonataspedroso.sbootavaliadorcreditoservice.service.AvaliadorCreditoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping("avaliacoes-credito")
public class AvaliadorCreditoController {

	private final AvaliadorCreditoService avaliadorCreditoService;

	@GetMapping
	public String status () {
		return "ok";
	}

	@GetMapping(value = "situacao-cliente", params = "cpf")
	public ResponseEntity consultaSituacaoCliente(@RequestParam("cpf") String cpf) {
		try {
			SituacaoCliente situacaoCliente = avaliadorCreditoService.obterSituacaoCliente(cpf);
			return ResponseEntity.ok(situacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoMicroservicesException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}

	@PostMapping
	public ResponseEntity realizarAvaliacao(@RequestBody DadosAvaliacao dados) {
		try {
			RetornoAvaliacaoCliente retornoAvaliacaoCliente = avaliadorCreditoService
					.realizarAvaliacao(dados.getCpf(), dados.getRenda());
			return ResponseEntity.ok(retornoAvaliacaoCliente);
		} catch (DadosClienteNotFoundException e) {
			return ResponseEntity.notFound().build();
		} catch (ErroComunicacaoMicroservicesException e) {
			return ResponseEntity.status(HttpStatus.resolve(e.getStatus())).body(e.getMessage());
		}
	}

	@PostMapping("solicitacoes-cartao")
	public ResponseEntity solicitarCartao(@RequestBody DadosSolicitacaoEmissaoCartao dados) {
		try {
			ProtocoloSolicitacaoCartao protocolo = avaliadorCreditoService.solicitarEmissaoCartao(dados);
			return ResponseEntity.ok(protocolo);
		} catch (ErroSolicitacaoCartaoException e) {
			return ResponseEntity.internalServerError().body(e.getMessage());
		}
	}
}
