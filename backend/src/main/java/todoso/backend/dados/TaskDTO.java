package todoso.backend.dados;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class TaskDTO {

	private String title = "";
	private long id;
	private String description;
	private Timestamp creationDate = Timestamp.from(Instant.now());
	private Timestamp completionDate;
	private Timestamp deadline;
	private ArrayList<CategoryDTO> categories;
	private ArrayList<TagDTO> tags;
	private int priority;
	private String color; // #FFFFFF
	private ArrayList<String> files;
	private ArrayList<UserDTO> users;

	public TaskDTO() {
	}

	public void setTitle(String title) {
		this.title = (title == null) ? "" : title;
	}

	public String getTitle() {
		return this.title;
	}
	
	public void setId(long id) {
		this.id = id;
	}
	
	public long getId() {
		return this.id;
	}

	public void setDescription(String description) {
		this.description = (description == null) ? "" : description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = (creationDate == null)
			? Timestamp.from(Instant.now())
			: creationDate;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCompletionDate(Timestamp completionDate) {
		this.completionDate = completionDate;
	}

	/**
	 * @return data de término da tarefa ou null. */
	public Timestamp getCompletionDate() {
		return this.completionDate;
	}

	public void setDeadline(Timestamp deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return data limite da tarefa ou null. */
	public Timestamp getDeadline() {
		return this.deadline;
	}

	public void setCategories(ArrayList<CategoryDTO> list)	 {
		this.categories = (list == null)
			? new ArrayList<>()
			: list;
	}

	public ArrayList<CategoryDTO> getCategories() {
		return this.categories;
	}

	public void setTags(ArrayList<TagDTO> list) {
		this.tags = (list == null)
			? new ArrayList<TagDTO>()
			: list;
	}

	public ArrayList<TagDTO> getTags() {
		return this.tags;
	}

	public void setPriority(int priority) {
		this.priority = priority;
	}

	public int getPriority() {
		return this.priority;
	}

	public void setColor(String color) {
		this.color = (color == null) ? "" : color;
	}

	public String getColor() {
		return this.color;
	}

	public void setFiles(ArrayList<String> list) {
		this.files = (list == null)
			? new ArrayList<String>()
			: list;
	}

	public ArrayList<String> getFiles() {
		return this.files;
	}

	public void setUsers(ArrayList<UserDTO> list) {
		this.users = (list == null)
			? new ArrayList<UserDTO>()
			: list;
	}

	public ArrayList<UserDTO> getUsers() {
		return this.users;
	}
}