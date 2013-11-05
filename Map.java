import java.util.HashMap;
import java.util.Iterator;
import java.util.ArrayList;
import java.util.Arrays;

class Map extends OpcodeTable { 

	private static HashMap<String, String> regs = new HashMap<String, String>();
 	private static HashMap<String, Boolean> valid = new HashMap<String, Boolean>();
	private static String PTXType;
	private static String[] PTXOptions;
	private static int r_tempNum = 0;
	private static int p_tempNum = 0;
	private static int l_tempNum = 0;

	public static HARPInstruction[] Instruction(PTXInstruction PTXInst) {
		String PTXOpcode = PTXInst.getOpcode();
		String[] PTXParams = PTXInst.getParams();
		String PTXPredicate = PTXInst.getPredicate();
		String PTXPredicate_var = PTXInst.getPredicateVar();
		PTXType = PTXInst.getType();
		PTXOptions = PTXInst.getOptions();

		/* PTX Predicate Instruction  */		
		if(PTXPredicate.equals(PTX_PREDICATE) || PTXPredicate.equals(PTX_PREDICATE2)) return ptx_predicate(PTXInst);	
	
		/* PTX Interger&Floating-Point Arithmetic Instructions */	
		else if(PTXOpcode.equals(PTX_ADD)) return new HARPInstruction[] {harp_add(PTXParams[0], PTXParams[1], PTXParams[2])};
		else if(PTXOpcode.equals(PTX_SUB)) return new HARPInstruction[] {harp_sub(PTXParams[0], PTXParams[1], PTXParams[2])};
		else if(PTXOpcode.equals(PTX_MUL)) return new HARPInstruction[] {harp_mul(PTXParams[0], PTXParams[1], PTXParams[2])};
		else if(PTXOpcode.equals(PTX_MAD)) return harp_mad(PTXParams[0], PTXParams[1], PTXParams[2], PTXParams[3]);
		else if(PTXOpcode.equals(PTX_MUL24)) return new HARPInstruction[] {harp_mul(PTXParams[0], PTXParams[1], PTXParams[2])};
		else if(PTXOpcode.equals(PTX_MAD24)) return harp_mad(PTXParams[0], PTXParams[1], PTXParams[2], PTXParams[3]);
		else if(PTXOpcode.equals(PTX_SAD)) return harp_sad(PTXParams[0], PTXParams[1], PTXParams[2], PTXParams[3]);
		else if(PTXOpcode.equals(PTX_DIV)) return new HARPInstruction[] {harp_div(PTXParams[0], PTXParams[1], PTXParams[2])};
		else if(PTXOpcode.equals(PTX_REM)) return new HARPInstruction[] {harp_mod(PTXParams[0], PTXParams[1], PTXParams[2])};
		else if(PTXOpcode.equals(PTX_ABS)) return harp_abs(PTXParams[0], PTXParams[1]);
		else if(PTXOpcode.equals(PTX_NEG)) return new HARPInstruction[] {harp_neg(PTXParams[0], PTXParams[1])};
		else if(PTXOpcode.equals(PTX_MIN)) return harp_min(PTXParams[0], PTXParams[1], PTXParams[2]);
		else if(PTXOpcode.equals(PTX_MAX)) return harp_max(PTXParams[0], PTXParams[1], PTXParams[2]);
		else if(PTXOpcode.equals(PTX_POPC)) return harp_popc(PTXParams[0], PTXParams[1]);
		else if(PTXOpcode.equals(PTX_CLZ)) return harp_clz(PTXParams[0], PTXParams[1]);
		//else if(PTXOpcode.equals(PTX_BFIND)) 
		//else if(PTXOpcode.equals(PTX_BREV)) 
		//else if(PTXOpcode.equals(PTX_BFE)) 
		//else if(PTXOpcode.equals(PTX_BFI)) 
		//else if(PTXOpcode.equals(PTX_TESTP)) 
		//else if(PTXOpcode.equals(PTX_COPYSIGN)) 
		//else if(PTXOpcode.equals(PTX_FMA)) 
		//else if(PTXOpcode.equals(PTX_SQRT)) 
		//else if(PTXOpcode.equals(PTX_RSQRT)) 
		//else if(PTXOpcode.equals(PTX_SIN)) 
		//else if(PTXOpcode.equals(PTX_COS)) 
		//else if(PTXOpcode.equals(PTX_LG2)) 
		//else if(PTXOpcode.equals(PTX_EX2)) 

		/* PTX Extended-Precision Integer Arithmetic Instructions */
		//else if(PTXOpcode.equals(PTX_ADDCC)) 
		//else if(PTXOpcode.equals(PTX_ADDC)) 
		//else if(PTXOpcode.equals(PTX_SUBCC)) 
		//else if(PTXOpcode.equals(PTX_SUBC)) 
		//else if(PTXOpcode.equals(PTX_MADCC)) 
		//else if(PTXOpcode.equals(PTX_MADC)) 


	
		/* Data Movement and Conversion Instructions */
		else if(PTXOpcode.equals(PTX_MOV)) return new HARPInstruction[] {harp_ld(PTXParams[0], PTXParams[1], "0")};
		//else if(PTXOpcode.equals(PTX_SHFL)) 
		//else if(PTXOpcode.equals(PTX_PRMT)) 
		else if(PTXOpcode.equals(PTX_LD)) return harp_ld(PTXParams[0], PTXParams[1]);
		//else if(PTXOpcode.equals(PTX_LDU)) 
		else if(PTXOpcode.equals(PTX_ST)) return harp_st(PTXParams[0], PTXParams[1]);
		//else if(PTXOpcode.equals(PTX_PREFETCH)) 
		//else if(PTXOpcode.equals(PTX_PREFETCHU)) 
		//else if(PTXOpcode.equals(PTX_ISSPACEP)) 
		//else if(PTXOpcode.equals(PTX_CVTA)) 
		//else if(PTXOpcode.equals(PTX_CVT)) 

		/* Logic and Shift Instructions */
		else if(PTXOpcode.equals(PTX_AND)) return new HARPInstruction[] {harp_and(PTXParams[0], PTXParams[1], PTXParams[2])};
		else if(PTXOpcode.equals(PTX_OR)) return new HARPInstruction[] {harp_or(PTXParams[0], PTXParams[1], PTXParams[2])};
		else if(PTXOpcode.equals(PTX_XOR)) return new HARPInstruction[] {harp_xor(PTXParams[0], PTXParams[1], PTXParams[2])};
		else if(PTXOpcode.equals(PTX_NOT)) return new HARPInstruction[] {harp_not(PTXParams[0], PTXParams[1])};
		else if(PTXOpcode.equals(PTX_CNOT)) return new HARPInstruction[] {harp_iszero(PTXParams[0], PTXParams[1])};
		//else if(PTXOpcode.equals(PTX_SHF)) 
		else if(PTXOpcode.equals(PTX_SHL)) return new HARPInstruction[] {harp_shl(PTXParams[0], PTXParams[1], PTXParams[2])};
		else if(PTXOpcode.equals(PTX_SHR)) return new HARPInstruction[] {harp_shr(PTXParams[0], PTXParams[1], PTXParams[2])};

		/* Comparison and Selection Instructions */
		//else if(PTXOpcode.equals(PTX_SET)) 
		else if(PTXOpcode.equals(PTX_SETP) && PTXParams.length == 3) return harp_setp(PTXParams[0], PTXParams[1], PTXParams[2]);
		else if(PTXOpcode.equals(PTX_SETP) && PTXParams.length == 4) return harp_setp(PTXParams[0], PTXParams[1], PTXParams[2], PTXParams[3]);	
		//else if(PTXOpcode.equals(PTX_SELP)) 
		//else if(PTXOpcode.equals(PTX_SLCT)) 
	
		/* Control Flow Instructions */
		//	else if(PTXOpcode.equals(PTX_BRACEOPEN))	
		//	else if(PTXOpcode.equals(PTX_BRACECLOSE)) 
		else if(PTXOpcode.equals(PTX_BRA)) return new HARPInstruction[] {harp_jmpi(PTXParams[0])};
		//	else if(PTXOpcode.equals(PTX_CALL)) 
		//	else if(PTXOpcode.equals(PTX_RET)) 
		else if(PTXOpcode.equals(PTX_EXIT)) return new HARPInstruction[] {harp_trap()};
		else if(PTXOpcode.equals(PTX_LABEL)) return new HARPInstruction[] {harp_label(PTXParams[0])};
		
		/* Miscellaneous Instructions */
		else if(PTXOpcode.equals(PTX_TRAP)) return new HARPInstruction[] {harp_trap()};
		//else if(PTXOpcode.equals(PTX_BRKPT)) 
		//else if(PTXOpcode.equals(PTX_PMEVENT)) 
		
		/* Invalid Instruction */
		else return new HARPInstruction[] {};
	}	

