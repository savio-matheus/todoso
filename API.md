# Rascunho das APIs

## v1 /task

* GET /task/{:id}
```
{
	"title": string -> 50 chars
	"id": integer
	"description": string
	"creationDate": string -> aaaa/mm/dd
	"completionDate": string -> aaaa/mm/dd
	"deadline": string -> aaaa/mm/dd
	"categories": string1, string2, ...
	"tags": string1, string2, ...
	"priority": integer
	"color": string -> cor em hexadecimal
	"done": true || false
}
```

* POST /task
```
{
	"title": string -> 50 chars (obrigatório)
	"description": string
	"creationDate": string -> aaaa/mm/dd
	"completionDate": string -> aaaa/mm/dd
	"deadline": string -> aaaa/mm/dd
	"categories": string1, string2, ...
	"tags": string1, string2, ...
	"priority": integer
	"color": string -> cor em hexadecimal
}
```