function buscarPorNombre() {
	let objForm = document.querySelector("#form_generico");
	objForm.action = "buscarPorNombre";
	objForm.submit();
}