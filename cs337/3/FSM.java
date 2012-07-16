import java.io.File;
import java.util.Vector;

class FSM {
	// Should the program provide a full trace of the simulation?
	final boolean VERBOSE;
	
	// Should the program print warnings in addition to errors?
	final boolean WARNINGS;
	
	// Should unspecified transitions go to a final trap state?
	final boolean TRAP;

	File fsm;
	Vector<File> inputs;

	/**
	 * Creates a new FSM.
	 *
	 * @param fsm 
	 * @param inputs
	 * @param verbose
	 * @param warnings
	 * @param trap
	 */
	FSM (File fsm, Vector<File> inputs, boolean verbose, boolean warnings, boolean trap) {
		this.fsm = fsm;
		this.inputs = inputs;
		this.VERBOSE = verbose;
		this.WARNINGS = warnings;
		this.TRAP = trap;
	}

	/**
	 * Returns a Vector of FSM objects that can then be run.
	 * 
	 * @param arguments an array of Strings
	 * @return a Vector of FSM objects initialized according to the arguments
	 */
	static Vector<FSM> parseArguments (String... arguments) {
		int state;
		boolean verbose, warnings, trap;
		Vector<File> fsms, inputs;
		Vector<FSM> result;
		
		state = 0;
		verbose = false;
		warnings = false;
		trap = false;
		fsms = new Vector<File>();
		inputs = new Vector<File>();
		result = new Vector<FSM>();
		
		for (String argument : arguments) {
			if ("--verbose".equals(argument)) {
				state = 0;
				verbose = true;
			}
			else if ("--warnings".equals(argument)) {
				state = 0;
				warnings = true;
			}
			else if ("--unspecified-transitions-trap".equals(argument)) {
				state = 0;
				trap = true;
			}
			else if ("--fsm".equals(argument)) {
				state = 1;
			}
			else if ("--input".equals(argument)) {
				state = 2;
			}
			else {
				switch (state) {
					case 1:
						fsms.add(new File(argument));
						break;
					case 2:
						inputs.add(new File(argument));
						break;
					default:
						break;
				}
			}
		}

		for (File fsm : fsms) {
			result.add(new FSM(fsm, inputs, verbose, warnings, trap));
		}

		return result;
	}
	
	/**
	 * Compares another object to this FSM.
	 *
	 * @param other
	 */
	@Override public boolean equals (Object other) {
		FSM fsm;
		
		if (other == null) {
			return false;
		}
		if (other == this) {
			return true;
		}
		if (this.getClass() != other.getClass()) {
			return false;
		}
		
		fsm = (FSM) other;
		
		return this.hashCode() == fsm.hashCode();
	}
	
	/**
	 * Creates a hash code for this FSM.
	 */
	@Override public int hashCode () {
		int hash_code;
		
		hash_code = 1;
		hash_code *= this.VERBOSE ? 2 : 1;
		hash_code *= this.WARNINGS ? 2 : 1;
		hash_code *= this.TRAP ? 2 : 1;
		hash_code += this.fsm.hashCode();
		hash_code += this.inputs.hashCode();
		
		return hash_code;
	}
}
