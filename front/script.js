const form = document.querySelector('form');
const inputNome = document.getElementById("nome");
const inputSobrenome = document.getElementById("sobrenome");
const inputIdade = document.getElementById("idade");
const inputEmail = document.getElementById("email");

function cadastrar(){
    fetch("http://localhost:8080/usuarios",
        {
            headers: {
                'Accept': 'application/json',
                'Content-type': 'application/json'
            },
            method: "POST",
            body: JSON.stringify({
                    nome: inputNome.value,
                    sobrenome: inputSobrenome.value,
                    idade: inputIdade.value,
                    email: inputEmail.value
                })
        
        })
        .then((res)=>{
            console.log(res)
        })
        .catch((err)=>{
            console.log(err)
        })
}



function limpar(){
    inputNome.value=""
    inputSobrenome.value=""
    inputIdade.value=""
    inputEmail.value=""
}

form.addEventListener('submit', event=>{
    event.preventDefault();
    cadastrar();
    limpar();

})

