package backend.app;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Paths;

public class TaskPersistence {
	static final String DIR_PATH = "./todoso/";
	static final String FILE_NAME = "todo.txt";

	public static Long write(Task task) throws IOException {
		File todo_txt = new File(DIR_PATH + FILE_NAME);

		if (Files.notExists(Paths.get(DIR_PATH), LinkOption.values())) {
			Files.createDirectories(Paths.get(DIR_PATH));
		}
		if (Files.notExists(Paths.get(DIR_PATH + FILE_NAME))) {
			todo_txt.createNewFile();
			FileWriter header = new FileWriter(todo_txt, true);
			header.append("todo.txt format\n");
			header.close();
		}
		
		if (task.getId() == null) {
			task.setId(Files.size(Paths.get(DIR_PATH + FILE_NAME)));
		}
		
		FileWriter writer = new FileWriter(todo_txt, true);
		writer.append(task.toString() + "\n");
		writer.close();
		
		return task.getId();
	}
}