	public static void Register(HARPInstruction[] HARPInst) {
		ArrayList<String> removeAfter = new ArrayList<String>();
		if(HARPInst != null) {

			// First: Integer Registers Mapping 
			for(int i=0; i<HARPInst.length; i++) {
				String[] params = HARPInst[i].getParams();
				for(int j=0; j<params.length; j++) {
					if(params[j].contains("%t")) {	// Parameter that needs to be replaced with valid register
						if(regs.get(params[j]) != null) {
							HARPInst[i].setParam(regs.get(params[j]), j);
						} else {
							int z = 0;
							while(true) {
								if(valid.get("%r" + z) == null || valid.get("%r" + z)) {
									regs.put(params[j], "%r" + Integer.toString(z));
									valid.put("%r" + z, false);
									HARPInst[i].setParam("%r" + z, j);
									removeAfter.add("%r" + z);
									break;
								}
								z++;
							}
						}
					} else if(params[j].contains("@t")) {
						if(regs.get(params[j]) != null) {
							HARPInst[i].setParam(regs.get(params[j]), j);
						} else {
							int z = 0;
							while(true) {
								if(valid.get("@p" + z) == null || valid.get("@p" + z)) {
									regs.put(params[j], "@p" + z);
									valid.put("@p" + z, false);
									HARPInst[i].setParam("@p" + z, j);
									removeAfter.add("@p" + z);
									break;
								}
								z++;
							}
						}
					} else if(params[j].contains("r")) {	// New Rigster parameter
						regs.put(params[j], params[j]);
						valid.put(params[j], false);
					} else if(params[j].contains("p")) {
						params[j] = params[j].replace("%", "@");
						regs.put(params[j], params[j]);
						valid.put(params[j], false);
					} else if(params[j].contains("f")) {	// Floating-point Register Parameter
						// will handle later
					} else if(params[j].contains("$")) {	
					
					} else if(params[j].charAt(0) < '0' || params[j].charAt(0) > '9') {
						// do nothing
					}
					else {	// Immidieate Parameter
						HARPInst[i].setParam("#" + params[j], j);
					}
				}		
			}
	
			// Second: Floating-point Registers Mapping with leftover valid registers
			for(int i=0; i<HARPInst.length; i++) {
				String[] params = HARPInst[i].getParams();
				for(int j=0; j<params.length; j++) {
					 if(params[j].contains("%f")) { // New floting register parameter
						if(regs.get(params[j]) != null) {
							HARPInst[i].setParam(regs.get(params[j]), j);
						}
		 				else {
							int z = 0;
							while(true) {
								if(valid.get("%r" + z) == null || valid.get("%r" + z)) {
									regs.put(params[j], "%r" + z);
									valid.put("%r" + z, false);
									HARPInst[i].setParam("%r" + z, j);
									break;
								}	
								z++;	
							}
						}
					}
				}
			}
			for(String reg : removeAfter) {
				valid.put(reg, true);
			}
		}	
	}
		

