package br.senai.sp.cfp138.beachguide.repository;

import org.springframework.data.repository.PagingAndSortingRepository;

import br.senai.sp.cfp138.beachguide.model.Usuario;

public interface UsuarioRepository extends PagingAndSortingRepository<Usuario, Long> {
	public Usuario findByEmailAndSenha(String email, String senha);

	
}
