package com.todoso.backend;

import java.util.ArrayList;
import java.time.LocalDate; // TODO: pesquisar java.time

class Task {

	private String title;
	private long id;
	private String description;
	private LocalDate creationDate;
	private LocalDate completionDate;
	private LocalDate deadline;
	private ArrayList<Category> categories;
	private ArrayList<Tag> tags;
	private int priority;
	private String color; // #FFFFFF
	private ArrayList<String> files;
	private ArrayList<User> users;

	public Task() {
	}

	public void setTitle(String title) {
		this.title = (title == null) ? "" : title;
	}

	public String getTitle() {
		return this.title;
	}

	public void setDescription(String description) {
		this.description = (description == null) ? "" : description;
	}

	public String getDescription() {
		return this.description;
	}

	public void setCreationDate(LocalDate creationDate) {
		this.creationDate = (creationDate == null)
			? LocalDate.now()
			: creationDate;
	}

	public LocalDate getCreationDate() {
		return this.creationDate;
	}

	public void setCompletionDate(LocalDate completionDate) {
		this.completionDate = completionDate;
	}

	/**
	 * @return data de término da tarefa ou null. */
	public LocalDate getCompletionDate() {
		return this.completionDate;
	}

	public void setDeadline(LocalDate deadline) {
		this.deadline = deadline;
	}

	/**
	 * @return data limite da tarefa ou null. */
	public LocalDate getDeadline() {
		return this.deadline;
	}

	public void setCategories(ArrayList<Category> list)	 {
		this.categories = (list == null)
			? new ArrayList<Category>()
			: list;
	}

	public ArrayList<Category> getCategories() {
		return this.categories;
	}

	public void setTags(ArrayList<Tag> list) {
		this.tags = (list == null)
			? new ArrayList<Tag>()
			: list;
	}

	public ArrayList<Tag> getTags() {
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

	public void setUsers(ArrayList<User> list) {
		this.users = (list == null)
			? new ArrayList<User>()
			: list;
	}

	public ArrayList<User> getUsers() {
		return this.users;
	}
}