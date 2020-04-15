package cn.lige2333.paipai.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Code implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    private String rancode;

    private String telephone;

    @Column(name = "expire_date")
    private Date expireDate;

    private static final long serialVersionUID = 1L;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return rancode
     */
    public String getRancode() {
        return rancode;
    }

    /**
     * @param rancode
     */
    public void setRancode(String rancode) {
        this.rancode = rancode;
    }

    /**
     * @return telephone
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * @param telephone
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    /**
     * @return expire_date
     */
    public Date getExpireDate() {
        return expireDate;
    }

    /**
     * @param expireDate
     */
    public void setExpireDate(Date expireDate) {
        this.expireDate = expireDate;
    }
}