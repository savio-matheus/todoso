# Rascunho das APIs

## caminhos em /todoso-backend/api/v1

### /tasks
* GET /tasks/:id
* GET /tasks/list (suporta query)
* POST /tasks
* POST /tasks/:id/delete
* POST, PUT /tasks/:id
* DELETE /tasks/:id

### /users
* GET /users/:id
* POST /users
* POST /users/:id/delete
* POST, PUT /users/:id
* DELETE /users/:id

### /categories
* GET /categories/:id
* GET /categories/list (suporta query)
* POST /categories
* POST /categories/:id/delete
* POST, PUT /categories/:id
* DELETE /categories/:id

### /tags
* GET /tags/:id
* GET /tags/list (suporta query)
* POST /tags
* POST /tags/:id/delete
* POST, PUT /tags/:id
* DELETE /tags/:id

### /files
* GET /file/:id
* GET /file/:url

### /session
* GET /session
* POST /session

## Queries

## JSON padrão da resposta
```
{
	"meta" {
		"types": [("tasks", "users", "categories", "tags", "auth", "notifications", "error",
			"userTasks", "categoryTasks", "tagTasks", "files")],
		"context": {
			"message": (string),
			"code": (código HTTP),
			"codeMessage": (significado do código HTTP)
		},
		"auth": {...}
	},

	// Varia a cada resposta. Observe os "types".
	"data": {
		"tasks": {
			"count": (integer),
			"start": (integer),
			[{...}]
		},
		"users": {
			"count": (integer),
			"start": (integer),
			[{...}]
		},
		"categories": {
			"count": (integer),
			"start": (integer),
			[{...}]
		},
		"tags": {
			"count": (integer),
			"start": (integer),
			[{...}]
		},
		"notifications": {
			"count": (integer),
			"start": (integer),
			[{...}]
		},
		"userTasks": {
			"count": (integer),
			"start": (integer),
			[{...}]
		},
		"categoryTasks": {
			"count": (integer),
			"start": (integer),
			[{...}]
		},
		"tagTasks": {
			"count": (integer),
			"start": (integer),
			[{...}]
		},
		"files": {
			"count": (integer),
			"start": (integer),
			[{...}]
		},
		"error": {}
	}
}
```

* auth
```
{
	"token": (string),
	"username": (string),
	"email": (string),
	"password": (string)
}
```

* task
```
{
	"title": (string (50)),
	"id": (integer),

	// varia
	"description": (string (8192)),
	"creationDate": (string -> aaaa/mm/dd),
	"completionDate": (string -> aaaa/mm/dd),
	"deadline": (string -> aaaa/mm/dd),
	"categories": (objeto category1, category2, ...),
	"tags": (objeto tag1, tag2, ...),
	"priority": (integer),
	"color": (string (7) (ex.: #FFFFFF)),
	"files": (objeto file),
	"users": ([objeto user1, user2, ...])
}
```

* user
```
{
	"id": (integer),
	"username": (string),
	"deleted": true | false,

	// varia
	"email": (string),
	"image": (objeto file),
	"recentTasks": ([objeto task1, task2, ...](25)),
	"creationDate": (string),
	"lastLogin": (string)
}
```

* category
```
{
	"id": (integer),
	"name": (string)
}
```

* tag
```
{
	"id": (integer),
	"name": (string)
}
```

* notification
```
{
	"type": "sucess" | "warning" | "error" | "alert",
	"message": (string)
}
```

* userTasks
```
{
	"username": (string),
	"id": (integer),
	"taskList": ([objeto task1, task2, ...](50))
}
```

* categoryTasks
```
{
	"name": (string),
	"id": (integer),
	"taskList": ([objeto task1, task2, ...](50))
}
```

* tagTasks
```
{
	"name": (string),
	"id": (integer),
	"taskList": ([objeto task1, task2, ...](50))
}
```

* file
```
{
	"id": (integer),
	"url": (string),
	"type": (mimetype para imagens, texto, vídeo, música ou blob),
	"base64": (stringBase64, para imagens)
}
```

* error
```
{}
```
