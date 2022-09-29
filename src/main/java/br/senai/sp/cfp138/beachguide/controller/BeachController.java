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
import br.senai.sp.cfp138.beachguide.model.TipoPraia;
import br.senai.sp.cfp138.beachguide.repository.TipoPraiaRepository;

@Controller
public class BeachController {
	@Autowired
	private TipoPraiaRepository repository;
	
	@RequestMapping("cadPraia")
	private String cadPraias() {
		return "cadastro/formpraia";
	}
	
	@RequestMapping(value = "salvarPraia", method = RequestMethod.POST)
	public String salvarTipo(@Valid TipoPraia praia, BindingResult result, RedirectAttributes attr) {
	if (result.hasErrors()) {
	attr.addFlashAttribute("mensagemErro", "Verifique os campos...");
	return "redirect:cadPraia";
	}
	try {
	repository.save(praia);
	attr.addFlashAttribute("mensagemSucesso", "Tipo salvo com sucesso. ID:" + praia.getId());
	return "redirect:cadPraia";
	} catch (Exception e) {
	attr.addFlashAttribute("mensagemErro", "Houve um erro ao cadastrar o Tipo: " + e.getMessage());
	}
	return "redirect:cadPraia";
	}
	
	@RequestMapping("listarTipoPraias/{page}")
	public String listar(Model model, @PathVariable("page") int page) {
		PageRequest pageable = PageRequest.of(page-1, 7, Sort.by(Sort.Direction.ASC, "nome")); 
		Page<TipoPraia> paginas = repository.findAll(pageable);
		int totalPag =  paginas.getTotalPages();
		List<Integer> Numberspage = new ArrayList<Integer>(); 
		for(int i = 0; i < totalPag; i++) {
			Numberspage.add(i+1);
		}
		model.addAttribute("praias", paginas.getContent());
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPag);
		model.addAttribute("numPaginas", Numberspage);
		return "cadastro/listapraia";
	}
	@RequestMapping("excluirPraia")
	public String excluir(Long id){
		repository.deleteById(id);
		return "redirect:listarPraias/1";
	}
	@RequestMapping("alterarPraia")
	public String alterar(Long id, Model model) {
		TipoPraia beach = repository.findById(id).get();
		model.addAttribute("beach", beach);
		return "forward:cadPraia";
	}
	@RequestMapping("procurarPalavraChave")
	public String buscarPelaPChave(String palavrasChave, Model model) {
		model.addAttribute("praias", repository.procurarPalavraChave("%"+palavrasChave+"%"));
		return "/cadastro/listapraia";
	}
}
