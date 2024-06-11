package com.work.easystep2.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.work.easystep2.REPO.ChatWithVenderRepository;
import com.work.easystep2.model.ChatWithVender;

@Service
public class ChatService {
	
	@Autowired
	private ChatWithVenderRepository cRepo;
	
	// 秀出對話內容
	public List<ChatWithVender> showWhitChats(String venderAcct,String travelerAcct){
		return cRepo.findByVenderAcctAndTravelerAcct(venderAcct, travelerAcct);		
	}
	// 找 旅者聊過天的廠商
	public List<String> showChatVenders(String travelerAcct){
		return cRepo.findVendersByTraveler(travelerAcct);
	}
	// 新增聊天內容
	public void startChat(ChatWithVender cwv) {
		cRepo.save(cwv);
	}
	// 找 廠商聊過天的旅者
	public List<String> showChatTra(String venderAcct){
		return cRepo.findTravelersByVender(venderAcct);
	}
	// 找 旅者聊過天的廠商
	public List<String> showChatVen(String traAcct){
		return cRepo.findVendersByTraveler(traAcct);
	}
	
}
