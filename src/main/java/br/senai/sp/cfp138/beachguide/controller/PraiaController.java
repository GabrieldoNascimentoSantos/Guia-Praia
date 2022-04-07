package br.senai.sp.cfp138.beachguide.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cfp138.beachguide.model.Praia;
import br.senai.sp.cfp138.beachguide.repository.PraiaRepository;
import br.senai.sp.cfp138.beachguide.repository.TipoPraiaRepository;

@Controller
public class PraiaController {
	@Autowired
	private TipoPraiaRepository repTipo;
	@Autowired
	private PraiaRepository repPraia;
	
	@RequestMapping("formPraia")
	public String form(Model model) {
		model.addAttribute("tipos", repTipo.findAllByOrderByNomeAsc());
		return "praia/form";
	}
	@RequestMapping("praiaSalvar")
	public String salvar(Praia praia, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
	System.out.println(fileFotos.length);
		//repPraia.save(praia);
		return "redirect:formPraia";
	}
	
	
	
}
