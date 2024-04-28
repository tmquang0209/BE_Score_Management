package com.tmquang.score.models;


import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "majors")
public class Major {
    @Id
    @Column(name = "major_code")
    private String majorCode;

    @Column(name = "major_name")
    private String majorName;

    @Column(name = "created_at")
    private String createAt;

    @Column(name = "updated_at")
    private String updateAt;

    public Major() {}

    public Major(String majorCode, String majorName, String createAt, String updateAt) {
        this.majorCode = majorCode;
        this.majorName = majorName;
        this.createAt = createAt;
        this.updateAt = updateAt;
    }

    public String getMajorCode() {
        return majorCode;
    }

    public String getMajorName() {
        return majorName;
    }

    public String getCreateAt() {
        return createAt;
    }

    public String getUpdateAt() {
        return updateAt;
    }
}
