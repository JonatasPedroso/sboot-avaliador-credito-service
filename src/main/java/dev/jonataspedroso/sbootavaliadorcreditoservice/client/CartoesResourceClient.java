package dev.jonataspedroso.sbootavaliadorcreditoservice.client;

import dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity.Cartao;
import dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity.CartaoCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@FeignClient(value = "sboot-cartoes-service", path = "/cartoes")
public interface CartoesResourceClient {

	@GetMapping(params = "cpf")
	ResponseEntity<List<CartaoCliente>> getCartoesByCliente(@RequestParam("cpf") String cpf);

	@GetMapping(params = "renda")
	ResponseEntity<List<Cartao>> getCartoesRendaAteh(@RequestParam("renda") Long renda);
}
