package com.work.easystep2.service;


import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.work.easystep2.DTO.FeedbackDTO;
import com.work.easystep2.DTO.VenderInformationDTO;
import com.work.easystep2.DTO.VendersShowSimpleDTO;
import com.work.easystep2.REPO.TravelerRepository;
import com.work.easystep2.REPO.VenderFeedbackRepository;
import com.work.easystep2.REPO.VenderImgRepository;
import com.work.easystep2.REPO.VenderInformationRepository;
import com.work.easystep2.REPO.VenderRepository;
import com.work.easystep2.model.Traveler;
import com.work.easystep2.model.Vender;
import com.work.easystep2.model.VenderFeedback;
import com.work.easystep2.model.VenderImg;
import com.work.easystep2.model.VenderInformation;
import com.work.easystep2.model.VenderToken;

import jakarta.mail.MessagingException;


@Service
public class VenderService {

	@Autowired
	private VenderRepository vedRepo;
	
	@Autowired
	private PasswordEncoder pwdEncoder;
	
	@Autowired
	private VenderTokenService tokenService;
	
	@Autowired
	private VenderEmailService emailService;
	
	@Autowired
	private RandomStringGenerator randomService;
	
	
	@Autowired
	private VenderFeedbackRepository vFRe;
	
	@Autowired
	private VenderInformationRepository vIRe;
	
	@Autowired
	private TravelerRepository tRepo;
	
	@Autowired
	private VenderImgRepository vPrepo;
	
	//新增會員（含加密）google
	public Vender addVender3(Vender vender) throws MessagingException {
		String encoderPwd=pwdEncoder.encode(vender.getPwd());
		vender.setPwd(encoderPwd);
		vender.setEnable(true);

		Optional<Vender> saved =Optional.of(vedRepo.save(vender));
		if(saved.isPresent()) {
			Vender newVender=saved.get();
			return vedRepo.save(newVender);

		}
		
		return null;
	}
	
	//確認帳號是否重複(沒有重複回傳＿真)
	public Boolean chkVenderAcct(String acct) {
		Optional<Vender> optionl = Optional.ofNullable(vedRepo.findVenderByAcct(acct));
		if(optionl.isEmpty()) {
			return true;
		}
		return false;
	} 

	//google登入會員
	public Vender googleLogin(String sub) {
		Vender dbVender =vedRepo.findVenderBySub(sub);
		if(dbVender!=null) {
				return dbVender;
		}
		return null;
	}
	
	//新增會員（含加密）測試信箱驗證版＿成功
	public Vender addVender2(Vender vender) throws MessagingException {
		String encoderPwd=pwdEncoder.encode(vender.getPwd());
		vender.setPwd(encoderPwd);
		vender.setEnable(false);

		Optional<Vender> saved =Optional.of(vedRepo.save(vender));
		if(saved.isPresent()) {
			String token=UUID.randomUUID().toString();
			Vender newVender=saved.get();
			VenderToken newtoken= tokenService.save(newVender,token);//新增token
			newVender.setVenderToken(newtoken);
			newtoken.setVender(vender);
			emailService.sendHtmlMail2(newVender);//發送信件(使用發送驗證連結第二種方式)
			
			return vedRepo.save(newVender);

		}
		
		return null;
	}

	//新增會員（含加密）
	public Vender addVender(Vender vender) {
//		String encoderPwd=pwdEncoder.encode(traveler.getPwd());
//		traveler.setPwd(encoderPwd);
		return vedRepo.save(vender);
	}
	
	
	//登入會員（含解密，就是和對合不合）
	public Vender checkLogin(String acct ,String pwd) {
		Vender dbVender =vedRepo.findVenderByAcct(acct);
		if(dbVender!=null) {
									//輸入者和資料庫的
			if(pwdEncoder.matches(pwd,dbVender.getPwd())) {
				return dbVender;
				
			}
		}
		return null;
	}

