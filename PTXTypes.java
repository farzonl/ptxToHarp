public enum PTXTypes {
		B8("b8"), B16("b16"), B32("b32"), B64("b64"), S8("s8"), S16("s16"), S32("s32"), S64("s64"), 
		U8("u8"), U16("u16"), U32("u32"), U64("u64"), F16("f16"), F32("f32"), F64("f64");	
		private String statusCode;
		 
		private PTXTypes(String s) {
			statusCode = s;
		}
			 
		public String type() {
			return statusCode;
		}
}


