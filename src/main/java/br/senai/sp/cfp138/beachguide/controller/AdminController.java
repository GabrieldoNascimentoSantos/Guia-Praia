package br.senai.sp.cfp138.beachguide.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpSession;
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
import br.senai.sp.cfp138.beachguide.util.HashUtil;

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
		// verifica se esta sendo feita uma alteração ao invés de uma inserção
		boolean alteracao = admin.getId() != null ? true : false;
		// verifica se a senha está vazia
		if(admin.getSenha().equals(HashUtil.hash256(""))){
			// se não for alteração, eu defino a primeira parte do email como a senha
			if(!alteracao) {
				// extrair a parte do email antes do @
				String parte = admin.getEmail().substring(0, admin.getEmail().indexOf("@"));
				// define a senha do admin
				admin.setSenha(parte);
			}else {
				// busca a senha atual
				String hash = repository.findById(admin.getId()).get().getSenha();
				// "seta a senha com hash"
				admin.setSenhaComHash(hash);
			}
		}
		try {
			//salva o adm
			repository.save(admin);
			attr.addFlashAttribute("mensagemSucesso", "Administrador salvo com sucesso. ID: "+admin.getId());
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
	
	@RequestMapping("logout")
	public String logou(HttpSession session) {
		// elimina o usuario da session
		session.invalidate();
		// retorna para a página inicial
		return "redirect:/";
	}
	
	@RequestMapping("telaLogin")
	public String login(Administrador admLogin, RedirectAttributes attr, HttpSession session) {
		// buscar o administrador do BD, através do email e senha
		Administrador admin = repository.findByEmailAndSenha(admLogin.getEmail(), admLogin.getSenha());
		// verifica se existe o admin
		if (admin == null) {
			// se for nulo, avisa ao usuário
			attr.addFlashAttribute("mensagemErro", "Login e/ou senha inválido(s)");
			return "redirect:/";
		}else {
			// se não for nulo, salva na sessão e acessa o sistema
			session.setAttribute("usuarioLogado", admin);
			return "redirect:listarAdmin/1";
		}
	}
	
	@RequestMapping("alterarAdmin")
	public String alterar(Long id, Model model) {
		Administrador admin = repository.findById(id).get();
		model.addAttribute("admin", admin);
		return "forward:cadAdm";
	}
	@RequestMapping("/")
	public String index() {
		return "index/index";
	}
	
	
}
