package br.senai.sp.cfp138.beachguide.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import br.senai.sp.cfp138.beachguide.model.Praia;
import br.senai.sp.cfp138.beachguide.model.TipoPraia;
import br.senai.sp.cfp138.beachguide.repository.PraiaRepository;
import br.senai.sp.cfp138.beachguide.repository.TipoPraiaRepository;
import br.senai.sp.cfp138.beachguide.util.FirebaseUtil;

@Controller
public class PraiaController {
	@Autowired
	private TipoPraiaRepository repTipo;
	@Autowired
	private PraiaRepository repPraia;
	@Autowired
	private FirebaseUtil firebaseUtil;
	
	@RequestMapping("formPraia")
	public String form(Model model) {
		model.addAttribute("tipos", repTipo.findAllByOrderByNomeAsc());
		return "praia/form";
	}
	@RequestMapping("praiaSalvar")
	public String salvar(Praia praia, @RequestParam("fileFotos") MultipartFile[] fileFotos) {
		// String para a url das fotos
		String fotos = praia.getFotos();
		// percorrer cada arquivo que foi submetido no formúlario
		for(MultipartFile arquivo : fileFotos) {
			// verificar se o arwuivo está vazio
			if(arquivo.getOriginalFilename().isEmpty()) {
				//vai para o próximo arquivo
				continue;
			}
			//faz o upload para a nuvem e obtém a url gerada
			try {
				fotos += firebaseUtil.uploadFile(arquivo)+";";
			} catch (IOException e) {
				e.printStackTrace();
				throw new RuntimeException(e);
			}
		}
		//atribui a String fotos ao objeto Praia
		praia.setFotos(fotos);
		repPraia.save(praia);
		return "redirect:formPraia";
	}
	
	@RequestMapping("listarPraias/{page}")
	public String listar(Model model,  @PathVariable("page") int page) {
		PageRequest pageable = PageRequest.of(page-1, 7, Sort.by(Sort.Direction.ASC, "nome")); 
		Page<Praia> paginas = repPraia.findAll(pageable);
		int totalPag =  paginas.getTotalPages();
		List<Integer> Numberspage = new ArrayList<Integer>(); 
		for(int i = 0; i < totalPag; i++) {
			Numberspage.add(i+1);
		}
		model.addAttribute("paginaAtual", page);
		model.addAttribute("totalPaginas", totalPag);
		model.addAttribute("numPaginas", Numberspage);
		model.addAttribute("prainha", repPraia.findAll());
		return "listarpraia/lista";
	}
	@RequestMapping("praiaAlterar")
	public String alterar(Long id, Model model) {
		Praia praia = repPraia.findById(id).get();
		model.addAttribute("praia", praia);
		return "forward:formPraia";
	}
	@RequestMapping("excluirFotoPraia")
	public String excluirFoto(Long idPraia, int numFoto, Model model) {
		// busca o restaurante no banco de dados
		Praia rest = repPraia.findById(idPraia).get();
		// pegar a String da foto a ser excluída
		String fotoUrl = rest.verFotos()[numFoto];
		// excluir do firebase usar // esse no metodo para excluir praia
		firebaseUtil.deletar(fotoUrl);
		// "arranca" a foto da string fotos
		rest.setFotos(rest.getFotos().replace(fotoUrl+";", ""));
		//salva no BD o objeto rest
		repPraia.save(rest);
		// adiciona o rest na model
		model.addAttribute("praia", rest);
		// encaminhar para o form
		return "forward:formPraia";
		}
	@RequestMapping("praiaExcluir")
	public String excluirPraia(Long id) {
		Praia praia = repPraia.findById(id).get();
		if(praia.getFotos().length() > 0) {
			for(String foto : praia.verFotos()) {
				firebaseUtil.deletar(foto);
			}
		}	
		repPraia.delete(praia);
		return "redirect:listarPraias/1";
	}
}
