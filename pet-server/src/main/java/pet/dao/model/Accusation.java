package pet.dao.model;

import java.util.Date;
import javax.persistence.*;

public class Accusation {
    @Id
    @Column(name = "acc_id")
    private Integer accId;

    private Integer accusater;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "pet_id")
    private Integer petId;

    private String reason;

    @Column(name = "acc_time")
    private Date accTime;

    private String status;

    /**
     * @return acc_id
     */
    public Integer getAccId() {
        return accId;
    }

    /**
     * @param accId
     */
    public void setAccId(Integer accId) {
        this.accId = accId;
    }

    /**
     * @return accusater
     */
    public Integer getAccusater() {
        return accusater;
    }

    /**
     * @param accusater
     */
    public void setAccusater(Integer accusater) {
        this.accusater = accusater;
    }

    /**
     * @return user_id
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
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
     * @return reason
     */
    public String getReason() {
        return reason;
    }

    /**
     * @param reason
     */
    public void setReason(String reason) {
        this.reason = reason;
    }

    /**
     * @return acc_time
     */
    public Date getAccTime() {
        return accTime;
    }

    /**
     * @param accTime
     */
    public void setAccTime(Date accTime) {
        this.accTime = accTime;
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
}