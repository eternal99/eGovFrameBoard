package egovframework.example.sample.service.impl;

import java.util.HashMap;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

import org.egovframe.rte.fdl.cmmn.EgovAbstractServiceImpl;
import org.springframework.stereotype.Service;

import egovframework.example.sample.dao.MainMapper;
import egovframework.example.sample.service.MainService;

@Service("MainService")
public class MainServiceImpl extends EgovAbstractServiceImpl implements MainService {
	
	@Resource(name="MainMapper")
	MainMapper mainMapper;
	@Override
	public HashMap<String, Object> selectMain(HttpServletRequest request) throws Exception {
		HashMap<String, Object> paramMap = new HashMap<>();
		// request 요청을 paramMap에 담아주기		
		return mainMapper.selectMain(paramMap);
	}
	
	@Override
	public HashMap<String, Object> selectLogin(HttpServletRequest request) throws Exception {
		
		String userid = request.getParameter("id");
		String userpw = request.getParameter("password");
		
		if (userid.length()>10) {
			throw new Exception("validError_userId");
		}
		
		HashMap<String, Object> paramMap = new HashMap<>();
		paramMap.put("inid", userid);
		paramMap.put("inpw", userpw);
		
		HashMap<String, Object> resultMap = mainMapper.selectLogin(paramMap); 
		if (resultMap == null) {
			throw new Exception("result_Error:id not found");
		}
		return resultMap;
	}

}
