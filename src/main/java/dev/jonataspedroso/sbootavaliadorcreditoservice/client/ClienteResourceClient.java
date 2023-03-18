package dev.jonataspedroso.sbootavaliadorcreditoservice.client;

import dev.jonataspedroso.sbootavaliadorcreditoservice.domain.entity.DadosCliente;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@FeignClient(value = "sboot-cliente-service", path = "/clientes")
public interface ClienteResourceClient {

	@GetMapping(params = "cpf")
	ResponseEntity<DadosCliente> dadosCliente(@RequestParam("cpf") String cpf);

}
