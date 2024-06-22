package pet.dao.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "black_list")
public class BlackList {
    @Id
    @Column(name = "black_list_id")
    private Integer blackListId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "put_in_time")
    private Date putInTime;

    private String remark;

    /**
     * @return black_list_id
     */
    public Integer getBlackListId() {
        return blackListId;
    }

    /**
     * @param blackListId
     */
    public void setBlackListId(Integer blackListId) {
        this.blackListId = blackListId;
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
     * @return put_in_time
     */
    public Date getPutInTime() {
        return putInTime;
    }

    /**
     * @param putInTime
     */
    public void setPutInTime(Date putInTime) {
        this.putInTime = putInTime;
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