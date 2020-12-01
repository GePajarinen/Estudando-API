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
import com.gft.produtos.api.model.Venda;
import com.gft.produtos.api.repository.VendaRepository;
import com.gft.produtos.api.service.VendaService;

@RestController
@RequestMapping("/api/vendas")
public class VendaResource {

		
		@Autowired
		private VendaRepository vr;
		
		@Autowired
		private ApplicationEventPublisher pub;
		
		@Autowired
		private VendaService vs;

		
		
		//LISTAR VENDAS
		@GetMapping
		public List<Venda> listarVendas(){
			return vr.findAll();
		}

			
		//INSERIR VENDAS
		@PostMapping
		public ResponseEntity<Venda> cadastrarVenda(
				@Valid @RequestBody Venda venda, HttpServletResponse response) {
						
			Venda vendaSalva = vr.save(venda);
				
			pub.publishEvent(new RecursoCriadoEvent(this, response, vendaSalva.getCodigo()));
			return ResponseEntity.status(HttpStatus.CREATED).body(vendaSalva);
				
		}
			
			
		//BUSCAR VENDA PELO ID
		@GetMapping("/{codigo}")
		public ResponseEntity<Venda> buscaPeloCodigo(
				@PathVariable Long codigo) {
			
			Venda venda = vr.findById(codigo).orElse(null);
			return venda != null ? ResponseEntity.ok(venda) : ResponseEntity.notFound().build();
		}
		
		
		
		//ATUALIZAR VENDA
			@PutMapping("/{codigo}")
			public ResponseEntity<Venda> atualizarVenda(
					@PathVariable Long codigo, 
					@Valid @RequestBody Venda venda){
				
				Venda vendaAtualizada = vs.atualizar(codigo, venda);
				return ResponseEntity.ok(vendaAtualizada);
				
			}
		

		//EXCLUIR VENDA
			@DeleteMapping("/{codigo}")
			@ResponseStatus(HttpStatus.NO_CONTENT)
			public void removerVenda(
					@PathVariable Long codigo) {
				
				vr.deleteById(codigo);
			}
		
			
			
		//LISTAR VENDAS ORDEM ALFA CRESC
		@GetMapping("/asc")
		public List<Venda> ordernarAsc(){
			List<Venda> asc = vr.findAllByOrderByCodigoAsc();
			return asc;
		}
		
		
		//LISTAR VENDAS ORDEM ALFA DRECR
		@GetMapping("/desc")
		public List<Venda> ordernarDesc(){
			List<Venda> desc = vr.findAllByOrderByCodigoDesc();
			return desc;
		}
			
			
		//BUSCAR VENDA POR NOME
		@GetMapping("/codigo/{codigo}")
		public @ResponseBody List<Venda> procuraPorNomeCliente(@PathVariable Optional<Long> codigo){
			if(codigo.isPresent()) {
				return vr.findByCodigoContaining(codigo.get());
			}else{
				return vr.findAll(); //Não funciona
			}
	}
		
		
	
	
}