	public static String Output(HARPInstruction[] HARPInsts) {
		String output = new String();
		for(int i=0; i<HARPInsts.length; i++) {
			String opcode = HARPInsts[i].getOpcode();
			String[] params = HARPInsts[i].getParams();
			HARPType type = HARPInsts[i].getType();

			switch(type) {
				case AC_NONE : output += "\t" + opcode + ";\n"; break;
				case AC_1REG : output += "\t" + opcode + " " + params[0] + ";\n"; break;
				case AC_2REG : output += "\t" + opcode + " " + params[0] + ", " + params[1] + ";\n"; break;
				case AC_3REG : output += "\t" + opcode + " " + params[0] + ", " + params[1] + ", " + params[2] + ";\n"; break;
				case AC_3REGSRC : output += "\t" + opcode + " " + params[0] + ", " + params[1] + ", " + params[2] + ";\n"; break;
				case AC_1IMM : output += "\t" + opcode + " " + params[0] + ";\n"; break;	   
				case AC_2IMM : output += "\t" + opcode + " " + params[0] + ", " + params[1] + ";\n"; break;
				case AC_3IMM : output += "\t" + opcode + " " + params[0] + ", " + params[1] + ", " + params[2] + ";\n"; break;
				case AC_3IMMSRC : output += "\t" + opcode + " " + params[0] + ", " + params[1] + ", " + params[2] + ";\n"; break;
				case AC_2PREG : output += "\t" + opcode + " " + params[0] + ", " + params[1] + ";\n"; break;
				case AC_3PREG : output += "\t" + opcode + " " + params[0] + ", " + params[1] + ", " + params[2] + ";\n"; break;
				case AC_PREG_REG : output += "\t" + opcode + " " + params[0] + ", " + params[1] + ";\n"; break;
				case AC_PREDICATE : output += params[0] + " ? "; break;
				case AC_LABEL : output += params[0] + ": " ; break;
				default : output += "Error: Not-Implemented Type \n";
			}

		}

		return output;

	}

