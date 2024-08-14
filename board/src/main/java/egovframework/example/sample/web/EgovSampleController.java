/*
 * Copyright 2008-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package egovframework.example.sample.web;

import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import egovframework.example.sample.service.EgovSampleService;
import egovframework.example.sample.service.SampleDefaultVO;
import egovframework.example.sample.service.SampleFileVO;
import egovframework.example.sample.service.SampleVO;
import egovframework.login.domain.member.Member;
import egovframework.login.web.login.SessoinConst;
import lombok.extern.slf4j.Slf4j;

import org.egovframe.rte.fdl.property.EgovPropertyService;
import org.egovframe.rte.ptl.mvc.tags.ui.pagination.PaginationInfo;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriUtils;
import org.springmodules.validation.commons.DefaultBeanValidator;

@Slf4j
@Controller
public class EgovSampleController {

	/** EgovSampleService */
	@Resource(name = "sampleService")
	private EgovSampleService sampleService;

	/** EgovPropertyService */
	@Resource(name = "propertiesService")
	protected EgovPropertyService propertiesService;

	@GetMapping("/egovSampleList")
	public String search(@ModelAttribute SampleVO sampleVO, Model model) throws Exception {
		return this.list(sampleVO, model);
	}

	@PostMapping("/egovSampleList")
	public String list(@ModelAttribute SampleVO sampleVO, Model model) throws Exception {
		sampleVO.setPageUnit(propertiesService.getInt("pageUnit"));
		sampleVO.setPageSize(propertiesService.getInt("pageSize"));

		// pagination setting
		PaginationInfo paginationInfo = new PaginationInfo();
		paginationInfo.setCurrentPageNo(sampleVO.getPageIndex());
		paginationInfo.setRecordCountPerPage(sampleVO.getPageUnit());
		paginationInfo.setPageSize(sampleVO.getPageSize());

		sampleVO.setFirstIndex(paginationInfo.getFirstRecordIndex());
		sampleVO.setLastIndex(paginationInfo.getLastRecordIndex());
		sampleVO.setRecordCountPerPage(paginationInfo.getRecordCountPerPage());

		// List
		model.addAttribute("resultList", sampleService.selectSampleList(sampleVO));
		// Count
		paginationInfo.setTotalRecordCount(sampleService.selectSampleListTotCnt(sampleVO));
		// Pagination
		model.addAttribute("paginationInfo", paginationInfo);

		return "thymeleaf/sample/egovSampleList";
	}

	@PostMapping("/sample/detail")
	public String detail(@ModelAttribute SampleVO sampleVO, @RequestParam String id, Model model) throws Exception {
	    sampleVO.setId(id);
	    SampleVO detail = this.sampleService.selectSample(sampleVO);
	    //List<SampleFileVO> files = this.sampleService.getSampleFiles(id);
//	    detail.setSampleFiles(files);
	    model.addAttribute("sampleVO", detail);
	    return "thymeleaf/sample/egovSampleRegister";
	}

	@GetMapping("/sample/add")
	public String form(@ModelAttribute SampleVO sampleVO, HttpSession session, Model model) {
		Member loginMember = (Member) session.getAttribute(SessoinConst.LOGIN_MEMBER);
		if (loginMember != null) {
			sampleVO.setRegUser(loginMember.getName());
		}
		model.addAttribute("sampleVO", sampleVO);
		return "thymeleaf/sample/egovSampleRegister";
	}

	@PostMapping("/sample/add")
	public String add(@Valid @ModelAttribute SampleVO sampleVO, BindingResult bindingResult,
			@RequestParam("files") List<MultipartFile> files, HttpSession session)
			throws Exception {
		if (bindingResult.hasErrors()) {
			return "thymeleaf/sample/egovSampleRegister";
		}
		Member loginMember = (Member) session.getAttribute(SessoinConst.LOGIN_MEMBER);
		if (loginMember != null) {
			sampleVO.setRegUser(loginMember.getName());
		}
		sampleVO.setFiles(files);
		this.sampleService.insertSample(sampleVO);
		return "redirect:/egovSampleList";
	}

	@PostMapping("/sample/update")
	public String update(@Valid @ModelAttribute SampleVO sampleVO, BindingResult bindingResult) throws Exception {
		if (bindingResult.hasErrors()) {
			return "thymeleaf/sample/egovSampleRegister";
		}
		SampleVO existingSample = this.sampleService.selectSample(sampleVO);
		sampleVO.setRegUser(existingSample.getRegUser());
		this.sampleService.updateSample(sampleVO);
		return "redirect:/egovSampleList";
	}

	@PostMapping("/sample/delete")
	public String delete(@ModelAttribute SampleVO sampleVO) throws Exception {
		this.sampleService.deleteSample(sampleVO);
		return "redirect:/egovSampleList";
	}
	/*
	@GetMapping("/sample/files/{sampleId}")
	public String getFiles(@PathVariable String sampleId, Model model) throws Exception {
		List<SampleFileVO> files = sampleService.getSampleFiles(sampleId);
		model.addAttribute("files", files);
		return "thymeleaf/sample/sampleFiles";
	}

	@GetMapping("/sample/download/{fileId}")
	public ResponseEntity<org.springframework.core.io.Resource> downloadFile(@PathVariable String fileId) throws Exception {
		SampleFileVO fileInfo = sampleService.getFileInfo(fileId);

		if (fileInfo == null) {
			return ResponseEntity.notFound().build();
		}

		Path filePath = Paths.get(fileInfo.getFilePath());
		org.springframework.core.io.Resource resource = new UrlResource(filePath.toUri());

		if (!resource.exists() || !resource.isReadable()) {
			throw new RuntimeException("파일을 읽을 수 없습니다.");
		}

		String encodedFileName = UriUtils.encode(fileInfo.getFileName(), StandardCharsets.UTF_8);
		String contentDisposition = "attachment; filename=\"" + encodedFileName + "\"";

		return ResponseEntity.ok().header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
				.contentType(MediaType.APPLICATION_OCTET_STREAM).body(resource);
	}

	@GetMapping("/sample/deleteFile/{fileId}")
	public String deleteFile(@PathVariable String fileId, @RequestParam String sampleId) throws Exception {
		sampleService.deleteSampleFile(fileId);
		return "redirect:/sample/detail?id=" + sampleId;
	} */

}