package pet.dao.model;

import java.util.Date;

import javax.persistence.*;

public class Pet {
    @Id
    @Column(name = "pet_id")
    private Integer petId;

    private String category;
    
    @Column(name = "sub_category")
    private String subCategory;

    private String age;

    private String gender;

    private String jieyu;

    private String mianyi;

    private String title;

    private String pic;

    @Column(name = "pos_lat")
    private String posLat;

    @Column(name = "pos_lng")
    private String posLng;

    @Column(name = "pos_txt")
    private String posTxt;

    private String status;

    private String detail;

    private String remark;
    
    @Column(name = "publish_time")
    private Date publishTime;
    
    @Column(name = "user_id")
    private Integer userId;

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
     * @return category
     */
    public String getCategory() {
        return category;
    }

    /**
     * @param category
     */
    public void setCategory(String category) {
        this.category = category;
    }

    /**
     * @return sub_category
     */
    public String getSubCategory() {
        return subCategory;
    }

    /**
     * @param subCategory
     */
    public void setSubCategory(String subCategory) {
        this.subCategory = subCategory;
    }

    
    public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	/**
     * @return gender
     */
    public String getGender() {
        return gender;
    }

    /**
     * @param gender
     */
    public void setGender(String gender) {
        this.gender = gender;
    }

    /**
     * @return jieyu
     */
    public String getJieyu() {
        return jieyu;
    }

    /**
     * @param jieyu
     */
    public void setJieyu(String jieyu) {
        this.jieyu = jieyu;
    }

    /**
     * @return mianyi
     */
    public String getMianyi() {
        return mianyi;
    }

    /**
     * @param mianyi
     */
    public void setMianyi(String mianyi) {
        this.mianyi = mianyi;
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title;
    }

    /**
     * @return pic
     */
    public String getPic() {
        return pic;
    }

    /**
     * @param pic
     */
    public void setPic(String pic) {
        this.pic = pic;
    }

    /**
     * @return pos_lat
     */
    public String getPosLat() {
        return posLat;
    }

    /**
     * @param posLat
     */
    public void setPosLat(String posLat) {
        this.posLat = posLat;
    }

    /**
     * @return pos_lng
     */
    public String getPosLng() {
        return posLng;
    }

    /**
     * @param posLng
     */
    public void setPosLng(String posLng) {
        this.posLng = posLng;
    }

    /**
     * @return pos_txt
     */
    public String getPosTxt() {
        return posTxt;
    }

    /**
     * @param posTxt
     */
    public void setPosTxt(String posTxt) {
        this.posTxt = posTxt;
    }

    /**
     * @return status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return detail
     */
    public String getDetail() {
        return detail;
    }

    /**
     * @param detail
     */
    public void setDetail(String detail) {
        this.detail = detail;
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

	public Date getPublishTime() {
		return publishTime;
	}

	public void setPublishTime(Date publishTime) {
		this.publishTime = publishTime;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}
}