	public static void DisplayMaps() {
		Iterator it = Map.regs.keySet().iterator();
		Iterator it2 = Map.valid.keySet().iterator();

		boolean itHasNext = it.hasNext(); 
		boolean it2HasNext = it2.hasNext();
		System.out.println("PTX\tHARP\t\tHARP\tValid\n=====================================");
		while(itHasNext || it2HasNext) {
			if(itHasNext) {
			    String key = it.next().toString();  
			    String value = Map.regs.get(key).toString();
			    System.out.print(key + "\t" + value + " \t");
				itHasNext = it.hasNext();
			} else {
				System.out.print("\t\t");
			}	

			if(it2HasNext) {
			    String key = it2.next().toString();  
			    String value = Map.valid.get(key).toString();
			    System.out.println(" \t" + key + "\t" + value);	
				it2HasNext = it2.hasNext();
			} else {
				System.out.println("\t\t");
			}


		}


	}


	// Temporary registers/label Generator
	private static String getRTemp() {
		return "%t" + (r_tempNum++);
	}

	private static String getPTemp() {
		return "@t" + (p_tempNum++);
	}
	
	private static String getLTemp() {
		return "temp" + (l_tempNum++);
	}

	
	// Memory Loads / Stores
	private static HARPInstruction harp_st(String val, String base, String offset) {
		return new HARPInstruction(HARP_ST, HARPType.AC_3REG, new String[] {val, base, offset});
	}
	private static HARPInstruction harp_ld(String dest, String base, String offset) {
		return new HARPInstruction(HARP_LD, HARPType.AC_3REG, new String[] {dest, base, offset});
	}

