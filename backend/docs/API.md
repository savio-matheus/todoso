# Rascunho das APIs

## caminhos em /api/v1

### /tasks
* GET /tasks
* GET /tasks/:id
* POST /tasks
* PATCH /tasks/:id
* DELETE /tasks/:id

### /users
* GET /users
* GET /users/:id
* POST /users
* PATCH /users/:id
* DELETE /users/:id

### /categories
* GET /categories
* GET /categories/:id
* POST /categories
* PATCH /categories/:id
* DELETE /categories/:id

### /tags
* GET /tags
* GET /tags/:id
* POST /tags
* PATCH /tags/:id
* DELETE /tags/:id

### /files
* GET /file/:url
* POST /file
* DELETE /file/:url

### /session
* GET /session
* POST /session

## Queries
* length
* page
* id
* sort
* filtros (atributos do objeto correspondente, como title, creationDate etc.)

## JSON padrão da resposta
```
{
	"status": {...},

	// Conteúdo varia
	"page": 1..N,
	"data": [objetos]
}
```

## JSON padrão da requisição
```
{
	"auth": {...},
	"data": [objetos]
}
```

## Objetos

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
	"id": (integer),
	"title": (string (50)),

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

* userTasks
```
{
	"id": (integer),
	"username": (string),
	"tasks": ([objeto task1, task2, ...](50))
}
```

* categoryTasks
```
{
	"id": (integer),
	"name": (string),
	"tasks": ([objeto task1, task2, ...](50))
}
```

* tagTasks
```
{
	"id": (integer),
	"name": (string),
	"tasks": ([objeto task1, task2, ...](50))
}
```

* file
```
{
	"id": (integer),
	"url": (string),
	"type": (mimetype para imagens, texto, vídeo, música ou blob),
}
```

* status
```
{
	"code": N,
	"mensagem": "..."
}
```
