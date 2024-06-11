package com.work.easystep2.REPO;


import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.work.easystep2.model.Vender;

public interface VenderRepository extends JpaRepository<Vender, Integer> {
	
	@Query(value="from Vender where acct = ?1")//使用者帳戶查詢
	Vender findVenderByAcct(String acct);
	
	@Query(value="from Vender where sub = ?1")//使用者帳戶查詢
	Vender findVenderBySub(String sub);
	
	@Query("SELECT v FROM Vender v WHERE v.username LIKE %:keyword%")
	List<Vender> searchByKeyword(@Param("keyword") String keyword);
	
	
	
	
}
