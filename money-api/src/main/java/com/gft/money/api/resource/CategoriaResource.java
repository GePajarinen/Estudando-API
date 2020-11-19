package com.gft.money.api.resource;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.gft.money.api.event.RecursoCriadoEvent;
import com.gft.money.api.model.Categoria;
import com.gft.money.api.repository.CategoriaRepository;


@RestController
@RequestMapping("/categorias")
public class CategoriaResource {
	
	@Autowired
	private CategoriaRepository cr;
	
	@Autowired
	private ApplicationEventPublisher pub;
	
	@GetMapping
	public List<Categoria> listarCategorias(){
		return cr.findAll();
		
	}
	
	@PostMapping
	public ResponseEntity<Categoria> criarCategoria(@Valid @RequestBody Categoria categoria, HttpServletResponse response) {
		Categoria categoriaSalva = cr.save(categoria);
		
		pub.publishEvent(new RecursoCriadoEvent(this, response, categoriaSalva.getCodigo()));
		return ResponseEntity.status(HttpStatus.CREATED).body(categoriaSalva);
		
	}
	
	@GetMapping("/{codigo}")
	public ResponseEntity<Categoria> buscaPeloCodigo(@PathVariable Long codigo) {
		Categoria categoria = cr.findById(codigo).orElse(null);
		
		return categoria != null ? ResponseEntity.ok(categoria) : ResponseEntity.notFound().build();
	}

}
