package pet.dao.model;

import java.util.Date;
import javax.persistence.*;

public class Adopt {
    @Id
    @Column(name = "adopt_id")
    private Integer adoptId;

    @Column(name = "adopt_user")
    private Integer adoptUser;

    @Column(name = "publish_user")
    private Integer publishUser;

    @Column(name = "pet_id")
    private Integer petId;

    @Column(name = "adopt_date")
    private Date adoptDate;

    private String remark;

    /**
     * @return adopt_id
     */
    public Integer getAdoptId() {
        return adoptId;
    }

    /**
     * @param adoptId
     */
    public void setAdoptId(Integer adoptId) {
        this.adoptId = adoptId;
    }

    public Integer getAdoptUser() {
		return adoptUser;
	}

	public void setAdoptUser(Integer adoptUser) {
		this.adoptUser = adoptUser;
	}

	public Integer getPublishUser() {
		return publishUser;
	}

	public void setPublishUser(Integer publishUser) {
		this.publishUser = publishUser;
	}

	/**
     * @return pet_id
     */
    public Integer getPetId() {
        return petId;
    }

    /**
     * @param petId
     */
    public void setPetId(Integer petId) {
        this.petId = petId;
    }

    /**
     * @return adopt_date
     */
    public Date getAdoptDate() {
        return adoptDate;
    }

    /**
     * @param adoptDate
     */
    public void setAdoptDate(Date adoptDate) {
        this.adoptDate = adoptDate;
    }

    /**
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }
}