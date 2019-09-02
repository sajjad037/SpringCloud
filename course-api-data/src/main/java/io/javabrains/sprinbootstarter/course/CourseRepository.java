package io.javabrains.sprinbootstarter.course;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface CourseRepository extends CrudRepository<Course, String> {
	
	public List<Course> findByTopicId(String topicId);
//	public List<Course> findByName(String name);
//	public List<Course> findByDescription(String description);
	//getAllTopics
	//getTopic(String id)
	//updateTopic(Topic t)
	//deleteTopic(String id)

}
