package core.test;

import core.api.IAdmin;

import core.api.impl.Student;

import core.api.impl.Admin;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class TestAdmin {
	private IAdmin admin;

    @Before
    public void setup() {
        this.admin = new Admin();
    } 
    
    @Test
    //year is not in past, true
    public void testMakeClass() {
        this.admin.createClass("Test", 2017, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2017));
    }

    @Test
    //year is in past, false
    public void testMakeClass2() {
        this.admin.createClass("Test", 2016, "Instructor", 15);
        assertFalse(this.admin.classExists("Test", 2016));
    }
    
    @Test
    //year is in the future, true
    public void testMakeClass3() {
        this.admin.createClass("Test", 2019, "Instructor", 15);
        assertTrue(this.admin.classExists("Test", 2019));
    }
    
    @Test
    // capacity > 0, true
    public void testMakeClass4() {
    		this.admin.createClass("Test", 2017, "Instructor", 10);
    		assertEquals(10, this.admin.getClassCapacity("Test", 2017));    		
    }
    
    @Test
    // capacity == 0, false
    public void testMakeClass5() {
    		this.admin.createClass("Test", 2017, "Instructor", 0);
		assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test
    // capacity < 0, class doesn't exist, false
    public void testMakeClass6() {
    		this.admin.createClass("Test", 2017, "Instructor", -1);
    		// assert false when it is false => true
		assertFalse(this.admin.classExists("Test", 2017));
    }
    
    @Test
    // empty string for class name
    public void testMakeClass7() {
		this.admin.createClass("", 2017, "Instructor", 10);
		assertFalse(this.admin.classExists("", 2017));
    }
    
    @Test
    // empty string for instructor
    public void testMakeClass8() {
		this.admin.createClass("Test", 2017, "", 10);
		assertFalse(this.admin.classExists("Test", 2017));
    }

    @Test
    // unique pair of class and year
    public void testMakeClass9() {
		this.admin.createClass("Test", 2017, "Instructor1", 10);
		this.admin.createClass("Test", 2017, "Instructor2", 15);
		assertEquals("Instructor1", this.admin.getClassInstructor("Test", 2017));
    }
    
    @Test
    //instructor assigned to more than 2 courses in a year
    public void testMakeClass10() {
		this.admin.createClass("Class1", 2017, "Instructor", 10);
		this.admin.createClass("Class2", 2017, "Instructor", 15);
		this.admin.createClass("Class3", 2017, "Instructor", 20);
		assertFalse(this.admin.classExists("Class3", 2017));
    }
    
    @Test
    // changing capacity > students enrolled, true
    public void testChangeCapacity() {
    		this.admin.createClass("Test", 2017, "Instructor", 15);
    		this.admin.changeCapacity("Test", 2017, 20);
    		assertEquals(20, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    // changing capacity == students enrolled, true
    public void testChangeCapacity2() {
    		this.admin.createClass("Test", 2017, "Instructor", 15);
    		this.admin.changeCapacity("Test", 2017, 15);
    		assertEquals(15, this.admin.getClassCapacity("Test", 2017));
    }

    @Test
    // changing capacity < current capacity, true
    public void testChangeCapacity3() {
    		this.admin.createClass("Test", 2017, "Instructor", 15);
    		this.admin.changeCapacity("Test", 2017, 5);
    		assertEquals(5, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    // changing capacity < students enrolled, should not do anything, false
    public void testChangeCapacity4() {
    		this.admin.createClass("Test", 2017, "Instructor", 15);
    		
    		Student student1 = new Student();
    		Student student2 = new Student();
    		
    		student1.registerForClass("name1", "Test", 2017);
    		student2.registerForClass("name2", "Test", 2017);
    		
    		this.admin.changeCapacity("Test", 2017, 1);
    		assertEquals(15, this.admin.getClassCapacity("Test", 2017));
    }
    
    @Test
    // change capacity to negative, false
    public void testChangeCapacity5() {
    		this.admin.createClass("Test", 2017, "Instructor", 15);
		this.admin.changeCapacity("Test", 2017, -5);
		assertFalse(this.admin.getClassCapacity("Test", 2017) == -5);
    }
    
}
