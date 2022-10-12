package todoso.backend.controlador;


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

@RestController
class TodosoAPIv1 {

	@GetMapping("/api/v1/tasks/{id}")
	public ResponseEntity<List> getTasks(@PathVariable("id") long id) {
		ArrayList<TaskDTO> tasks = new ArrayList<>();
		TaskDTO t = new TaskDTO();
		
		t.setId(id);
		tasks.add(t);
		tasks.add(t);

		return new ResponseEntity(tasks, HttpStatus.FOUND);
	}
}