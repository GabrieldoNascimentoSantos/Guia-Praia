package br.senai.sp.cfp138.beachguide.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import br.senai.sp.cfp138.beachguide.util.HashUtil;
import lombok.Data;

// para gerar o get e set
@Data
// para mapear como uma entidade JPA
@Entity
public class Administrador {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	// chave primária e auto-increment
	private Long id;
	@NotEmpty
	private String nome;
	@Email
	@Column(unique = true)
	private String email;
	@NotEmpty
	private String senha;
	
	// metodo para setar a senha aplicando o hash
	public void setSenha(String senha) {
		// aplica o hash e seta a senha no objeto
		this.senha = HashUtil.hash256(senha);
	}
	// metodo para "setar" a senha sem aplicar o hash
	public void setSenhaComHash(String hash) {
		// "seta" o hash na senha
		this.senha = hash;
	}
}
