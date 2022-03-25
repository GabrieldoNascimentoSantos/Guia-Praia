package br.senai.sp.cfp138.beachguide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cfp138.beachguide.model.Administrador;
import br.senai.sp.cfp138.beachguide.repository.AdminRepository;

@Controller
public class AdminController {

	@Autowired
	private AdminRepository repository;
	
	// redireciona para o formulario
	@RequestMapping("cadAdm")
	public String adm() {
		return "adm/formadm";
	}
	
	@RequestMapping(value = "salvarAdministrador", method = RequestMethod.POST)
	public String salvarAdmin(@Valid Administrador admin, BindingResult result, RedirectAttributes attr) {
		//verifica se houve erro na validação do objeto
		if(result.hasErrors()) {
			// envia mensagem de erro via requisição 
			attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
		return "redirect:cadAdm";
		}
		try {
			//salva o adm
			repository.save(admin);
			attr.addFlashAttribute("mensagemSucesso", "Administrador cadastrado com sucesso. ID: "+admin.getId());
			return "redirect:cadAdm";
		} catch (Exception e) {
			attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o Administrador: " +e.getMessage());
		}
		return "redirect:cadAdm";
	}
	
	//request mapping para listar, informando a página desejada
	@RequestMapping("listarAdmin/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		// cria um pageable com 6 elementos por página, ordenando os objetos pelo nome, de forma ascendente
		PageRequest pageable = PageRequest.of(page-1, 6, Sort.by(Sort.Direction.ASC, "nome"));
		// cria a página atual através do repository 
		Page<Administrador> pagina = repository.findAll(pageable);
		// descobrir o total de páginas
		int totalPages =  pagina.getTotalPages();
		// cria uma lista de inteiros para representar as paginas 
		List<Integer> pageNumbers = new ArrayList<Integer>();
		// preecher a lista com as páginas 
		for(int i = 0; i < totalPages; i++) {
			pageNumbers.add(i+1);
		}
		//adiciona as variáveis na Model
		model.addAttribute("admins", pagina.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPages);
		model.addAttribute("numPaginas", pageNumbers);
		// retorna para o HTML da lista
		return "adm/lista";
	}
	
	@RequestMapping("excluirAdmin")
	public String excluir(Long id) {
		repository.deleteById(id);
		return "redirect:listarAdmin/1";
	}
	
	@RequestMapping("alterarAdmin")
	public String alterar(Long id, Model model) {
		Administrador admin = repository.findById(id).get();
		model.addAttribute("admin", admin);
		return "forward:cadAdm";
	}
	
	
}
