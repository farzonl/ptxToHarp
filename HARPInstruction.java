
class HARPInstruction {

	private String opcode;
	private HARPType type;
	private String[] params;
	
	public HARPInstruction() {}

	public HARPInstruction(String opcode) { 
		this.opcode = opcode;
	}
	
	public HARPInstruction(String opcode, HARPType type, String[] params) {
		this.opcode = opcode;
		this.type = type;
		this.params = params;
	}

	public String getOpcode() {
		return opcode;
	}
	
	public void setOpcode(String opcode) {
		this.opcode = opcode;
	}

	public String[] getParams() {
		return params;
	}	
	
	public void setParams(String[] params) {
		this.params = params;
	}

	public void setParam(String newParam, int index) {
		this.params[index] = newParam;
	}

	public HARPType getType() {
		return type;
	}
	
	public void setType(HARPType type) {
		this.type = type;
	}

	public String toString() {		
		String output;
		output = "[HARPOpcode]: " + opcode + "\t[HARPType]: " + type + "\t [HARPParams]: ";
		for(int i=0; i<params.length; i++) {
			output += params[i] + " ";
		}
		return output;
	}

}





