package br.senai.sp.cfp138.beachguide.model;

import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

import lombok.Data;
@Entity
@Data
public class Praia {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	private String nome;
	@Column(columnDefinition = "TEXT")
	private String descricao;
	private String cep;
	private String numero;
	private String endereco;
	private String bairro;
	private String cidade;
	private String estado;
	private String complemento;
	@Column(columnDefinition = "TEXT")
	private String fotos;
	@ManyToOne
	private TipoPraia tipo;
	private String atracoes;
	private boolean guardaSol;
	private boolean quiosque;
	@OneToMany(mappedBy = "praia")
	private List<Avaliacao> avaliacoes;
	
	public String[] verFotos() {
		return this.fotos.split(";");
	}
	
}
