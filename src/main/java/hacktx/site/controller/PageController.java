package petfinder.site.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Created by jlutteringer on 8/22/17.
 */
@Controller
public class PageController {
	@RequestMapping("/")
	public String index() {
		return "index";
	}
}