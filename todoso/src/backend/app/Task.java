package backend.app;

import java.util.ArrayList;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;

public class Task {
	static final int MAX_TITLE_SIZE = 50;
	static final int NO_PRIORITY = 0;
	static final long NO_ID = -1;
	
	private String title; // 50 chars
	private Long id;
	private String description;
	private LocalDate creationDate;
	private LocalDate completionDate;
	private LocalDate deadline;
	private ArrayList<String> categories;
	private ArrayList<String> tags;
	private Integer priority;
	private String color; // hex: #FFFFFF
	private boolean done;

	public Task() {}
	
	public Task(String title, Long id, String description, String creationDate,
			String completionDate, String deadline, ArrayList<String> categories,
			ArrayList<String> tags, Integer priority, String color, boolean done) {

		super();
		setTitle(title);
		setId(id);
		setDescription(description);
		setCompletionDate(completionDate);
		setCreationDate(creationDate);
		setDeadline(deadline);
		setCategories(categories);
		setTags(tags);
		setPriority(priority);
		setColor(color);
		setDone(done);
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		if (title.length() > MAX_TITLE_SIZE) {
			title = title.substring(0, 50);
		}
		
		this.title = title.trim();
	}

	public Long getId() {
		return id;
	}

	public void setId(String id) {
		try {
			this.id = new Long(id);
		} catch (NumberFormatException e) {
			this.id = NO_ID;
			//System.out.println(e);
			//System.out.println("id = NO_ID");
		}
	}
	
	public void setId(Long id) {
		this.id = id;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public LocalDate getCreationDate() {
		return creationDate;
	}

	public void setCreationDate(String creationDate) {
		try {
			// Considerando o formato "AAAA-MM-DD" (ISO_LOCAL_DATE)
			this.creationDate = LocalDate.parse(creationDate);
		} catch (DateTimeParseException e) {
			this.creationDate = LocalDate.now();
		}
	}

	public LocalDate getDeadline() {
		return deadline;
	}
	
	public void setDeadline(String deadline) {
		try {
			// Considerando o formato "AAAA-MM-DD" (ISO_LOCAL_DATE)
			this.deadline = LocalDate.parse(deadline);
		} catch (DateTimeParseException e) {
			this.deadline = null;
		}
	}

	public ArrayList<String> getCategories() {
		return this.categories;
	}

	public void setCategories(ArrayList<String> categories) {
		this.categories = categories;
	}

	public ArrayList<String> getTags() {
		return tags;
	}

	public void setTags(ArrayList<String> tags) {
		this.tags = tags;
	}

	public Integer getPriority() {
		if (priority == null) {
			return NO_PRIORITY;
		}
		return priority;
	}

	public void setPriority(Integer priority) {
		this.priority = priority;
	}
	
	public void setPriority(String priority) {
		try {
			this.priority = new Integer(priority);
		} catch (NumberFormatException e) {
			this.priority = NO_PRIORITY;
		}
	}

	public String getColor() {
		return color;
	}

	public void setColor(String color) {
		this.color = color;
	}
	
	public LocalDate getCompletionDate() {
		return completionDate;
	}
	
	public void setCompletionDate(String completionDate) {
		try {
			// Considerando o formato "AAAA-MM-DD" (ISO_LOCAL_DATE)
			this.completionDate = LocalDate.parse(completionDate);
		} catch (DateTimeParseException e) {
			this.completionDate = null;
		}
	}

	public boolean isDone() {
		return done;
	}

	public void setDone(boolean done) {
		this.done = done;
	}
	
	@Override
	public String toString() {
		StringBuffer strBuf = new StringBuffer();
		strBuf.append(getId() + " ");
		if (isDone()) {
			strBuf.append("x" + " ");
		}
		strBuf.append("(" + getPriority() + ")" + " ");
		strBuf.append(getCompletionDate() + " ");
		strBuf.append(getCreationDate() + " ");
		strBuf.append(getDescription() + " ");
		for (String i : getTags()) {
			strBuf.append("+" + i + " ");
		}
		for (String i : getCategories()) {
			strBuf.append("@" + i + " ");
		}
		strBuf.append("due:" + getDeadline());

		System.out.println(strBuf);
		return strBuf.toString();
	}
}
