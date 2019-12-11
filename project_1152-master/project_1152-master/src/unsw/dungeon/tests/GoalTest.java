package unsw.dungeon.tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import unsw.dungeon.ComplexAndGoal;
import unsw.dungeon.ComplexOrGoal;
import unsw.dungeon.DummyGoalObservable;
import unsw.dungeon.Goal;
import unsw.dungeon.GoalObserver;
import unsw.dungeon.LeafGoal;

class GoalTest {

	@Test
	void testIncompleteLeafGoal() {
		Goal leaf = new LeafGoal("exit");
		assertEquals(leaf.isComplete(), false);
	}

	@Test
	void testCompleteLeafGoal() {
		Goal leaf = new LeafGoal("exit");
		DummyGoalObservable d = new DummyGoalObservable();
		//System.out.println("hello");
		d.addGoalObserver((GoalObserver) leaf);
		d.notifyGoalObservers("exit", true);
		assertEquals(leaf.isComplete(), true);
	}
	
	@Test 
	void testChangeCompletedGoalToIncomplete() {
		Goal leaf = new LeafGoal("exit");
		DummyGoalObservable d = new DummyGoalObservable();
		//System.out.println("hello");
		d.addGoalObserver((GoalObserver) leaf);
		d.notifyGoalObservers("exit", true);
		assertEquals(leaf.isComplete(), true);
		d.notifyGoalObservers("exit", false);
		assertEquals(leaf.isComplete(), false);
	}
	
	@Test
	void testComplexAndGoal() {
		// Setting up complexAndGoal
		Goal parent = new ComplexAndGoal();
		Goal leaf1 = new LeafGoal("exit");
		Goal leaf2 = new LeafGoal("enemies");
		parent.addGoal(leaf1);
		parent.addGoal(leaf2);
		DummyGoalObservable d = new DummyGoalObservable();
		d.addGoalObserver((GoalObserver) leaf1);
		d.addGoalObserver((GoalObserver) leaf2);
		// Checking that parent isComplete changes depending on children
		assertEquals(parent.isComplete(), false);
		d.notifyGoalObservers("exit", true);
		assertEquals(parent.isComplete(), false);
		d.notifyGoalObservers("enemies", true);
		assertEquals(parent.isComplete(), true);
		// Changing back to false
		d.notifyGoalObservers("enemies", false);
		assertEquals(parent.isComplete(), false);
	}
	
	@Test
	void testSingleParentComplexAndGoal() {
		Goal parent = new ComplexAndGoal();
		Goal leaf1 = new LeafGoal("exit");
		parent.addGoal(leaf1);
		DummyGoalObservable d = new DummyGoalObservable();
		d.addGoalObserver((GoalObserver) leaf1);
		
		assertEquals(parent.isComplete(), false);
		
		d.notifyGoalObservers("exit", true);
		assertEquals(parent.isComplete(), true);
	}
	
	@Test
	void testSingleParentComplexOrGoal() {
		Goal parent = new ComplexOrGoal();
		Goal leaf1 = new LeafGoal("exit");
		parent.addGoal(leaf1);
		DummyGoalObservable d = new DummyGoalObservable();
		d.addGoalObserver((GoalObserver) leaf1);
		
		assertEquals(parent.isComplete(), false);
		
		d.notifyGoalObservers("exit", true);
		assertEquals(parent.isComplete(), true);
	}
	
	@Test
	void testComplexOrGoal() {
		// Setting up complexAndGoal
		Goal parent = new ComplexOrGoal();
		Goal leaf1 = new LeafGoal("exit");
		Goal leaf2 = new LeafGoal("enemies");
		parent.addGoal(leaf1);
		parent.addGoal(leaf2);
		DummyGoalObservable d = new DummyGoalObservable();
		d.addGoalObserver((GoalObserver) leaf1);
		d.addGoalObserver((GoalObserver) leaf2);
		// Checking that parent isComplete changes depending on children
		assertEquals(parent.isComplete(), false);
		d.notifyGoalObservers("exit", true);
		assertEquals(parent.isComplete(), true);
		d.notifyGoalObservers("enemies", true);
		assertEquals(parent.isComplete(), true);
		// Changing one leaf back to false
		d.notifyGoalObservers("enemies", false);
		assertEquals(parent.isComplete(), true);
	}
	
	@Test
	void testMultipleChildrenComplexAndGoal() {
		Goal parent = new ComplexAndGoal();
		Goal leaf1 = new LeafGoal("exit");
		Goal leaf2 = new LeafGoal("enemies");
		Goal leaf3 = new LeafGoal("boulders");
		parent.addGoal(leaf1);
		parent.addGoal(leaf2);
		parent.addGoal(leaf3);
		DummyGoalObservable d = new DummyGoalObservable();
		d.addGoalObserver((GoalObserver) leaf1);
		d.addGoalObserver((GoalObserver) leaf2);
		d.addGoalObserver((GoalObserver) leaf3);
		// Checking that parent isComplete changes depending on children
		assertEquals(parent.isComplete(), false);
		d.notifyGoalObservers("exit", true);
		assertEquals(parent.isComplete(), false);
		d.notifyGoalObservers("enemies", true);
		assertEquals(parent.isComplete(), false);
		d.notifyGoalObservers("boulders", true);
		assertEquals(parent.isComplete(), true);
	}
	
	@Test
	void testMultipleChildrenComplexOrGoal() {
		Goal parent = new ComplexOrGoal();
		Goal leaf1 = new LeafGoal("exit");
		Goal leaf2 = new LeafGoal("enemies");
		Goal leaf3 = new LeafGoal("boulders");
		parent.addGoal(leaf1);
		parent.addGoal(leaf2);
		parent.addGoal(leaf3);
		DummyGoalObservable d = new DummyGoalObservable();
		d.addGoalObserver((GoalObserver) leaf1);
		d.addGoalObserver((GoalObserver) leaf2);
		d.addGoalObserver((GoalObserver) leaf3);
		// Checking that parent isComplete changes depending on children
		assertEquals(parent.isComplete(), false);
		d.notifyGoalObservers("exit", true);
		assertEquals(parent.isComplete(), true);
		d.notifyGoalObservers("enemies", true);
		assertEquals(parent.isComplete(), true);
		d.notifyGoalObservers("boulders", true);
		assertEquals(parent.isComplete(), true);
		d.notifyGoalObservers("exit", false);
		assertEquals(parent.isComplete(), true);
	}
	
	@Test
	void testComplexGoalNested() {
		// Testing that complex goals can be nested
		Goal head = new ComplexAndGoal();
		Goal parent = new ComplexOrGoal();
		Goal leaf1 = new LeafGoal("exit");
		Goal leaf2 = new LeafGoal("enemies");
		Goal leaf3 = new LeafGoal("boulders");
		parent.addGoal(leaf1);
		parent.addGoal(leaf2);
		head.addGoal(parent);
		head.addGoal(leaf3);
		DummyGoalObservable d = new DummyGoalObservable();
		d.addGoalObserver((GoalObserver) leaf1);
		d.addGoalObserver((GoalObserver) leaf2);
		d.addGoalObserver((GoalObserver) leaf3);
		
		assertEquals(head.isComplete(), false);
		
		d.notifyGoalObservers("exit", true);
		assertEquals(parent.isComplete(), true);
		assertEquals(head.isComplete(), false);
		
		d.notifyGoalObservers("enemies", true);
		assertEquals(parent.isComplete(), true);
		assertEquals(head.isComplete(), false);
		
		d.notifyGoalObservers("boulders", true);
		assertEquals(head.isComplete(), true);
	}
	
}
