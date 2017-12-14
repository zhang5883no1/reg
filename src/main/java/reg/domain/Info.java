package reg.domain;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;

@Entity
public class Info implements Serializable{

	@Id
	@GeneratedValue
	private long aid;
	
	private String mob;
	
	private String url;
	
	private String referer;
	
	private String d1;
	
	private String d2;
	
	private String d3;
	
	private Date date;
	
	private String nickName;

	public long getAid() {
		return aid;
	}

	public void setAid(long aid) {
		this.aid = aid;
	}

	public String getMob() {
		return mob;
	}

	public void setMob(String mob) {
		this.mob = mob;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getReferer() {
		return referer;
	}

	public void setReferer(String referer) {
		this.referer = referer;
	}

	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}
	
	public String getNickName() {
		return nickName;
	}

	public void setNickName(String nickName) {
		this.nickName = nickName;
	}


	public String getD1() {
		return d1;
	}

	public void setD1(String d1) {
		this.d1 = d1;
	}

	public String getD2() {
		return d2;
	}

	public void setD2(String d2) {
		this.d2 = d2;
	}

	public String getD3() {
		return d3;
	}

	public void setD3(String d3) {
		this.d3 = d3;
	}

	public Info(long aid, String mob, String url, String referer, String d1, String d2, String d3, Date date,
			String nickName) {
		super();
		this.aid = aid;
		this.mob = mob;
		this.url = url;
		this.referer = referer;
		this.d1 = d1;
		this.d2 = d2;
		this.d3 = d3;
		this.date = date;
		this.nickName = nickName;
	}


	public Info() {
		super();
	}
	
	
	

}
