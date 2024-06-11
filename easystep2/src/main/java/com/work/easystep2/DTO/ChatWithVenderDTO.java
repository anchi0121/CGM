package com.work.easystep2.DTO;

import java.util.Date;

import com.work.easystep2.model.ChatWithVender;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ChatWithVenderDTO {
	
	private Integer chatId;

	private String venderAcct;

	private String travelerAcct;

	private String sender;

	private String msg;
	
	private Date writeDatetime;
	
	public ChatWithVenderDTO(ChatWithVender c) {
		this.chatId=c.getChatId();
		this.venderAcct=c.getVenderAcct();
		this.travelerAcct=c.getTravelerAcct();
		this.sender=c.getSender();
		this.msg=c.getMsg();
		this.writeDatetime=c.getWriteDatetime();
	}
	public ChatWithVenderDTO() {
		
	}
	
	
}

	

