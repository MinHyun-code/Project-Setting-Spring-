package sample.prac.pracMain.test.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import sample.prac.pracMain.test.dao.TestDao;
import sample.prac.pracMain.test.model.TestModel;

@Service
public class TestService{

	private final TestDao testDao;
	
	@Autowired
	public TestService(TestDao testDao) {
		this.testDao = testDao;
	}
	
	public List<TestModel> test() {
		return testDao.test();
	}
}