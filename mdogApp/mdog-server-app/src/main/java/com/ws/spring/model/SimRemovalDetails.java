package com.ws.spring.model;

import java.io.Serializable;
import java.sql.Clob;
import java.sql.Date;

import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

@Entity
@Table(name = "t_ws_sim_removal_details")
@EntityListeners(AuditingEntityListener.class)

public class SimRemovalDetails implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long id;

    private String mobileNumber;

    private String imeiNum;

    private String langitude;

    private String lattitude;

    @CreationTimestamp
    private Date insertedDate;

    private Clob image;

    
    public SimRemovalDetails() {
		super();
	}


	public SimRemovalDetails(Long id, String mobileNumber, String imeiNum, String langitude, String lattitude,
			Date insertedDate, Clob image) {
		super();
		this.id = id;
		this.mobileNumber = mobileNumber;
		this.imeiNum = imeiNum;
		this.langitude = langitude;
		this.lattitude = lattitude;
		this.insertedDate = insertedDate;
		this.image = image;
	}


	@Override
    public String toString() {
        return String.format(
                "SimRemovalDetails [id=%s, mobileNumber=%s, imeiNum=%s, langitude=%s, lattitude=%s, insertedDate=%s, image=%s]",
                id, mobileNumber, imeiNum, langitude, lattitude, insertedDate, image);
    }


	public Long getId() {
		return id;
	}


	public void setId(Long id) {
		this.id = id;
	}


	public String getMobileNumber() {
		return mobileNumber;
	}


	public void setMobileNumber(String mobileNumber) {
		this.mobileNumber = mobileNumber;
	}


	public String getImeiNum() {
		return imeiNum;
	}


	public void setImeiNum(String imeiNum) {
		this.imeiNum = imeiNum;
	}


	public String getLangitude() {
		return langitude;
	}


	public void setLangitude(String langitude) {
		this.langitude = langitude;
	}


	public String getLattitude() {
		return lattitude;
	}


	public void setLattitude(String lattitude) {
		this.lattitude = lattitude;
	}


	public Date getInsertedDate() {
		return insertedDate;
	}


	public void setInsertedDate(Date insertedDate) {
		this.insertedDate = insertedDate;
	}


	public Clob getImage() {
		return image;
	}


	public void setImage(Clob image) {
		this.image = image;
	}

	
}
