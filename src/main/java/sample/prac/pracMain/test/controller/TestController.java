package sample.prac.pracMain.test.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import sample.prac.common.mail.MailSend;
import sample.prac.pracMain.test.model.TestModel;
import sample.prac.pracMain.test.service.TestService;

@Controller
public class TestController {

	private final TestService testService;
	
	@Value("${active.name}")
	private String testConfig;
	
	
	@Autowired
	public TestController(TestService testService) {
		this.testService = testService; 
	}
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String test(Model model) {
		
		List<TestModel> result = testService.test();
		
		model.addAttribute("result", result);
		model.addAttribute("testConfig", testConfig);
		
		MailSend send = new MailSend();
		
		send.MailSend();
		
		return "test/test";
	}
}
