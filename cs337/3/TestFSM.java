// setenv CLASSPATH .:/lusr/share/lib/java/junit/junit-4.5.jar

import java.io.*;
import java.util.*;
import junit.framework.Assert;
import junit.framework.TestCase;
import junit.framework.TestSuite;
import junit.textui.TestRunner;

public class TestFSM extends TestCase {
	public static void main (String... args) {
		TestRunner.run(new TestSuite(TestFSM.class));
	}
	
	/**
	 * Empty argument list.
	 */
	public void test_parseArguments1 () {
		Vector<FSM> result, expected;
		
		result = FSM.parseArguments();
		expected = new Vector<FSM>();
		
		Assert.assertTrue(expected.equals(result));
	}

	/**
	 * One FSM, one input.
	 */
	public void test_parseArguments2 () {
		Vector<FSM> result, expected;
		Vector<File> inputs;
		
		result = FSM.parseArguments("--fsm", "fsm1", "--input", "input1");
		expected = new Vector<FSM>();
		inputs = new Vector<File>();
		inputs.add(new File("input1"));
		expected.add(new FSM(new File("fsm1"), inputs, false, false, false));
		
		Assert.assertTrue(expected.equals(result));
	}
	
	/**
	 * One FSM, many inputs.
	 */
	public void test_parseArguments3 () {
		Vector<FSM> result, expected;
		Vector<File> inputs;
		
		result = FSM.parseArguments("--fsm", "fsm1", "--input", "input1", "input2");
		expected = new Vector<FSM>();
		inputs = new Vector<File>();
		inputs.add(new File("input1"));
		inputs.add(new File("input2"));
		expected.add(new FSM(new File("fsm1"), inputs, false, false, false));
		
		Assert.assertTrue(expected.equals(result));
	}
	
	/**
	 * Many FSMs, one input.
	 */
	public void test_parseArguments4 () {
		Vector<FSM> result, expected;
		Vector<File> inputs;
		
		result = FSM.parseArguments("--fsm", "fsm1", "fsm2", "--input", "input1");
		expected = new Vector<FSM>();
		inputs = new Vector<File>();
		inputs.add(new File("input1"));
		expected.add(new FSM(new File("fsm1"), inputs, false, false, false));
		expected.add(new FSM(new File("fsm2"), inputs, false, false, false));
		
		Assert.assertTrue(expected.equals(result));
	}
	
	/**
	 * Many FSMs, many inputs.
	 */
	public void test_parseArguments5 () {
		Vector<FSM> result, expected;
		Vector<File> inputs;
		
		result = FSM.parseArguments("--fsm", "fsm1", "fsm2", "--input", "input1", "input2");
		expected = new Vector<FSM>();
		inputs = new Vector<File>();
		inputs.add(new File("input1"));
		inputs.add(new File("input2"));
		expected.add(new FSM(new File("fsm1"), inputs, false, false, false));
		expected.add(new FSM(new File("fsm2"), inputs, false, false, false));
		
		Assert.assertTrue(expected.equals(result));
	}
	
	/**
	 * Flags.
	 */
	public void test_parseArguments6 () {
		Vector<FSM> result, expected;
		Vector<File> inputs;
		
		result = FSM.parseArguments("--fsm", "fsm1", "--verbose", "--warnings", "--unspecified-transitions-trap");
		expected = new Vector<FSM>();
		inputs = new Vector<File>();
		expected.add(new FSM(new File("fsm1"), inputs, true, true, true));
		
		Assert.assertTrue(expected.equals(result));
	}
	
	/**
	 * Arbitrary ordering.
	 */
	public void test_parseArguments7 () {
		Vector<FSM> result, expected;
		Vector<File> inputs;
		
		result = FSM.parseArguments("--verbose", "--input", "input1", "input2", "--unspecified-transitions-trap", "--fsm", "fsm1", "fsm2", "--warnings");
		expected = new Vector<FSM>();
		inputs = new Vector<File>();
		inputs.add(new File("input1"));
		inputs.add(new File("input2"));
		expected.add(new FSM(new File("fsm1"), inputs, true, true, true));
		expected.add(new FSM(new File("fsm2"), inputs, true, true, true));
		
		Assert.assertTrue(expected.equals(result));
	}
}
