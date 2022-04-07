package br.senai.sp.cfp138.beachguide.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;

import br.senai.sp.cfp138.beachguide.model.TipoPraia;

public interface TipoPraiaRepository extends PagingAndSortingRepository<TipoPraia, Long> {
	@Query("SELECT g FROM TipoPraia g WHERE g.palavrasChave LIKE %:n%")
	public List<TipoPraia> procurarPalavraChave(@Param("n") String palavrasChave);
	
	public List<TipoPraia> findAllByOrderByNomeAsc();
}
