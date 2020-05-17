package com.xebia.repository;

import javax.transaction.Transactional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
//import org.springframework.transaction.annotation.Transactional;

import com.xebia.entity.Article;

@Repository
public interface ArticleRepository extends JpaRepository<Article, Long> {

	@Transactional
	@Modifying
	@Query("delete from Article b where b.slug_id=:slug_id")
	void deleteByArticalId(String slug_id);

	@Query("select a from Article a where a.slug_id = :slug_id")
	Article getById(@Param("slug_id")  String slug_id);
	
	@Query("select a from Article a where a.slug_id = :slug_id")
	Object isIdExist(@Param("slug_id")  String slug_id);
	
	

}
