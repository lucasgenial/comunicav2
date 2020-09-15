
Webcam.set({
	width: 465,
	height: 465,
	image_format: 'jpeg',
	jpeg_quality: 90
});
Webcam.attach( '#my_camera' );



function take_snapshot(id) {

	Webcam.snap(function(data_uri) {
		$("#foto-3x4-"+id).html('<img data-toggle="modal" data-target="#exampleModalCenter" class="clicouNa3x4" style="width: 45px; height: 45px;" src="'+data_uri+'"/>');
		$("#foto-id-"+id).html('<input hidden="hidden" name="visitantes['+id+'].fotografiaUri" value="'+data_uri+'"/>');
	});
}


$('.clicouNa3x4').on("click", function(e){
	$("#fotografar").attr('onclick', 'take_snapshot('+$(this).attr('data-identificador')+')')
});
