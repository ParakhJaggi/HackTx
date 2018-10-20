package petfinder.site.controller;

import java.io.IOException;

import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.common.xcontent.XContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import alloy.util.Json;
import petfinder.site.common.user.UserDto;
import petfinder.site.elasticsearch.PetfinderElasticSearchClientProvider;

/**
 * Created by jlutteringer on 1/15/18.
 */
@Controller
@RequestMapping("/tools")
public class ToolsController {

}