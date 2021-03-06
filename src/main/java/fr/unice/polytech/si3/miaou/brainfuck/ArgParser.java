package fr.unice.polytech.si3.miaou.brainfuck;

import fr.unice.polytech.si3.miaou.brainfuck.exceptions.ArgumentsException;

/**
 * Parser for the arguments passed through the call of the bfck executable.
 * It also stores the different parsed arguments into fields relative to their function.
 * e.g : if a filename is passed through bfck command, it is stored as a filename in this class.
 *
 * @author Julien Lemaire
 */
public class ArgParser {
	private String filename = "";
	private String in;
	private String out;
	private Mode mode;
	private Type type;
	private boolean trace = false;

	/**
	 * Main constructor of the <code>ArgParser</code> class.
	 * It takes an array of String, respresenting the arguments, and parse it to store them.
 	 *
	 * @param args Array of String containing all the arguments passed through the executable.
	 */
	public ArgParser(String[] args) throws ArgumentsException {
		mode = Mode.RUN; //reading a file by default
		type = Type.TEXT; //the file is considered as text by default
		//parsing files
		for (int i = 0 ; i < args.length ; i++) {
			switch (args[i]) {
				case "-p":
					if (i+1 < args.length && !(args[i+1].startsWith("-"))) {
						this.filename = args[i+1];
						if (this.filename.toLowerCase().endsWith(".bmp")) {
							type = Type.IMAGE;
						}
						i++;
					} else {
						throw new ArgumentsException("No file for -p option.");
					}
					break;
				case "-i":
					if (i+1 < args.length && !(args[i+1].startsWith("-"))) {
						this.in = args[i+1];
						i++;
					} else {
						throw new ArgumentsException("No file for -i option.");
					}
					break;
				case "-o":
					if (i+1 < args.length && !(args[i+1].startsWith("-"))) {
						this.out = args[i+1];
						i++;
					} else {
						throw new ArgumentsException("No file for -o option.");
					}
					break;
				case "--rewrite":
					setMode(Mode.REWRITE);
					break;
				case "--translate":
					setMode(Mode.TRANSLATE);
					break;
				case "--check":
					setMode(Mode.CHECK);
					break;
				case "--trace":
					this.trace = true;
					break;
				default:
					throw new ArgumentsException(args[i]+" is not a recognized option or argument.");
			}
		}
		if (this.filename == "") {
			throw new ArgumentsException("No program filename specified.");
		}
	}

	/**
	 * Getter for the filename, if any.
	 *
	 * @return The filename if one was passed, else an empty string.
	 */
	public String getFilename() {
		return filename;
	}

	/**
	 * Getter for the name of the input file.
	 *
	 * @return The name of the input file if one was passed, else null.
	 */
	public String getInput() {
		return in;
	}

	/**
	 * Getter for the name of the output file.
	 *
	 * @return The name of the output file if one was passed, else null.
	 */
	public String getOutput() {
		return out;
	}

	/**
	 * Getter for the current instance's mode.
	 *
	 * @return The current instance's mode (by default RUN).
	 */
	public Mode getMode() {
		return mode;
	}

	/**
	 * Getter for the trace boolean.
	 *
	 * @return true if trace mode is activated, else false.
	 */
	public boolean isTracing() {
		return trace;
	}

	/**
	 * Getter for the type of the file matching the filename entry.
	 *
	 * @return The type of the used file.
	 */
	public Type getType() {
		return type;
	}

	/**
	 * Setter for the mode.
	 * Checks if another mode (other than RUN) has been used before. In that case, it throws an exceptions.
	 *
	 * @param mode The new mode of execution.
	 * @throws ArgumentsException if we try to use several modes of execution for the same instance (e.g : Translate and Check at the same time).
	 */
	private void setMode(Mode mode) {
		if (this.mode == Mode.RUN) {
			this.mode = mode;
		} else {
			throw new ArgumentsException("Trying to use several modes at the same time.");
		}
	}
}
