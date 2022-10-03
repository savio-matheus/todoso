package com.todoso.backend;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
class OlarController {
	private static final String modeloResposta = "Olá, %s!";

	@GetMapping("/olar")
	public Olar olar(
		@RequestParam(value = "name", defaultValue = "Mundo") String nome) {

		return new Olar(String.format(modeloResposta, nome));
	}
}