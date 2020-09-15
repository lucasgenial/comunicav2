today = new Date();

$('[name="inicioPlantaoteste"]').datetimepicker({
		format: 'DD/MM/YYYY HH:mm:ss',
		locale:  'pt-br',
		ignoreReadonly: true,
		showClose: true,
		showTodayButton:true,
		toolbarPlacement: 'bottom',
		date: today,
    });