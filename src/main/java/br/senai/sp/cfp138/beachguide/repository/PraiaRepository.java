package br.senai.sp.cfp138.beachguide.repository;

import java.util.List;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.beachguide.model.Praia;

public interface PraiaRepository extends PagingAndSortingRepository<Praia, Long>{
	public List<Praia> findByTipoId(Long idTipo);
	
	public List<Praia> findByQuiosque(boolean quiosque);
	
	public List<Praia> findByEstado(String uf);
}
