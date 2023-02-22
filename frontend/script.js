const url_endpoint = "http://127.0.0.1:8080/api/v1/tasks";

getdata();

// READ TASK DATA 
function getdata() {
    fetch(url_endpoint).then(
        (res)=>res.json()
    ).then((response)=>{
        var tmpData = "";
        console.log(response);
        response.data.forEach((task)=>{
            const dataJson = task.deadline;
            const data = new Date(dataJson);
            const dia = data.getDate().toString().padStart(2, '0');
            const mes = (data.getMonth() + 1).toString().padStart(2, '0');
            const ano = data.getFullYear();
            const dataFormatada = `${dia}/${mes}/${ano}`;

            if (task.priority > 1) {
                if (task.priority > 4) {
                    priori = "Alta"
                }
                else {
                    priori = "Média"
                }
            }
            else {
                priori = "Baixa"
            }

            tmpData += "<div class='card' id=" + task.id + " data-prioridade=" + priori + ">"
            tmpData += "<h3 class='titulo'>" + task.title.toUpperCase() + "</h3>";
            tmpData += "<p class='descricao'>" + task.description + "</p>";
            tmpData += "<p class='categoria'>" + task.categories[0].name + "</p>";
            tmpData += "<p class='data'>" + dataFormatada + "</p>";
            tmpData += "<p class='prioridade'>" + priori + "</p><br>";
            tmpData += "<div class='card-buttons'>";
            tmpData += "<button class='editar-button' onclick='editTask(" + task.id + ")'>Editar</button>";
            tmpData += "<button class='deletar-button' onclick='deleteTask(" + task.id + ")'>Deletar</button>";
            tmpData += "</div>";
            tmpData += "</div>";
        })
        document.getElementById("to_do").innerHTML = tmpData;
    })
}

// CREATE TASK DATA
function addTask() {
    titulo = document.getElementById("titulo").value;
    descricao = document.getElementById("descricao").value;
    data_limite = document.getElementById("data").value;
    prioridade  = document.getElementById("prioridade").value;
    categoria  = document.getElementById("categoria").value;

    let data = {
        "title": titulo,
        "id": 1,
        "description": descricao,
        "creationDate": "2023-02-20",
        "completionDate": "2023-02-20",
        "deadline": data_limite,
        "categories": [{
            "id": categoria,
            "name": "string"
        }],
        "tags": [{
            "id": 0,
            "name": "string"
        }],
        "priority": prioridade,
        "color": "#3AA",
        "files": [
        "string"
        ],
        "users": [
        {}
        ]
    }

    fetch(url_endpoint,{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body: JSON.stringify(data),
    }).then(response => response.json())
    .then(data => {
      console.log('Resposta da API:', data);
    })
    .catch(error => {
      console.error('Ocorreu um erro:', error);
    });
    getdata();
}


// UPDATE TASK DATA
function editTask(id) {
    titulo = document.getElementById("titulo").value;
    descricao = document.getElementById("descricao").value;
    data_limite = document.getElementById("data").value;
    prioridade  = document.getElementById("prioridade").value;
    categoria  = document.getElementById("categoria").value;
    
    let data = {
        "title": titulo,
        "id": 55,
        "description": descricao,
        "creationDate": "2023-02-20",
        "completionDate": "2023-02-20",
        "deadline": data_limite,
        "categories": [{
            "id": 0,
            "name": categoria
        }],
        "tags": [{
            "id": 0,
            "name": "string"
        }],
        "priority": prioridade,
        "color": "#3AA",
        "files": [
        "string"
        ],
        "users": [
        {}
        ]
    }

    let edit = ""
    edit = url_endpoint + "/" + id

    fetch(edit,{
        method:"PATCH",
        headers:{
            "Content-Type":"application/json"
        },
        body: JSON.stringify(data),
    }).then(response => response.json())
    .then(data => {
      console.log('Resposta da API:', data);
    })
    .catch(error => {
      console.error('Ocorreu um erro:', error);
    });
    getdata();
}


// DELETE TASK DATA
function deleteTask(id) {
    let del = ""
    del = url_endpoint + "/" + id
    fetch(del,{
        method:"DELETE",
        headers:{
            "Content-Type":"application/json"
        },
    }).then(response => response.json())
    .then(data => {
      console.log('Resposta da API:', data);
    })
    .catch(error => {
      console.error('Ocorreu um erro:', error);
    });
    getdata();
    getdata();
}


// ==================== CATEGORIES =========================== //
const url_categ = 'http://127.0.0.1:8080/api/v1/categories'; 

getCatData();

// GET CATEGORIES DATA
function getCatData() {
    fetch(url_categ).then(
        (res)=>res.json()
    ).then((response)=>{
        var tmpCatData = "";
        console.log(response);
        response.data.forEach((cat)=>{
			tmpCatData += "<option value='" + cat.id + "'>" + cat.name + "</option>";
        })
        document.getElementById("categoria").innerHTML = tmpCatData;
    })
}


// CREATE CATEGORIES DATA
function addCat() {
    nova_categoria  = document.getElementById("nova_cat").value;

    let data = {
        "id": 55,
        "name": nova_categoria,
    }

    fetch(url_categ,{
        method:"POST",
        headers:{
            "Content-Type":"application/json"
        },
        body: JSON.stringify(data),
    }).then(response => response.json())
    .then(data => {
      console.log('Resposta da API:', data);
    })
    .catch(error => {
      console.error('Ocorreu um erro:', error);
    });
    getCatData();
}