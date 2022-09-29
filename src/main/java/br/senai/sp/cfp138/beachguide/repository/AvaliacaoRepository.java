package br.senai.sp.cfp138.beachguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.beachguide.model.Avaliacao;
import br.senai.sp.cfp138.beachguide.model.Praia;

public interface AvaliacaoRepository extends PagingAndSortingRepository<Avaliacao, Long>{
	public List<Avaliacao> findByPraiaId(Long idPraia);

}
