package br.senai.sp.cfp138.beachguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.beachguide.model.Administrador;

public interface AdminRepository extends PagingAndSortingRepository<Administrador, Long>{
	
}
