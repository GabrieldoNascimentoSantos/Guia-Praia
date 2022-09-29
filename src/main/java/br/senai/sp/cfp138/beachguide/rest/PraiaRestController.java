package br.senai.sp.cfp138.beachguide.rest;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import br.senai.sp.cfp138.beachguide.annotation.Publico;
import br.senai.sp.cfp138.beachguide.model.Praia;
import br.senai.sp.cfp138.beachguide.repository.PraiaRepository;

@RequestMapping("/api/praia")
@RestController
public class PraiaRestController {
	@Autowired
	private PraiaRepository repository;
	
	@Publico
	@RequestMapping(value="", method = RequestMethod.GET)
	public Iterable<Praia> getPraia(){
		return repository.findAll();
	}
	@Publico
	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	public ResponseEntity<Praia> findPraia(@PathVariable("id") Long id){
		// busca o restaurante
		Optional<Praia> praia = repository.findById(id);
		if(praia.isPresent()) {
			return ResponseEntity.ok(praia.get());
		}else {
			return ResponseEntity.notFound().build();
		}
	}
	@Publico
	@RequestMapping(value = "/tipo/{idTipo}", method = RequestMethod.GET)
	public List<Praia> getPraiaByTipo(@PathVariable("idTipo") Long idTipo){
		return repository.findByTipoId(idTipo);
	}
	
	@Publico
	@RequestMapping(value = "/quiosque/{quiosque}", method = RequestMethod.GET)
	public List<Praia> getPraiaByQuiosque(@PathVariable("quiosque") boolean quiosque){
		return repository.findByQuiosque(quiosque);
	}
	@Publico
	@RequestMapping(value = "/estado/{uf}", method = RequestMethod.GET)
	public List<Praia> getPraiaByEstado(@PathVariable("uf") String uf){
		return repository.findByEstado(uf);
	}
}
