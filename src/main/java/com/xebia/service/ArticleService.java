package com.xebia.service;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.xebia.entity.Article;
import com.xebia.exception.ArticleException;
import com.xebia.repository.ArticleRepository;

import jdk.internal.org.jline.utils.Log;

@Service
public class ArticleService {
	
	@Autowired
	private ArticleRepository articleRepository;
	
	
	public Article createArticle(Article article) {
		
		Article updateArticle = new Article();
		
		String lowercaseSlug = article.getTitle().replace(" ", "-").toLowerCase();
		updateArticle.setSlug(lowercaseSlug);
		updateArticle.setTitle(article.getTitle());
		updateArticle.setDescription(article.getDescription());
		updateArticle.setBody(article.getBody());
		updateArticle.setTags(article.getTags());
		
		String uniqueID = UUID.randomUUID().toString();

		 updateArticle.setSlug_id(uniqueID);

		
		
		ZonedDateTime zdtObj = ZonedDateTime.now();
		
		updateArticle.setCreatedAt(zdtObj);
		updateArticle.setUpdatedAt(zdtObj);
		
		updateArticle.setFavorited(false);
		updateArticle.setFavoritesCount(0);
		
		
		return articleRepository.save(updateArticle);
		
		
	}
	
	public List<Article> findAllArticle(){
		return articleRepository.findAll();
		
	}
	
	public String deleteArticle(String slug_id) {
		
		if(slug_id.equals(articleRepository.isIdExist(slug_id))) {
		articleRepository.deleteByArticalId(slug_id);
		}
		else {
			slug_id = "Id is not Exist";
		}
		return slug_id;
		
	}

	public Article getById(String slug_id) throws ArticleException {
		
		Article article = articleRepository.getById(slug_id);
		
	
			
				try { 
					if(article == null) {
						throw new ArticleException("artical is not Found with id " + slug_id);
					}
						
				}
				catch (Exception e) { 
					throw new ArticleException("artical is not Found with id " + slug_id);	
				}
				
		
		
		return article;
	}

	
	public Article updateArticle(String title, String slug_id) {
		Article originalArticle = articleRepository.getById(slug_id);
		originalArticle.setTitle(title);
		return articleRepository.save(originalArticle);
	}

}
