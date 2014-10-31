class UrlMappings {

	static mappings = {
        "/$controller/$action?/$id?(.${format})?"{
            constraints {
                // apply constraints here
            }
        }

        "/"(view:"/index")
		//"/RegisterController"(view:'/register')
		"500"(view:'/error')
	}
}
