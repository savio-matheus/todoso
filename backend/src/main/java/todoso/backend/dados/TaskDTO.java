package todoso.backend.dados;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;

public class TaskDTO {

	private String title;
	private Long id;
	private String description;
	private Timestamp creationDate;
	private Timestamp completionDate;
	private Timestamp deadline;
	private ArrayList<CategoryDTO> categories;
	private ArrayList<TagDTO> tags;
	private Integer priority;
	private String color; // #FFFFFF
	private ArrayList<String> files;
	private ArrayList<UserDTO> users;

	public TaskDTO() {
		setId(null);
		setTitle(null);
		setDescription(null);
		setCreationDate(null);
		setCompletionDate(null);
		setDeadline(null);
		setCategories(null);
		setTags(null);
		setPriority(null);
		setColor(null);
		setFiles(null);
		setUsers(null);
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getTitle() {
		return this.title;
	}
	
	public void setId(Long id) {
		this.id = id;
	}
	
	public Long getId() {
		return this.id;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setCreationDate(Timestamp creationDate) {
		this.creationDate = creationDate;
	}

	public Timestamp getCreationDate() {
		return this.creationDate;
	}

	public void setCompletionDate(Timestamp completionDate) {
		this.completionDate = completionDate;
	}

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
		this.categories = list;
	}

	public ArrayList<CategoryDTO> getCategories() {
		return this.categories;
	}

	public void setTags(ArrayList<TagDTO> list) {
		this.tags = list;
	}

	public ArrayList<TagDTO> getTags() {
		return this.tags;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}

	public Integer getPriority() {
		return this.priority;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public String getColor() {
		return this.color;
	}

	public void setFiles(ArrayList<String> list) {
		this.files = list;
	}

	public ArrayList<String> getFiles() {
		return this.files;
	}

	public void setUsers(ArrayList<UserDTO> list) {
		this.users = list;
	}

	public ArrayList<UserDTO> getUsers() {
		return this.users;
	}
}