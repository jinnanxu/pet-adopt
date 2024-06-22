package pet.dao.model;

import java.util.Date;
import javax.persistence.*;

public class Task {
    @Id
    @Column(name = "task_id")
    private Integer taskId;

    @Column(name = "pet_id")
    private Integer petId;

    @Column(name = "user_id")
    private Integer userId;

    @Column(name = "next_time")
    private Date nextTime;

    @Column(name = "last_time")
    private Date lastTime;

    private String remark;

    /**
     * @return task_id
     */
    public Integer getTaskId() {
        return taskId;
    }

    /**
     * @param taskId
     */
    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
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
     * @return next_time
     */
    public Date getNextTime() {
        return nextTime;
    }

    /**
     * @param nextTime
     */
    public void setNextTime(Date nextTime) {
        this.nextTime = nextTime;
    }

    /**
     * @return last_time
     */
    public Date getLastTime() {
        return lastTime;
    }

    /**
     * @param lastTime
     */
    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
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