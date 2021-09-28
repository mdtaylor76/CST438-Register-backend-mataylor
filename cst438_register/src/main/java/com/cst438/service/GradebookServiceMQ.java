package com.cst438.service;


import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import com.cst438.domain.CourseDTOG;
import com.cst438.domain.Enrollment;
import com.cst438.domain.EnrollmentDTO;
import com.cst438.domain.EnrollmentRepository;


public class GradebookServiceMQ extends GradebookService {
	
	@Autowired
	RabbitTemplate rabbitTemplate;
	
	@Autowired
	EnrollmentRepository enrollmentRepository;
	
	@Autowired
	Queue gradebookQueue;
	
	
	public GradebookServiceMQ() {
		System.out.println("MQ grade book service");
	}
	
	// send message to grade book service about new student enrollment in course
	@Override
	public void enrollStudent(String student_email, String student_name, int course_id) {
		 
		//Create new EnrollmentDTO object
		EnrollmentDTO newStudent = new EnrollmentDTO(student_email, student_name, course_id);
		
		//TODO  complete this method in homework 4
		this.rabbitTemplate.convertAndSend(gradebookQueue.getName(), newStudent);
		System.out.println(" [x] Sent '" + newStudent + "'");
	}
	
	@RabbitListener(queues = "registration-queue")
	@Transactional
	public void receive(CourseDTOG courseDTOG) {
		
		//TODO  complete this method in homework 4
		for (CourseDTOG.GradeDTO grade : courseDTOG.grades) {
			System.out.println("Name: " + grade.student_name + " Email: " 
					+ grade.student_email + " Grade: " + grade.grade);

			//Get enrollment for Student
			Enrollment enrollment = enrollmentRepository.findByEmailAndCourseId(grade.student_email, courseDTOG.course_id);
			//Set grade for enrollment
			enrollment.setCourseGrade(grade.grade);
			//Write enrollment
			enrollmentRepository.save(enrollment);
			
		}
	}
	
	

}
