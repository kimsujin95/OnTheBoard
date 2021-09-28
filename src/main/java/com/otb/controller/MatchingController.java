package com.otb.controller;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import com.otb.sevice.MatchingService;
import com.otb.sevice.UserService;
import com.otb.vo.MatchingVo;
import com.otb.vo.UserVo;

@Controller
@RequestMapping(value = "/matching", method = {RequestMethod.GET, RequestMethod.POST})
public class MatchingController {
	
	@Autowired
	private MatchingService matchingService;
	
	// 매칭리스트
	@RequestMapping("/list")
	public String list(Model model) {
		System.out.println("매칭 컨트롤러: list;;;");
		
		List<MatchingVo> matchingList = matchingService.list();
		model.addAttribute("matchingList", matchingList);
		
		return "/matching/list";
	}
	
	// 매칭글 쓰기폼
	@RequestMapping("/writeForm")
	public String writeForm(HttpSession session, Model model) {
		System.out.println("매칭 컨트롤러: writeForm;;;");
		
		// 로그인 유저 정보
		UserVo authUser = (UserVo)session.getAttribute("authUser");
		System.out.println(authUser);
		int authUserNo = authUser.getUserNo();
		System.out.println(authUserNo);

		// 글쓰기 폼에서 필요한(사용할) 데이터 Map으로 묶어서 보내기 
		Map<String, Object> writeUserMap = matchingService.writeUserInfo(authUserNo);
		System.out.println(writeUserMap);
		model.addAttribute("writeUserMap", writeUserMap);
		
		return "/matching/writeForm";
	}
	
	// 매칭글 쓰기
	@RequestMapping("/write")
	public String write(@ModelAttribute MatchingVo matchingVo) {
		System.out.println("매칭 컨트롤러: write;;;");
		System.out.println(matchingVo);
		
		// 글쓰기 폼에서 받은 데이터 묶어서 INSERT
		int write = matchingService.write(matchingVo);
		
		return "redirect:/matching/list";
	}
	
	// 매칭글 읽기
	@RequestMapping("/read")
	public String read(Model model, @RequestParam(value = "no") int matchingNo) {
		System.out.println("매칭 컨트롤러: read;;;");
		System.out.println(matchingNo);
		
		// 클릭한 매칭글 번호 받아서 데이터 불러오기
		MatchingVo matchingVo = matchingService.read(matchingNo);
		model.addAttribute("matchingVo", matchingVo);
		
		return "/matching/read";
	}
	
	// 매칭글 수정폼
	@RequestMapping("/modifyForm")
	public String modify() {
		System.out.println("매칭 컨트롤러: modifyForm;;;");
		
		return "/matching/modifyForm";
	}
	
	// 매칭글 삭제
	@RequestMapping("/delete")
	public String delete() {
		System.out.println("매칭 컨트롤러: delete;;;");
		// deleteForm == modal
		
		return "redirect:/matching/list";
	}

}
