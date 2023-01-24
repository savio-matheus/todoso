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
* GET /users/:id/tasks
* GET /users/:id/categories
* GET /users/:id/tags
* POST /users
* PATCH /users/:id
* DELETE /users/:id

### /categories
* GET /categories
* GET /categories/:id
* GET /categories/:id/tasks
* POST /categories
* PATCH /categories/:id
* DELETE /categories/:id

### /tags
* GET /tags
* GET /tags/:id
* GET /tags/:id/tasks
* PATCH /tags/:id

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

## JSON da requisição (quando aplicável)
```
{
	"data": [objetos]
}
```
ou
```
{
	"data": {objeto}
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
	"http": N,
	"description": "...",
	"message": "..."
}
```
