package com.gft.controlados.api.resource;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.gft.controlados.api.event.RecursoCriadoEvent;
import com.gft.controlados.api.model.Medicamento;
import com.gft.controlados.api.repository.MedicamentoRepository;
import com.gft.controlados.api.service.MedicamentoService;
	
	@RestController //Rest porque já transforma pra JASON
	@RequestMapping("/medicamentos")	
public class MedicamentoResource {

		@Autowired
		private MedicamentoRepository mr;
		
		@Autowired
		private MedicamentoService ms;
		
		@Autowired
		private ApplicationEventPublisher pub;
		
		@GetMapping
		public List<Medicamento> listarMedicamentos(){
			
			return mr.findAll();
		}
		
		
		@PostMapping
		public ResponseEntity<Medicamento> cadastrarMedicamento(@RequestBody Medicamento medicamento, HttpServletResponse response) {
			Medicamento medicamentoSalvo = mr.save(medicamento);
			
			pub.publishEvent(new RecursoCriadoEvent(this, response, medicamentoSalvo.getCodigo()));
			return ResponseEntity.status(HttpStatus.CREATED).body(medicamentoSalvo);
			
		}
		
		@GetMapping("/{codigo}")
		public ResponseEntity<Medicamento> buscaPeloCodigo(
				//@ApiParam(value="ID da categoria", example = "5")
				@PathVariable Long codigo) {
			Medicamento medicamento = mr.findById(codigo).orElse(null);
			
			return medicamento != null ? ResponseEntity.ok(medicamento) : ResponseEntity.notFound().build();
		}
		
		@PutMapping("/{codigo}")
		public ResponseEntity<Medicamento> atualizarMedicamento(
				//@ApiParam(value="ID da pessoa", example = "11")
				@PathVariable Long codigo, 
				
				//@ApiParam(name = "Corpo", value = "Representação da pessoa com os dados atualizados")
				@Valid @RequestBody Medicamento medicamento){
			
			Medicamento medicamentoAtualizado = ms.atualizar(codigo, medicamento);
			return ResponseEntity.ok(medicamentoAtualizado);
			
		}
		
		@DeleteMapping("/{codigo}")
		@ResponseStatus(HttpStatus.NO_CONTENT)
		public void removerMedicamento(
				//@ApiParam(value="ID da pessoa", example = "11")
				@PathVariable Long codigo) {
			
			mr.deleteById(codigo);
		}
	
}
