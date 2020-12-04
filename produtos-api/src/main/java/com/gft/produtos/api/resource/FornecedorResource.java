package com.gft.produtos.api.resource;

import java.util.List;
import java.util.Optional;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.produtos.api.event.RecursoCriadoEvent;
import com.gft.produtos.api.model.Fornecedor;
import com.gft.produtos.api.repository.FornecedorRepository;
import com.gft.produtos.api.repository.FornecedorminiRepository;
import com.gft.produtos.api.service.FornecedorService;


@RestController
@RequestMapping("/api/fornecedores")
public class FornecedorResource {

		
		@Autowired
		private FornecedorRepository fr;
		
		@Autowired
		private ApplicationEventPublisher pub;
		
		@Autowired
		private FornecedorService fs;
		
		@Autowired
		private FornecedorminiRepository fmr;
		
		
			
		//LISTAR FORNECEDORES
		@GetMapping
		public List<Fornecedor> listarFornecedores(){
		
			return fr.findAll();
		}

			
		//INSERIR FORNECEDORES
		@PostMapping
		public ResponseEntity<Fornecedor> cadastrarFornecedor(
				@Valid @RequestBody Fornecedor fornecedor, HttpServletResponse response) {
			
			Fornecedor fornecedorSalvo = fr.save(fornecedor);
			fs.salvaFornecedorMini(fornecedor);
				
			pub.publishEvent(new RecursoCriadoEvent(this, response, fornecedorSalvo.getCodigo()));
			return ResponseEntity.status(HttpStatus.CREATED).body(fornecedorSalvo);
				
			}
			
			
		//BUSCAR FORNECEDOR PELO ID
		@GetMapping("/{codigo}")
		public ResponseEntity<Fornecedor> buscaPeloCodigo(
				@PathVariable Long codigo) {
			
			Fornecedor fornecedor = fr.findById(codigo).orElse(null);
			return fornecedor != null ? ResponseEntity.ok(fornecedor) : ResponseEntity.notFound().build();
		}
		
		
		
		//ATUALIZAR FORNECEDOR
		@PutMapping("/{codigo}")
		public ResponseEntity<Fornecedor> atualizarFornecedor(
				@PathVariable Long codigo, 
				@Valid @RequestBody Fornecedor fornecedor){
				
			Fornecedor fornecedorAtualizado = fs.atualizar(codigo, fornecedor);
			return ResponseEntity.ok(fornecedorAtualizado);
				
		}
		

		//EXCLUIR FORNECEDOR
		@DeleteMapping("/{codigo}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void removerFornecedor(
					@PathVariable Long codigo) {
			
			fmr.deleteById(codigo);
				
			fr.deleteById(codigo);
		}
		
			
			
		//LISTAR FORNECEDORES ORDEM ALFA CRESC
		@GetMapping("/asc")
		public List<Fornecedor> ordernarAsc(){
			List<Fornecedor> asc = fr.findAllByOrderByNomeAsc();
			return asc;
		}
		
		
		//LISTAR FORNECEDORES ORDEM ALFA DRECR
		@GetMapping("/desc")
		public List<Fornecedor> ordernarDesc(){
			List<Fornecedor> desc = fr.findAllByOrderByNomeDesc();
			return desc;
		}
			
			
		//BUSCAR FORNECEDOR POR NOME
		@GetMapping("/nome/{nome}")
		public @ResponseBody List<Fornecedor> procuraPorNome(@PathVariable Optional<String> nome){
			if(nome.isPresent()) {
				return fr.findByNomeContaining(nome.get());
			}else{
				return fr.findAll(); //Não funciona
			}
	}
		
		
		
	
}