	// Predicate Manipulation
	private static HARPInstruction harp_andp(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_ANDP, HARPType.AC_3PREG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_orp(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_ORP, HARPType.AC_3PREG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_xorp(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_XORP, HARPType.AC_3PREG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_notp(String dest, String src1) {
		return new HARPInstruction(HARP_NOTP, HARPType.AC_2PREG, new String[] {dest, src1});
	}

	// Value Tests
	private static HARPInstruction harp_rtop(String dest, String src) {
		return new HARPInstruction(HARP_RTOP, HARPType.AC_PREG_REG, new String[] {dest, src});
	}
	private static HARPInstruction harp_isneg(String dest, String src) {
		return new HARPInstruction(HARP_ISNEG, HARPType.AC_PREG_REG, new String[] {dest, src});
	}
	private static HARPInstruction harp_iszero(String dest, String src) {
		return new HARPInstruction(HARP_ISZERO, HARPType.AC_PREG_REG, new String[] {dest, src});
	}

	// Immediate Integer Arithmetic / Logic
	private static HARPInstruction harp_ldi(String dest, String imm) {
		return new HARPInstruction(HARP_LDI, HARPType.AC_2IMM, new String[] {dest, imm});
	}
	private static HARPInstruction harp_addi(String dest, String src1, String imm) {
		return new HARPInstruction(HARP_ADDI, HARPType.AC_3IMM, new String[] {dest, src1, imm});
	}
	private static HARPInstruction harp_subi(String dest, String src1, String imm) {
		return new HARPInstruction(HARP_SUBI, HARPType.AC_3IMM, new String[] {dest, src1, imm});
	}
	private static HARPInstruction harp_muli(String dest, String src1, String imm) {
		return new HARPInstruction(HARP_MULI, HARPType.AC_3IMM, new String[] {dest, src1, imm});
	}
	private static HARPInstruction harp_divi(String dest, String src1, String imm) {
		return new HARPInstruction(HARP_DIVI, HARPType.AC_3IMM, new String[] {dest, src1, imm});
	}
	private static HARPInstruction harp_modi(String dest, String src1, String imm) {
		return new HARPInstruction(HARP_MODI, HARPType.AC_3IMM, new String[] {dest, src1, imm});
	}
	private static HARPInstruction harp_shli(String dest, String src1, String imm) {
		return new HARPInstruction(HARP_SHLI, HARPType.AC_3IMM, new String[] {dest, src1, imm});
	}
	private static HARPInstruction harp_shri(String dest, String src1, String imm) {
		return new HARPInstruction(HARP_SHRI, HARPType.AC_3IMM, new String[] {dest, src1, imm});
	}
	private static HARPInstruction harp_andi(String dest, String src1, String imm) {
		return new HARPInstruction(HARP_ANDI, HARPType.AC_3IMM, new String[] {dest, src1, imm});
	}
	private static HARPInstruction harp_ori(String dest, String src1, String imm) {
		return new HARPInstruction(HARP_ORI, HARPType.AC_3IMM, new String[] {dest, src1, imm});
	}
	private static HARPInstruction harp_xori(String dest, String src1, String imm) {
		return new HARPInstruction(HARP_XORI, HARPType.AC_3IMM, new String[] {dest, src1, imm});
	}

	
	// Register Integer Arithmetic / Logic
	private static HARPInstruction harp_add(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_ADD, HARPType.AC_3REG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_sub(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_SUB, HARPType.AC_3REG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_mul(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_MUL, HARPType.AC_3REG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_div(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_DIV, HARPType.AC_3REG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_mod(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_MOD, HARPType.AC_3REG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_shl(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_SHL, HARPType.AC_3REG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_shr(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_SHR, HARPType.AC_3REG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_and(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_AND, HARPType.AC_3REG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_or(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_OR, HARPType.AC_3REG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_xor(String dest, String src1, String src2) {
		return new HARPInstruction(HARP_XOR, HARPType.AC_3REG, new String[] {dest, src1, src2});
	}
	private static HARPInstruction harp_neg(String dest, String src1) {
		return new HARPInstruction(HARP_NEG, HARPType.AC_2REG, new String[] {dest, src1});
	}
	private static HARPInstruction harp_not(String dest, String src1) {
		return new HARPInstruction(HARP_NOT, HARPType.AC_3REG, new String[] {dest, src1});
	}


	// Control Flow	
	private static HARPInstruction harp_jmpi(String reldest) {
		return new HARPInstruction(HARP_JMPI, HARPType.AC_1IMM, new String[] {reldest});
	}
	private static HARPInstruction harp_jmpr(String addr) {
		return new HARPInstruction(HARP_JMPR, HARPType.AC_1REG, new String[] {addr});
	}
	private static HARPInstruction harp_jmpi(String link, String reldest) {
		return new HARPInstruction(HARP_JMPI, HARPType.AC_2IMM, new String[] {link, reldest});
	}
	private static HARPInstruction harp_jalr(String link, String reg) {
		return new HARPInstruction(HARP_JALR, HARPType.AC_2REG, new String[] {link, reg});
	}


	// Predicate and Label
	private static HARPInstruction harp_predicate(String param) {
		return new HARPInstruction(HARP_PREDICATE, HARPType.AC_PREDICATE, new String[] {param});
	}

	private static HARPInstruction harp_label(String line) {
		return new HARPInstruction(HARP_LABEL, HARPType.AC_LABEL, new String[] {line});
	}

	// Interrupt
	private static HARPInstruction harp_trap() {
		return new HARPInstruction(HARP_TRAP, HARPType.AC_NONE, new String[]{});
	}


	// PTX only instructions		
	private static HARPInstruction[] harp_mad(String dest, String src1, String src2, String src3) {											
		String temp = getRTemp();
		return new HARPInstruction[] {
				harp_mul(temp, src1, src2), 	// t = a * b 
				harp_add(dest, temp, src3)		// d = t + c;				
		};
	}

	private static HARPInstruction[] harp_sad(String dest, String src1, String src2, String src3) {
		String r_temp = getRTemp();
		String p_temp = getPTemp();
		return new HARPInstruction[] {
				harp_sub(r_temp, src1, src2),						// t = a - b
				harp_isneg(p_temp, r_temp),							// p = t < 0? true : false	
				harp_predicate(p_temp),	harp_neg(r_temp, r_temp), 	// p ? t = t * -1
				harp_add(dest, src3, r_temp)						// d = c + t
		};
	}

	private static HARPInstruction[] harp_abs(String dest, String src1) {		
		String p_temp = getPTemp();								
		return new HARPInstruction[] {
				harp_ld(dest, src1, "0"),							// d = a 
				harp_isneg(p_temp, src1), 							// p = src1 < 0 ? true : false
				harp_predicate(p_temp), harp_neg(dest, dest)		// p ? d = d * -1
		};
	}

	private static HARPInstruction[] harp_min(String dest, String src1, String src2) {
		String r_temp = getRTemp();								
		String p_temp = getPTemp();						
		return new HARPInstruction[] {
				harp_ld(dest, src2, "0"), 							// d = b
				harp_sub(r_temp, src1, src2),						// t = a - b 
				harp_isneg(p_temp, r_temp),							// p = t < 0 ? true : false 
				harp_predicate(p_temp), harp_ld(dest, src1, "0")	// p ? d = a 	
		};
	}

	private static HARPInstruction[] harp_max(String dest, String src1, String src2) {								
		String r_temp = getRTemp();									
		String p_temp = getPTemp();						
		return new HARPInstruction[] {
				harp_ld(dest, src1, "0"),							// d = a 
				harp_sub(r_temp, src1, src2),						// t = a - b 
				harp_isneg(p_temp, r_temp),							// p = t < 0 ? true : false 
				harp_predicate(p_temp), harp_ld(dest, src2, "0")	// p ? d = b 
		};
	}

	private static HARPInstruction[] harp_popc(String dest, String src1) {
		String r_temp = getRTemp();																					
		String p_temp = getPTemp();								
		String l_temp1 = getLTemp();						              
		String l_temp2 = getLTemp();						 
		return new HARPInstruction[] {
				harp_ldi(dest, "0"),								// d = 0  
				harp_label(l_temp1), harp_iszero(p_temp, src1),		// tempLabel: p1 = (a != 0) ? true : false
				harp_predicate(p_temp), harp_jmpi(l_temp2), 		// p1 ? jmpi exitLabel
				harp_andi(r_temp, src1, "1"),						// t = a & 1 
				harp_rtop(p_temp, r_temp), 							// p1 = (t != 0) ? true : false
				harp_predicate(p_temp), harp_addi(dest, dest, "1"), // p1 ? d = d + 1
			   	harp_shri(src1, src1, "1"),							//	a = a >> 1;
				harp_jmpi(l_temp1),									// jmpi tempLabel 
				harp_label(l_temp2)									// exitLabel: 
		};
	}

	private static HARPInstruction[] harp_clz(String dest, String src1) {
		String max = getRTemp();
		String mask = getRTemp();
		String r_temp = getRTemp();
		String p_temp = getPTemp();
		String l_temp1 = getLTemp();
		String l_temp2 = getLTemp();
		String maxValue;
		String maskBitShift;

		if(PTXType.equals(PTXTypes.B32.type())) {
			maxValue = "32";
			maskBitShift = "7";	
		} else {
			maxValue = "64";
			maskBitShift = "15";
		}
		return new HARPInstruction[] {
				harp_ldi(max, maxValue), 									// max = 32
				harp_ldi(mask, "8"),										// mask = 8
				harp_label(l_temp1), harp_shli(mask, mask, maskBitShift),	// tempLabel: mask = mask << 15
				harp_sub(r_temp, max, dest),								// t1 = max - d			
				harp_isneg(p_temp, r_temp),									// p1 = (t1 < 0) ? true : false
				harp_predicate(p_temp), harp_jmpi(l_temp2),					// p1 ? jmpi exitLabel
				harp_and(r_temp, src1, mask),								// t1 = a & mask
				harp_rtop(p_temp, r_temp),									// p1 = (t1 != 0) ? true : false
				harp_predicate(p_temp), harp_jmpi(l_temp2),					// p1 ? jmpi exitLabel
				harp_addi(dest, dest, "1"),									// dest = dest + 1 
				harp_shli(src1, src1, "1"),									// a = a << 1
				harp_jmpi(l_temp1),											// jmpi tempLabel
				harp_label(l_temp2)											// exitLabel:
		};
	}

	private static HARPInstruction[] harp_setp(String... parameters) {
		String option = PTXOptions[0];
		String dest = parameters[0];
		String src1 = parameters[1];
		String src2 = parameters[2];
		String r_temp = getRTemp();
		String l_temp = getLTemp();

		if(option.equals("eq")) {
			return new HARPInstruction[] {
					harp_sub(r_temp, src1, src2),		// temp = src1 - src2
					harp_iszero(dest, r_temp)			// p = (temp == 0) ? true : false
			};
		} else if(option.equals("ne")) {
			return new HARPInstruction[] {
					harp_sub(r_temp, src1, src2),		// temp = src1 - src2
					harp_rtop(dest, r_temp)				// p = (temp != 0) ? true : false
			};	
		} else if(option.equals("lt")) {
			return new HARPInstruction[] {
					harp_sub(r_temp, src1, src2),		// temp = src1 - src2
					harp_isneg(dest, r_temp)			// p = (temp < 0) ? true : false
			};
		} else if(option.equals("gt")) {
			return new HARPInstruction[] {	
					harp_sub(r_temp, src2, src1),		// temp = src2 - src1 
					harp_isneg(dest, r_temp)			// p = (temp < 0) ? true : false
			};
		} else if(option.equals("lo")) {	
			return new HARPInstruction[] {
					harp_sub(r_temp, src1, src2),		// temp = src1 - src2
					harp_isneg(dest, r_temp)			/// p = (temp < 0) ? true : false
			};	
		} else if(option.equals("le")) { 
			return new HARPInstruction[] {
					harp_sub(r_temp, src1, src2),				// temp = src1 - src2
					harp_iszero(dest, r_temp),					// p = (temp == 0) ? true : false
					harp_predicate(dest), harp_jmpi(l_temp), 	// p ? jmpi exitLabel
					harp_isneg(dest, r_temp),					// p = (temp < 0) ? true : false
					harp_label(l_temp)							// exitLabel:
			};
		} else if(option.equals("ls")) {	
			return new HARPInstruction[] {
					harp_sub(r_temp, src1, src2),				// temp = src1 - src2 
					harp_iszero(dest, r_temp),					// p = (temp == 0) ? true : false
					harp_predicate(dest), harp_jmpi(l_temp), 	// p ? jmpi exitLabel
					harp_isneg(dest, r_temp),					// p = (temp < 0) ? true : false
					harp_label(l_temp)							// exitLabel:
			};
		} else if(option.equals("hi")) {
			return new HARPInstruction[] {
					harp_sub(r_temp, src2, src1),		// temp = src2 - src1
					harp_isneg(dest, r_temp)			// p = (temp < 0) ? true : false
			};
		} else if(option.equals("ge")) {
			return new HARPInstruction[] {
					harp_sub(r_temp, src2, src1),				// temp = src2 - src1
					harp_iszero(dest, r_temp),					// p = (temp == 0) ? true : false
					harp_predicate(dest), harp_jmpi(l_temp),	// p ? jmpi exitLabel
					harp_isneg(dest, r_temp),					// p = (temp < 0) ? true : false
					harp_label(l_temp)							// exitLabel:
			};
		} else if(option.equals("hs")) {
			return new HARPInstruction[] {
					harp_sub(r_temp, src2, src1),				// temp = src2 - src1
					harp_iszero(dest, r_temp),					// p = (temp == 0) ? true : false
					harp_predicate(dest), harp_jmpi(l_temp), 	// p ? jmpi exitLabel
					harp_isneg(dest, r_temp),					// p = (temp < 0) ? true : false
					harp_label(l_temp)							// exitLabel:
			};
		} 		
		return null;	
	}

	private static HARPInstruction[] ptx_predicate(PTXInstruction PTXInst) {
		String PTXOpcode = PTXInst.getOpcode();
		String[] PTXArgs = PTXInst.getArgs();
		String[] PTXParams = PTXInst.getParams();
		String PTXPredicate = PTXInst.getPredicate();
		String PTXPredicate_var = PTXInst.getPredicateVar();
		String l_temp = getLTemp();
		ArrayList<HARPInstruction> HARPInsts = new ArrayList<HARPInstruction>();	// return Instruction List
		
		HARPInstruction[] HARPInsts_temp = 
				Map.Instruction(new PTXInstruction(PTXOpcode, PTXArgs, PTXParams));	// Map and get the actual instruction(s)

		if(HARPInsts_temp.length <= 1) {											// This is the case of one-to-one instruction mapping
			if(PTXPredicate.equals("@!"))												// if "@!p"
				HARPInsts.add(harp_notp(PTXPredicate_var, PTXPredicate_var));			// 	  p = !p	
			HARPInsts.add(harp_predicate(PTXPredicate_var));
			HARPInsts.addAll(Arrays.asList(HARPInsts_temp));
		} else {																	// This is the case of one-to-multiple instruction mapping
			if(PTXPredicate.equals("@"))												// if "@p"
				HARPInsts.add(harp_notp(PTXPredicate_var, PTXPredicate_var));			// 	  p = !p
			HARPInsts.add(harp_predicate(PTXPredicate_var));							// p ?
			HARPInsts.add(harp_jmpi(l_temp));											// jmpi exitLabel 
			HARPInsts.addAll(Arrays.asList(HARPInsts_temp));							// PTXInsts
			HARPInsts.add(harp_label(l_temp));											// exitLabel:
		}	
		return HARPInsts.toArray(new HARPInstruction[HARPInsts.size()]);
	}

	private static HARPInstruction[] harp_ld(String dest, String src1) {
		String[] src = src1.substring(1, src1.length()-1).split("\\+");
		String r_temp = getRTemp();

		if(src.length == 1) {	// no offset
			String base = src[0];
			if((base.charAt(0) >= '0' && base.charAt(0) <='9') || base.charAt(0) == '-' ) {	// imm
				return new HARPInstruction[] {harp_ldi(r_temp, "0"), harp_ld(dest, r_temp, base)};			
			} else if(base.charAt(0) == '%') { // reg 
				return new HARPInstruction[] {harp_ld(dest, base, "0")}; 
			} else { // non_reg, non_imm
				return new HARPInstruction[] {harp_ldi(r_temp, base), harp_ld(dest, r_temp, "0")};	
			}
		} else { 	// base and offset
			String base = src[0];
			String offset = src[1];
			if(base.charAt(0) == '%') { // reg + imm
				return new HARPInstruction[] {harp_ld(dest, base, offset)};
			} else {	// non_reg + imm
				return new HARPInstruction[] {harp_ldi(r_temp, base), harp_ld(dest, r_temp, offset)};
			}
		}
	}
	
	private static HARPInstruction[] harp_st(String src1, String value) { 
		String[] dest = src1.substring(1, src1.length()-1).split("\\+");
		String r_temp = getRTemp();

		if(dest.length == 1) {	// no offset
			String base = dest[0];
			if((base.charAt(0) >= '0' && base.charAt(0) <='9') || base.charAt(0) == '-' ) {	// imm
				return new HARPInstruction[] {harp_ldi(r_temp, "0"), harp_st(value, r_temp, base)};			
			} else if(base.charAt(0) == '%') { // reg 
				return new HARPInstruction[] {harp_st(value, base, "0")}; 
			} else { // non_reg, non_imm
				return new HARPInstruction[] {harp_ldi(r_temp, base), harp_st(value, r_temp, "0")};	
			}
		} else { 	// base and offset
			String base = dest[0];
			String offset = dest[1];
			if(base.charAt(0) == '%') { // reg + imm
				return new HARPInstruction[] {harp_st(value, base, offset)};
			} else {	// non_reg + imm
				return new HARPInstruction[] {harp_ldi(r_temp, base), harp_st(value, r_temp, offset)};
			}
		}

	}


}