	//會員密碼是否正確
	public Boolean checkPwd(Integer id,String pwd) {
		Optional<Vender> optional=vedRepo.findById(id);
		
		if(optional.isPresent()) {
			Vender vender= optional.get();
									//輸入者和資料庫的
			if(pwdEncoder.matches(pwd,vender.getPwd())){
				return true;
			}
		}
		return false;
		
	}
		
		
	//忘記密碼：驗證帳號和信箱寄信
	public Boolean checkpwd(String acct ,String email) throws MessagingException {
		Vender dbVender =vedRepo.findVenderByAcct(acct);
		if(dbVender!=null) {
									//輸入者和資料庫的
			if(dbVender.getEmail().equals(email)) {
				String newpwd=randomService.generateRandomString(6);
				dbVender.setPwd(pwdEncoder.encode(newpwd));

				vedRepo.save(dbVender);
				emailService.sendMailPwd(dbVender,newpwd);;
				return true;
			}
		}
		return false;
	}
	
	//用id找用戶
	public Vender findVender(Integer id) {
		Optional<Vender> optional=vedRepo.findById(id);
		if(optional.isPresent()) {
			Vender vender= optional.get();
				return vender;
			}
		
		return null;
	}
	
	//更新會員資料
	public Vender updateVender(Vender vender) {
		Optional<Vender> optional=vedRepo.findById(vender.getVenderid());
		if(optional.isPresent()) {
			Vender newvender= optional.get();
			newvender.setConame(vender.getConame());
			newvender.setMobile(vender.getMobile());
			newvender.setStreet(vender.getStreet());
			newvender.setTel(vender.getTel());
			newvender.setUninum(vender.getUninum());
			newvender.setUsername(vender.getUsername());
			newvender.setZipno(vender.getZipno());
			return vedRepo.save(newvender);
			}
		return null;
	}

	//修改會員密碼（含加密）
	public Vender updatePwd(Integer id,String pwd,String newPwd) {
		Optional<Vender> optional=vedRepo.findById(id);
		if(optional.isPresent()) {
			Vender vender= optional.get();
			if(pwdEncoder.matches(pwd,vender.getPwd())) {
				String encoderPwd=pwdEncoder.encode(newPwd);
				vender.setPwd(encoderPwd);
				return vedRepo.save(vender);
			}
		}
		return null;
	}
	

	// 廠商新增資訊
		public Vender insertInfo(VenderInformation vi) {
			Optional<Vender> findById = vedRepo.findById(vi.getVenderId());
			Vender vender = findById.get();
			vi.setVender(vender);
			vender.setVInfo(vi);
			return vedRepo.save(vender);
		}
	
		// 找那個廠商的資訊
		public VenderInformationDTO findInfobyid(Integer venderId) {
			Optional<VenderInformation> optional = vIRe.findById(venderId);
			if (optional.isPresent()) {
				VenderInformation result = optional.get();
				VenderInformationDTO viDTO = new VenderInformationDTO(result);
				return viDTO;
			}
			return null;
		}
		

		// 廠商更新資訊
		public Vender updateInfo(VenderInformation vi) {
			Optional<Vender> findById = vedRepo.findById(vi.getVenderId());
			Vender vender = findById.get();
			vender.setVInfo(vi);
			return vedRepo.save(vender);
		}
		
