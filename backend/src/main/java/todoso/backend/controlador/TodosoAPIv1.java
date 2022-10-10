package todoso.backend.controlador;


import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import todoso.backend.dados.BancoDeDados;

@RestController
class TodosoAPIv1 {

	@GetMapping("/api/v1/tasks/{id}")
	public ResponseEntity<List> getTasks(@PathVariable("id") long id) {
		ArrayList<Task> tasks = new ArrayList<>();
		Task t = new Task();
		
		BancoDeDados bd = BancoDeDados.abrir();
		if (bd == null) {
			ArrayList<String> str = new ArrayList<>();
			return new ResponseEntity<>(str, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		bd.fechar();
		
		t.setId(id);
		tasks.add(t);
		tasks.add(t);
		
		return new ResponseEntity<>(tasks, HttpStatus.FOUND);
	}
}