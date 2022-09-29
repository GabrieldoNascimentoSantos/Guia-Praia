package br.senai.sp.cfp138.beachguide.rest;

import java.net.URI;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp138.beachguide.annotation.Privado;
import br.senai.sp.cfp138.beachguide.annotation.Publico;
import br.senai.sp.cfp138.beachguide.model.Avaliacao;
import br.senai.sp.cfp138.beachguide.repository.AvaliacaoRepository;

@RestController
@RequestMapping("/api/avaliacao")
public class AvaliacaoRestController {
	@Autowired
	private AvaliacaoRepository repository;

	@Privado
	@RequestMapping(value = "", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
	public ResponseEntity<Avaliacao> criarAvaliacao(@RequestBody Avaliacao avaliacao){
		repository.save(avaliacao);
		return ResponseEntity.created(URI.create("/avaliacao/"+avaliacao.getId())).body(avaliacao);
	}
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public Avaliacao getAvaliacao(@PathVariable("id") Long id) {
		return repository.findById(id).get();
	}
	@Publico
	@RequestMapping(value = "/praia/{idPraia}", method = RequestMethod.GET)
	public List<Avaliacao> ListaDePraia(@PathVariable("idPraia") Long idPraia){
		return repository.findByPraiaId(idPraia);
	}
}
