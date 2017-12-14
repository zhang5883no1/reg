package reg.domain;

public class ResultDto {

	//0 失败 1 成功
	private int code;
	
	private String msg;

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getMsg() {
		return msg;
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	/** 
	*  
	*  
	* @param code
	* @param msg 
	*/ 
	
	public ResultDto(int code, String msg) {
		super();
		this.code = code;
		this.msg = msg;
	}

	/** 
	*  
	*   
	*/ 
	
	public ResultDto() {
		super();
	}
	
	
}
