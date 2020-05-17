package com.xebia.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.xebia.entity.Article;
import com.xebia.exception.ArticleException;
import com.xebia.service.ArticleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@RestController
@RequestMapping("/api/articles")
@Api(value="createArticle API", description=" This  ArticleController contains all the operation about Article")
public class ArticleController {

	@Autowired
	private ArticleService articleService;
	
	
	@Autowired
	private KafkaTemplate<String, Object> template;
	
	private String topicName = "topic1";
	
	
	@PostMapping
	@ApiOperation(value = "store Article api")
	public ResponseEntity<Article>  createArticle(@RequestBody Article article) {
		
		template.send(topicName,article);
		
		System.err.println("Request is send to kafka with Article Json "+article);
		
		return new ResponseEntity<Article>(articleService.createArticle(article), HttpStatus.CREATED);
	}
	
	
	
	
	@PatchMapping("/{slug_id}")
	public ResponseEntity <Article> updateResource(@RequestParam(value ="title" , required = true) String title, @PathVariable("slug_id") String slug_id) {
	 
		Article newArticle = articleService.updateArticle(title, slug_id);
	 return new ResponseEntity <Article> (newArticle, HttpStatus.OK);
	}
	
	@GetMapping
	@ApiResponses(value = {
	        @ApiResponse(code = 200, message = "Successfully retrieved list"),
	        @ApiResponse(code = 401, message = "You are not authorized to view the resource"),
	        @ApiResponse(code = 403, message = "Accessing the resource you were trying to reach is forbidden"),
	        @ApiResponse(code = 404, message = "The resource you were trying to reach is not found")
	}
	)
	
	public List<Article> getAll(){
		
		return articleService.findAllArticle();
	}
	
	@GetMapping("/{slug_id}")
	@ResponseBody
	@ApiOperation(value = "Get Article api Using Id ")
	public Article getById(@PathVariable String slug_id) throws ArticleException {
		return articleService.getById(slug_id);
	}
	
	
	@DeleteMapping(value="/{slug_id}")
	@ApiOperation(value = "Delete Article Using Id")
	public String deleteById(@PathVariable String slug_id) {
		
		articleService.deleteArticle(slug_id);
		return "Article is deleted with id "+slug_id;
		
	}
}
