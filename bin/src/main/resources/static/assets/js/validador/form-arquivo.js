
$(".downloadArquivo").on("click", function(event){
		      window.location.href=	"/admin/cadastros/arquivo/download/"+this.id+"/"+this.name;
});

//today = new Date(new Date().getFullYear(), new Date().getMonth(), new Date().getDate());
//today = new Date();
//
//$('[name="dataEnvio"]').datetimepicker({
//	format: 'DD/MM/YYYY',
//	locale:  'pt-br',
//	ignoreReadonly: true,
//	showClose: true,
//	showTodayButton:true,
//	toolbarPlacement: 'bottom',
//	//date: today,
//}).on('changeDate', function(e) {
//	
//    // Revalidate the date field
//    fv.revalidateField('dataEnvio');
//});	