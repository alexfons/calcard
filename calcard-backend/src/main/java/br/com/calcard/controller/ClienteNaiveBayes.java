package br.com.calcard.controller;

import java.util.Iterator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.calcard.model.Cliente;
import br.com.calcard.model.ClienteRequest;
import br.com.calcard.model.ResultOption;
import br.com.calcard.repository.ClienteRepository;
import br.com.calcard.util.ClientePrediction;
import br.com.calcard.util.ClienteProbability;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/v1/cliente")
public class ClienteNaiveBayes {

	@Autowired
	private ClienteRepository repository;

	@PostMapping("/avaliaProposta")
	public ResponseEntity<Cliente> avaliaProposta(@RequestBody final ClienteRequest request) {

		final ClienteProbability probability = new ClienteProbability(repository).calculate();
		final Iterator<ResultOption> resultOptions = probability.getResultOptions().iterator();
		final ClientePrediction prediction = new ClientePrediction(repository).predict(request, resultOptions);

		log.info(request.toString());
		log.info(probability.toString());
		log.info(prediction.toString());

		return ResponseEntity.ok(repository.save(prediction.getResult()));

	}

}