		// 新增回饋
		public VenderFeedback addScore(VenderFeedback vf) {
			VenderFeedback save = vFRe.save(vf);
			return save;
		}
		// 查有沒有這筆
		public VenderFeedback findthisScore(String va,String ta) {
		 	return vFRe.findByVenderAcctAndTravelerAcct(va, ta);
		}
		// 更新回饋
		public VenderFeedback updateScore(VenderFeedback vf) {
			VenderFeedback save = vFRe.save(vf);
			return save;
		}
		// 秀出廠商得到的所有回饋
		public List<FeedbackDTO> showVFeedback(Integer venderId) {
			String venderAcct = vedRepo.findById(venderId).get().getAcct();
			List<FeedbackDTO> fLDTO = new ArrayList<>();
			List<VenderFeedback> result = vFRe.findByVenderAcct(venderAcct);
			for (VenderFeedback vf : result) {
				Traveler traResult = tRepo.findTravelerByAcct(vf.getTravelerAcct());
				Vender vResult = vedRepo.findVenderByAcct(venderAcct);
				FeedbackDTO feedbackDTO = new FeedbackDTO(vf);
				feedbackDTO.setTravelerName(traResult.getUsername());
				feedbackDTO.setVenderName(vResult.getUsername());
				fLDTO.add(feedbackDTO);
			}
			return fLDTO;
		}
		// 用帳號找名稱
		public String findNameByAcct(String venderAcct) {
			Vender result = vedRepo.findVenderByAcct(venderAcct);
			return result.getUsername();
		}
		// 傳所有 有新增資訊的廠商 內容 分頁 一張照片
		public List<VendersShowSimpleDTO> showAllInfopage(Integer pageNumber) {
			PageRequest pgb = PageRequest.of(pageNumber - 1, 3, Sort.Direction.DESC, "writeOrUpdate");
			List<VendersShowSimpleDTO> resultDTO = new ArrayList<>();
			List<VenderInformation> result = vIRe.findAll(pgb).getContent();
			for (VenderInformation vi : result) {
				VendersShowSimpleDTO vDTO = new VendersShowSimpleDTO();
				Optional<Vender> option = vedRepo.findById(vi.getVenderId());
				List<VenderImg> vp = vPrepo.findByVenderid(vi.getVenderId());
				if (!vp.isEmpty()) {
				    VenderImg firstVenderImg = vp.get(0);
				    vDTO.setShowphoto(firstVenderImg.getVenderImg());
				}
				vDTO.setVenderId(vi.getVenderId());
				vDTO.setVenderIntroduce(vi.getVenderIntroduce());
				vDTO.setVenderName(option.get().getUsername());
				vDTO.setWriteOrUpdate(vi.getWriteOrUpdate());
				
				resultDTO.add(vDTO);
			}
			return resultDTO;
		}
		// 傳總共幾頁
		public Integer showInfoTotalPages() {
			PageRequest pgb = PageRequest.of(0, 3, Sort.Direction.DESC, "writeOrUpdate");
			Integer totalPages = vIRe.findAll(pgb).getTotalPages();
			return totalPages;
		}
		// 傳 其他廠商的資訊 廠商名 更新日期 一張照片 只有三筆
		public List<VendersShowSimpleDTO> showOtherSimpleInfo(Integer venderId){
			List<VendersShowSimpleDTO> resultDTO = new ArrayList<>();
			List<VenderInformation> result = vIRe.otherVenders(venderId);
			int i = 0;
			for (VenderInformation vi : result) {
				VendersShowSimpleDTO vDTO = new VendersShowSimpleDTO();
				Optional<Vender> option = vedRepo.findById(vi.getVenderId());
				List<VenderImg> vp = vPrepo.findByVenderid(vi.getVenderId());
				if (!vp.isEmpty()) {
				    VenderImg firstVenderImg = vp.get(0);
				    vDTO.setShowphoto(firstVenderImg.getVenderImg());
				}
				vDTO.setVenderId(vi.getVenderId());
				vDTO.setVenderName(option.get().getUsername());
				vDTO.setWriteOrUpdate(vi.getWriteOrUpdate());
				resultDTO.add(vDTO);
				if(i==2) {
					break;
				}
				i++;
			}
			return resultDTO;
		}
		// 傳單筆簡單資訊 靠ID
		public VendersShowSimpleDTO showSimpleInfo(Integer venderId) {
			VendersShowSimpleDTO vDTO = new VendersShowSimpleDTO();
			Optional<VenderInformation> ovi = vIRe.findById(venderId);
			Optional<Vender> option = vedRepo.findById(venderId);

			vDTO.setVenderId(venderId);
			vDTO.setVenderIntroduce(ovi.get().getVenderIntroduce());
			vDTO.setVenderName(option.get().getUsername());
			vDTO.setWriteOrUpdate(ovi.get().getWriteOrUpdate());

			return vDTO;
		}
		// 傳單筆的所有資訊 靠ID
		public VenderInformationDTO showInfo(Integer venderId) {
			VenderInformation vi = vIRe.findById(venderId).get();
			return new VenderInformationDTO(vi);
		}
		// 用ID 找帳號
		public String findacct(Integer venderId) {
			return vedRepo.findById(venderId).get().getAcct();
		}
		// 用ID 找名字
		public String findName(Integer venderId) {
			return vedRepo.findById(venderId).get().getUsername();
		}
		// 用 帳號 找資料
		public Vender findByVenderAcct(String venAcct) {
			return vedRepo.findVenderByAcct(venAcct);
		}
		// 傳照片 靠ID
		public List<String> findImgsById(Integer venderId) {
			List<String> result = new ArrayList<>();
			List<VenderImg> findByVenderid = vPrepo.findByVenderid(venderId);
			for (VenderImg venderImg : findByVenderid) {
				String photoUrl = venderImg.getVenderImg();
				result.add(photoUrl);
			}
			return result;
		}
}
