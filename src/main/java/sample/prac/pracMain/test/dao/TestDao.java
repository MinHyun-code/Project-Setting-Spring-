package sample.prac.pracMain.test.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import sample.prac.pracMain.test.model.TestModel;

@Repository
public interface TestDao {

	List<TestModel> test();
}
