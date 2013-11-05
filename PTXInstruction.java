
class PTXInstruction {

	private String predicate;
	private String predicate_var;
	private String opcode;
	private String[] args;
	private String[] options;
	private String type;
	private String[] params;

	public PTXInstruction() {}

	public PTXInstruction(String opcode) { 
		this.opcode = opcode;
		this.predicate = "NONE";
	}	
	public PTXInstruction(String opcode, String[] params) {
		this.opcode = opcode;
		this.params = params;
		this.predicate = "NONE";
	}
	public PTXInstruction(String opcode, String[] args, String[] params) {
		this.opcode = opcode;
		this.params = params;
		this.args = args;
		this.predicate = "NONE";

		if(args.length == 1) {
			this.type = args[0];
			this.options = new String[0];
		} else if(args.length > 1){
				this.options = new String[args.length-1];
			for(int i=0; i<args.length-1; i++)
				this.options[i] = args[i];
			this.type = args[args.length-1];
		} else {
			this.type = "";
			this.options = new String[0];
		}
	}
	public PTXInstruction(String opcode, String[] args, String[] params, String predicate, String predicate_var) {
		this.opcode = opcode;
		this.params = params;
		this.args = args;
		this.predicate = predicate;
		this.predicate_var = predicate_var;
		if(args.length == 1) {
			this.type = args[0];
			this.options = new String[0];
		} else if(args.length > 1){
				this.options = new String[args.length-1];
			for(int i=0; i<args.length-1; i++)
				this.options[i] = args[i];
			this.type = args[args.length-1];
		} else {
			this.type = "";
			this.options = new String[0];
		}
	}
	public String getOpcode() {
		return opcode;
	}
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String[] getArgs() {
		return args;
	}

	public void setArgs(String[] args) {
		this.args = args;
	}

	public String[] getOptions() {
		return options;
	}
	
	public void setOptions(String[] options) {
		this.options = options;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String[] getParams() {
		return params;
	}	
	public void setParams(String[] params) {
		this.params = params;
	}

	public String getPredicate() {
		return predicate;
	}
	public void setPredicate(String predicate) {
		this.predicate = predicate;
	}

	public String getPredicateVar() {
		return predicate_var;
	}
	public void setPredicateVar(String predicate_var) {
		this.predicate_var = predicate_var;
	}

	public String toString() {		
		String output;
		output = "[PTXOpcode]: " + opcode + "   \t [PTXType]: ";
		if(type != null) {
			output += type + " ";
		}
		output += "\t[PTXOptions]: ";
		if(options != null) {
			for(int i=0; i<options.length; i++)	
				output += options[i] + " ";
		}
		output += " \t\t [PTXParams]: ";
		if(params != null) {
			for(int i=0; i<params.length; i++) 
				output += params[i] + " ";
		}
		return output;
	}

}





