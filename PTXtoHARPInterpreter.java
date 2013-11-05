import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

class PTXtoHARPInterpreter { 

	private static FileReader fr;
	private static BufferedReader bufferedReader;
	private static String line;
	private static FileWriter fw;
	private static BufferedWriter bufferedWriter;

	public static void main(String[] args) { 

		// If argument is missing, exits the program
		if(args.length != 2) { 
			System.out.println("ERROR: Need two Arguments");
			System.exit(0);
		}

		String fname1 = args[0];
		String fname2 = args[1];

		try { 	

			readFile(fname1);					// Reads the file
			writeFile(fname2);
			line = bufferedReader.readLine();	// Reads the first line in the file

			int lineNum = 0;	
			while(line != null) { 
				System.out.println("<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<< Line " + lineNum++ + " >>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>");
				PTXInstruction PTXInst = PTXparser(line);	// (1) Parse the PTX Instruction
				System.out.println(PTXInst.toString());
				System.out.println("[Initial Input]: " + line.replace("\t", " "));
		
				HARPInstruction[] HARPInst = Map.Instruction(PTXInst);	// (2) Map the PTX Instruction to HARP Instruction(s)
				Map.Register(HARPInst);		// (3) Maps the PTX Registers to HARP Registers
				for(int i=0; i<HARPInst.length; i++) {	
					System.out.println(HARPInst[i].toString());
				}		
				bufferedWriter.write(Map.Output(HARPInst));		// (4) Output Strings
				System.out.print("[Final Output]: \n\n" + Map.Output(HARPInst) + "\n");

				line = bufferedReader.readLine();	// Reads Next line in the file
			}

			bufferedReader.close();
			bufferedWriter.close();

			System.out.println("\n");
//			Map.DisplayMaps();	

		} catch (IOException ex) {
			System.out.println("ERROR: Cannot Read The File");
		}

	}

	// Reads the file. But if the file not found, exits the program
	public static void readFile(String fname) {
		try {
			fr = new FileReader(fname);
			bufferedReader = new BufferedReader(fr);
		} catch (FileNotFoundException ex) {
			System.out.println("ERROR: Could Not Find The File");
			System.exit(0);
		}
	}	

	public static void writeFile(String fname) {
		try {
			File file = new File(fname);
			if(file.exists()){
				file.delete();
			}
			file.createNewFile();
			fw = new FileWriter(file.getAbsoluteFile(), true);
			bufferedWriter = new BufferedWriter(fw);
		} catch (IOException ex) {
			System.out.println("ERROR: Cannot Read The File");
		}	
	}

	public static PTXInstruction PTXparser(String line) {	

		String[] PTXCode;
		String PTXOpcode;
		String[] PTXArgs;
	    String[] PTXParams;
		PTXInstruction PTXInst = new PTXInstruction();

		// ignore comments "//"
		for(int i=0; i<line.length()-1; i++) {
			if(line.substring(i, i+2).equals("//")) {
				line = line.substring(0, i);
			}
		}

		// split the line by whitespaces, semi-colon and comma	
		PTXCode = line.trim().split("\\s*(,|;|\\s)\\s*");	

		// begins parsing if PTXCode exists
		if(PTXCode.length != 0) {
			if(PTXCode[0].equals("")) {		// EMPTY SPACE
				 PTXInst = new PTXInstruction("space");
			} else if(PTXCode[0].equals("{") || PTXCode[0].equals("}")) {	// PARANTHESIS
				PTXOpcode = PTXCode[0].trim();
				PTXInst = new PTXInstruction(PTXOpcode);	
			} else if(PTXCode[0].charAt(0) == '$') {	// LABEL
				PTXOpcode = PTXCode[0].trim();
				PTXInst = new PTXInstruction("$", new String[] {PTXOpcode.substring(1, PTXOpcode.length()-1)});
			} else if(PTXCode[0].charAt(0) == '@') {	// PREDICATE INSTRUCTION ####### TO-DO ##########
				String predicate;
				String predicate_var;
				if(PTXCode[0].charAt(1) == '!') {
					predicate = "@!";
					predicate_var = PTXCode[0].substring(2, PTXCode[0].length());
				} else {
					predicate = "@";
					predicate_var = PTXCode[0].substring(1, PTXCode[0].length());
				}	
				
				String[] temp = PTXCode[1].trim().split("\\.");	// split the opcode by period		
				PTXOpcode = temp[0].trim();				// store the opcode
				PTXArgs = new String[temp.length-1];	
				for(int i=1; i<temp.length; i++) {
					PTXArgs[i-1] = temp[i].trim();	// store the options
				}
				PTXParams = new String[PTXCode.length-2];
				for(int i=2; i<PTXCode.length; i++) {
					PTXParams[i-2] = PTXCode[i].trim();	// store the params
				}
				PTXInst = new PTXInstruction(PTXOpcode, PTXArgs, PTXParams, predicate, predicate_var);

			} else if(PTXCode[0].charAt(0) == '.') {	// DIRECTIVE OPCODE


/*				if(PTXCode.length == 1)
					PTXCode = PTXCode[0].split("\\s");
				PTXOpcode = PTXCode[0].trim();
				int paramNum = 0;
				int argsNum = 0;
				for(int i=1; i<PTXCode.length; i++) {
					if(PTXCode[i].charAt(0) == '.') 
						optionNum ++;
					else 
						paramNum ++;
				}
				PTXParams = new String[paramNum];
				PTXArgs = new String[optionNum];
				for(int i=0; i<optionNum; i++) {
					PTXOptions[i] = PTXCode[i+1].substring(1, PTXCode[i+1].length());
				}			
				for(int i=0; i<paramNum; i++) {
					PTXParams[i] = PTXCode[1+optionNum+i];
				}

				PTXInst = new PTXInstruction(PTXOpcode, PTXOptions, PTXParams);*/
			} else if(PTXCode[0].charAt(0) >= 'a' && PTXCode[0].charAt(0) <= 'z') {	// INSTRUCTION OPCODE
				String[] temp = PTXCode[0].trim().split("\\.");	// split the opcode by period		
				PTXOpcode = temp[0].trim();				// store the opcode
				PTXArgs = new String[temp.length-1];	
				for(int i=1; i<temp.length; i++) {
					PTXArgs[i-1] = temp[i].trim();	// store the options
				}
				PTXParams = new String[PTXCode.length-1];
				for(int i=1; i<PTXCode.length; i++) {
					PTXParams[i-1] = PTXCode[i].trim();	// store the params
				}
				PTXInst = new PTXInstruction(PTXOpcode, PTXArgs, PTXParams);
			} 
		}
		return PTXInst;
	}
}





