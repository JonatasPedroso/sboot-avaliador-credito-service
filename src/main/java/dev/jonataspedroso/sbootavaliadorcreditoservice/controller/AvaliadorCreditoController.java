package dev.jonataspedroso.sbootavaliadorcreditoservice.controller;

import dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity.SituacaoCliente;
import dev.jonataspedroso.sbootavaliadorcreditoservice.exception.DadosClienteNotFoundException;
import dev.jonataspedroso.sbootavaliadorcreditoservice.exception.ErroComunicacaoMicroservicesException;
import dev.jonataspedroso.sbootavaliadorcreditoservice.service.AvaliadorCreditoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

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
}
