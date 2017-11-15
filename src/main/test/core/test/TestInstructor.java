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

public class TestInstructor {
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
	// the instructor has been assigned to this class
	public void testaddHomework_InstructorAssignedToClass() {
        this.admin.createClass("Class", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Class", 2017, "hw1");
        assertTrue(this.instructor.homeworkExists("Class", 2017, "hw1"));
    }
	
	@Test
	// the instructor has not been assigned to this class
	public void testaddHomework_InstructorNotAssignedToClass() {
        this.admin.createClass("ClassA", 2017, "InstructorA", 15);
        this.admin.createClass("ClassB", 2017, "InstructorB", 25);
        this.instructor.addHomework("InstructorB", "ClassA", 2017, "hw1");
        assertFalse(this.instructor.homeworkExists("ClassA", 2017, "hw1"));
    }
	
	@Test
	//instructor assigned, homework assigned, and student submitted the hw
	public void testAssignGrade_allTrue() {
		this.admin.createClass("Class", 2017, "Instructor", 15);
        this.instructor.addHomework("Instructor", "Class", 2017, "hw1");
        this.student.registerForClass("Student", "Class", 2017);
        this.student.submitHomework("Student", "hw1", "solution", "Class", 2017);
        this.instructor.assignGrade("Instructor", "Class", 2017, "hw1", "Student", 95);
        assertTrue(this.instructor.getGrade("Class", 2017, "hw1", "Student") == 95);
        //instructor assign grade
        //assert get grade to see if equal to assigned grade
	}

	@Test
	//instructor not assigned for the class
	public void testAssignGrade_instructorNotAssigned() {
		this.admin.createClass("ClassA", 2017, "InstructorA", 15);
		this.admin.createClass("ClassB", 2017, "InstructorB", 25);
        this.instructor.addHomework("InstructorA", "ClassA", 2017, "hw1");
        this.student.registerForClass("Student", "ClassA", 2017);
        this.student.submitHomework("Student", "hw1", "solution", "ClassA", 2017);
        this.instructor.assignGrade("InstructorB", "ClassA", 2017, "hw1", "Student", 95);
        assertFalse(this.instructor.getGrade("ClassA", 2017, "hw1", "Student") == 95);
	}
	
	@Test
	//homework not assigned, LOOK AT THIS AGAIN, ask TA!!!
	public void testAssignGrade_homeworkNotAssigned() {
		this.admin.createClass("ClassA", 2017, "InstructorA", 15);
        this.student.registerForClass("Student", "ClassA", 2017);
        this.instructor.assignGrade("InstructorA", "ClassA", 2017, "hw1", "Student", 95);
        assertNotEquals(new Integer (95), this.instructor.getGrade("ClassA", 2017, "hw1", "Student"));
	}
	
	@Test
	//student hasn't submitted this hw
	public void testAssignGrade_studentNotSubmit() {
		this.admin.createClass("ClassA", 2017, "InstructorA", 15);
        this.instructor.addHomework("InstructorA", "ClassA", 2017, "hw1");
        this.student.registerForClass("Student", "ClassA", 2017);
        this.instructor.assignGrade("InstructorA", "ClassA", 2017, "hw1", "Student", 95);
        assertFalse(this.instructor.getGrade("ClassA", 2017, "hw1", "Student") == 95);
	}
	
}
