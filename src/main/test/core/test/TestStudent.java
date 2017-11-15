package core.test;

import core.api.IAdmin;
import core.api.IInstructor;
import core.api.IStudent;

import core.api.impl.Admin;
import core.api.impl.Instructor;
import core.api.impl.Student;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestStudent {
	private IInstructor instructor;
	private IAdmin admin;
	private IStudent student;
	
	@Before
    public void setup() {
        this.instructor = new Instructor();
        this.admin = new Admin();
        this.student = new Student();
    } 
	
	@Test
	// class exists and has not met its capacity
	public void testRegisterForClass() {
        this.admin.createClass("Class", 2017, "Instructor", 15);
        this.student.registerForClass("Student", "Class", 2017);
        assertTrue(this.student.isRegisteredFor("Student", "Class", 2017));
    }
	
	@Test
	// class does not exist
	public void testRegisterForClass_classNotExist() {
        this.student.registerForClass("Student", "Class", 2017);
        assertFalse(this.student.isRegisteredFor("Student", "Class", 2017));
    }
	
	@Test
	// Class full (capacity already met), CHECK THIS ONE
	public void testRegisterForClass_classFull() {
        this.admin.createClass("Class", 2017, "Instructor", 2);
        
        Student student1 = new Student();
		Student student2 = new Student();
		
		student1.registerForClass("name1", "Class", 2017);
		student2.registerForClass("name2", "Class", 2017);
        
        this.student.registerForClass("Student", "Class", 2017);
        assertFalse(this.student.isRegisteredFor("Student", "Class", 2017));
    }
	
	@Test
	// student is registered and class has not ended
	public void testDropClass() {
		this.admin.createClass("Class", 2017, "Instructor", 10);
		this.student.registerForClass("Student", "Class", 2017);
		this.student.dropClass("Student", "Class", 2017);
		assertFalse(this.student.isRegisteredFor("Student", "Class", 2017));
	}
	
	@Test
	// student is not registered, shouldn't do anything
	public void testDropClass_studentNotRegistered() {
		this.admin.createClass("Class", 2017, "Instructor", 10);
		this.student.dropClass("Student", "Class", 2017);
		assertFalse(this.student.isRegisteredFor("Student", "Class", 2017));
	}
	
	@Test
	// class ended, LOOK AT THIS AGAIN
	public void testDropClass_classEnded() {
		this.admin.createClass("Class", 2016, "Instructor", 10);
		this.student.registerForClass("Student", "Class", 2016);
		this.student.dropClass("Student", "Class", 2016);
		assertTrue(this.student.isRegisteredFor("Student", "Class", 2016));
	}
	
	@Test
	// hw exists, student is registered, and class is taught in current year
	public void testSubmitHomework() {
		this.admin.createClass("Class", 2017, "Instructor", 10);
		this.student.registerForClass("Student", "Class", 2017);
		this.instructor.addHomework("Instructor", "Class", 2017, "hw1");
		this.student.submitHomework("Student", "hw1", "solution", "Class", 2017);
		assertTrue(this.student.hasSubmitted("Student", "hw1", "Class", 2017));
	}
	
	@Test
	// hw not exists
	public void testSubmitHomework_hwNotExisted() {
		this.admin.createClass("Class", 2017, "Instructor", 10);
		this.student.registerForClass("Student", "Class", 2017);
		this.student.submitHomework("Student", "hw1", "solution", "Class", 2017);
		assertFalse(this.student.hasSubmitted("Student", "hw1", "Class", 2017));
	}
	
	@Test
	// student not registered, provided that student is registered??? check with TA
	public void testSubmitHomework_studentNotRegistered() {
		this.admin.createClass("Class", 2017, "Instructor", 10);
		this.admin.createClass("ClassB", 2017, "InstructorB", 15);
		this.student.registerForClass("Student", "ClassB", 2017);
		this.instructor.addHomework("Instructor", "Class", 2017, "hw1");
		this.student.submitHomework("Student", "hw1", "solution", "Class", 2017);
		assertFalse(this.student.hasSubmitted("Student", "hw1", "Class", 2017));
	}
	
	@Test
	// class is taught in past
	public void testSubmitHomework_pastClass() {
		this.admin.createClass("Class", 2016, "Instructor", 10);
		this.student.registerForClass("Student", "Class", 2016);
		this.instructor.addHomework("Instructor", "Class", 2016, "hw1");
		this.student.submitHomework("Student", "hw1", "solution", "Class", 2016);
		assertFalse(this.student.hasSubmitted("Student", "hw1", "Class", 2016));
	}
	
	@Test
	// class is taught in future
	public void testSubmitHomework_futureClass() {
		this.admin.createClass("Class", 2019, "Instructor", 10);
		this.student.registerForClass("Student", "Class", 2019);
		this.instructor.addHomework("Instructor", "Class", 2019, "hw1");
		this.student.submitHomework("Student", "hw1", "solution", "Class", 2019);
		assertFalse(this.student.hasSubmitted("Student", "hw1", "Class", 2019));
	}
	
}
