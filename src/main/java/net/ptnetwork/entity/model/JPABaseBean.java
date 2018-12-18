package net.ptnetwork.entity.model;

/**
 * @Description 实体相关信息的基类
 * @Author Noah
 * @Date 2018-6-21
 * @Version V1.0
 */
public class JPABaseBean  {
    private static final long serialVersionUID = -707777972820275508L;
    private String name;
    private String remark;

    public String getName() {
        return name;
    }

    /**
     *名称
     */
    public void setName(String name) {
        this.name = name;
    }

    public String getRemark() {
        return remark;
    }

    /**
     *显示中文
     */
    public void setRemark(String remark) {
        this.remark = remark;
    }

}
