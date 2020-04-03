package codestart.info.kotlinphoto.models

data class Destination(
	var id: Int = 0,
	var city: String? = null,
	var description: String? = null,
	var country: String? = null
)

data class generate(
		var base64 :String? = null
)