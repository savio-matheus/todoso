const base_url = "http://localhost:8080";
const url_endpoint = base_url + "/api/v1/tasks";

getdata();

// READ TASK DATA 
function getdata() {
    fetch(url_endpoint).then(
        (res)=>res.json()
    ).then((response)=>{
        var tmpData = "";
        //console.log(response);
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
            tmpData += "<button class='anexar-button' onclick='editTask(" + task.id + ")'>Anexar arquivo</button>";
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
        "creationDate": new Date(),
        "deadline": data_limite,
        "categories": [{
            "id": categoria,
            "name": "string"
        }],
        "priority": prioridade,
        "color": "#3AA"
    }
    console.log(data);

    genericPost(url_endpoint, data);
}

function genericPost(url, data) {
    event.preventDefault();
    return fetch(url, {
        method: 'POST',
        headers: {
            'Accept': 'application/json, text/plain, */*',
            'Content-Type': 'application/json'
        },
        body: JSON.stringify(data),
        mode: 'cors'
    })
    .then(response => {
        response.json();
        getdata();
        getCatData();
    })
    .catch(error => console.error(error));
}

function genericDelete(url) {
    return fetch(url, {
        method: 'DELETE'
    })
    .then(response => {
        response.json();
        getdata();
        getCatData();
    })
    .catch(error => console.error(error));
}

function genericGet(url) {
    return fetch(url, {
      method: 'GET'
    })
    .then(response => {
        response.json();
    })
    .catch(error => console.error(error));
}

function genericPatch(url, data) {
    const options = {
      method: 'PATCH',
      headers: { 'Content-Type': 'application/json' },
      body: JSON.stringify(data)
    };
  
    return fetch(url, options)
    .then(response => {
        response.json();
        getdata();
        getCatData();
    })
    .catch(error => console.error(error));
}

// UPDATE TASK DATA
function editTask(id) {
    titulo = document.getElementById("titulo").value;
    descricao = document.getElementById("descricao").value;
    data_limite = document.getElementById("data").value;
    prioridade  = document.getElementById("prioridade").value;
    categoria  = document.getElementById("categoria").value;
    
    let editdata = {
        "title": titulo,
        "id": id,
        "description": descricao,
        "creationDate": new Date(),
        "deadline": data_limite,
        "categories": [{
            "id": 0,
            "name": categoria
        }],
        "priority": prioridade,
        "color": "#3AA"
    }

    let edit = ""
    edit = url_endpoint + "/" + id

    genericPatch(url, editdata);
}


// DELETE TASK DATA
function deleteTask(id) {
    let del = ""
    del = url_endpoint + "/" + id
    genericDelete(del);
}


// ==================== CATEGORIES =========================== //
const url_categ = base_url + '/api/v1/categories'; 

getCatData();

// GET CATEGORIES DATA
function getCatData() {
    fetch(url_categ).then(
        (res)=>res.json()
    ).then((response)=>{
        var tmpCatData = "";
        //console.log(response);
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

    genericPost(url_categ, data);
}

function editCat(idCategory, newName) {
    let data = {
        id: idCategory,
        name: newName
    };

    genericPatch(idCategory, data);
}

function remCat(idCategory) {
    let del = ""
    del = url_categ + "/" + id
    
    genericDelete(del);
}


// set the message for form status
function setSuccessMessage(message) {
    alert(message);
}