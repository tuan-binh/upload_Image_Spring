package rikkei.academy.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@Controller
@RequestMapping("/")
@PropertySource("classpath:upload.properties")
public class HomeController {
	
	@Value("${upload-path}")
	private String pathUpload;
	
	@GetMapping
	public String home() {
		return "home";
	}
	
	@PostMapping("/upload")
	public String uploadFile(@RequestParam("urls")List<MultipartFile> images, Model model) {
		File file = new File(pathUpload);
		if(!file.exists()) {
			file.mkdirs();
		}
		
		List<String> listUrl = new ArrayList<>();
		for (MultipartFile m:images) {
			String fileName = m.getOriginalFilename();
			listUrl.add(fileName);
			try {
				FileCopyUtils.copy(m.getBytes(),new File(pathUpload + fileName));
			} catch (IOException e) {
				throw new RuntimeException(e);
			}
		}
		model.addAttribute("images",listUrl);
		return "images";
	}
	
}
