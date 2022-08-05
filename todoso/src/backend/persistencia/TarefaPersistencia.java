package backend.persistencia;

import backend.app.Tarefa;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;
import java.util.ArrayList;

public class TarefaPersistencia {
	static final String DIR_PATH = "./todoso/";
	static final String FILE_NAME = "todo.txt";

	public static Long write(Tarefa task) throws IOException {
		File todo_txt = new File(DIR_PATH + FILE_NAME);

		checkFile();

		if (task.getId() == null || task.getId() == Tarefa.NO_ID) {
			task.setId(Files.size(Paths.get(DIR_PATH + FILE_NAME)));
		}

		try (FileWriter writer = new FileWriter(todo_txt, true)) {
			writer.append(task.toString() + "\n");
		}
		
		return task.getId();
	}

	public static Tarefa read(Long id) throws FileNotFoundException, IOException {
		File todo_txt = new File(DIR_PATH + FILE_NAME);
		String str;
		String idToFind;
		try (BufferedReader bufRead = new BufferedReader(new FileReader(todo_txt))) {
			String idFound;
			idToFind = id.toString();
			while ((str = bufRead.readLine()) != null) {
				idFound = str.substring(0, str.indexOf(" "));
				if (idFound.equals(idToFind)) {
					break;
				}
			}
		}

		if (str == null) {
			return null;
		}
		System.out.println("TaskPersistence.java:42 - " + idToFind + " " + str);
		return fillTask(str);
	}

	private static void checkFile() throws IOException {
		File todo_txt = new File(DIR_PATH + FILE_NAME);
		if (Files.notExists(Paths.get(DIR_PATH), LinkOption.values())) {
			Files.createDirectories(Paths.get(DIR_PATH));
		}
		if (Files.notExists(Paths.get(DIR_PATH + FILE_NAME))) {
			todo_txt.createNewFile();
			try (FileWriter header = new FileWriter(todo_txt, true)) {
				header.append("todo.txt (adaptado)\n");
			}
		}
	}
	
	private static Tarefa fillTask(String strTodo) {
		Tarefa task = new Tarefa();
		String[] tokens = strTodo.split(" ");
		for (String i : tokens) {
			System.out.println(i);
		}
		for (int i = 0; i < tokens.length; i++) {
			System.out.println(i);
			switch (i) {
			case 0:
				task.setId(tokens[i]);
				break;
			case 1:
				if (tokens[i].equals("x")) {
					task.setDone(true);
				} else {
					task.setDone(false);
				}
				break;
			case 2:
				task.setPriority(tokens[i].substring(1, tokens[i].length()-1));
			case 3:
				if (!tokens[i].equals("null")) {
					task.setCompletionDate(tokens[i]);
				} else {
					task.setCompletionDate(null);
				}
				break;
			case 4:
				if (!tokens[i].equals("null")) {
					task.setCreationDate(tokens[i]);
				} else {
					task.setCreationDate(null);
				}
				break;
			case 5:
				// Descrição
				StringBuilder tmp = new StringBuilder();
				boolean isNull = false;
				if (tokens[i].equals("null")) {
					isNull = true;
				}
				while (!isNull) {
					tmp.append(tokens[i].replace("\"", "")).append(" ");
					
					if (tokens[i].endsWith("\"")) {
						break;
					}
					
					i++;
				}
				task.setDescription(tmp.toString());
				//System.out.println(task.getDescription());

				//System.out.println(task.getDescription());
			
			default:
				// Tags
				if (tokens[i].startsWith("+")) {
					ArrayList<String> tagList = new ArrayList<>();
					for (; tokens[i].startsWith("+"); i++) {
						tagList.add(tokens[i].substring(1));
					}
					task.setTags(tagList);
				}

				// Categorias
				if (tokens[i].startsWith("@")) {
					ArrayList<String> categoryList = new ArrayList<>();
					for (; tokens[i].startsWith("@"); i++) {
						categoryList.add(tokens[i].substring(1));
					}
					task.setCategories(categoryList);
				}
				System.out.println(i);
				
				// due:AAAA-MM-DD
				//     4
				if (!tokens[i].equals("due:null")) {
					task.setDeadline(tokens[i].substring(4));
				} else {
					task.setDeadline(null);
				}
			}
		}
		return task;
	}
}
