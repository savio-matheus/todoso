package todoso.backend.controlador;


import java.sql.SQLException;
import todoso.backend.dados.TaskDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PathVariable;
import todoso.backend.dados.Tarefas;

@RestController
class TodosoAPIv1 {
	
	@GetMapping("/api/v1/tasks")
	public ResponseEntity<List> getTasks() {
		return getTasks(null);
	}

	@GetMapping("/api/v1/tasks/{id}")
	public ResponseEntity<List> getTasks(@PathVariable("id") Long id) {
		ArrayList<TaskDTO> lista = new ArrayList<>();
		TaskDTO t = new TaskDTO();
		
		t.setId(id);
		
		try {
			lista = Tarefas.listar(t, null, null);
		} catch (SQLException e) {
			System.out.println("TODO: enviar mensagem de erro.");
			e.printStackTrace();
		}

		for (TaskDTO t2 : lista) {
			System.out.println(t2.getTitle());
		}

		return new ResponseEntity(lista, HttpStatus.FOUND);
	}
}