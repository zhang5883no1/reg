package reg.domain;

import java.io.Serializable;

import javax.persistence.Entity;
import javax.persistence.Id;

@Entity
public class MobCount implements Serializable{

	@Id
	private String mobile;
	private int count;
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}
	/** 
	*  
	*  
	* @param mobile
	* @param count 
	*/ 
	
	public MobCount(String mobile, int count) {
		super();
		this.mobile = mobile;
		this.count = count;
	}
	/** 
	*  
	*   
	*/ 
	
	public MobCount() {
		super();
	}
	
	
}
