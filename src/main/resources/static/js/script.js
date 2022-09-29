const options = {
	method: "GET",
	
}

const cep = document.getElementById("cep")
cep.addEventListener("blur",() => {
	let Cep = document.getElementById("cep").value;
	console.log(Cep)
	let buscar = cep.value.replace("-", "")
	fetch(`https://viacep.com.br/ws/${buscar}/json`, options).then((resp)=>{
		resp.json().then(data =>{
			console.log(data)
			document.getElementById("bairro").value = data.bairro;
			document.getElementById("estado").value = data.uf;
			document.getElementById("cidade").value = data.localidade;
			document.getElementById("endereco").value = data.logradouro;
			
			
			
			
		})
	})
})

function enviar(){
	let bairro = document.getElementById("bairro").value;
	let cidade = document.getElementById("cidade").value;
	let endereco = document.getElementById("endereco").value;
	
	let json = {
		"bairro": bairro,
		"cidade": cidade,
		"endereco": endereco,
	
	}
	console.log(json)
}