package com.tboostAI_core.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

@Data
@Entity
@Table(name = "seller")
@AllArgsConstructor
@NoArgsConstructor
public class SellerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long uuid;

    @Column(name = "platform")
    private String platform;

    @Column(name = "username", length = 100)
    private String username;

    @Column(name = "feedback_percentage", length = 10)
    private String feedbackPercentage;

    @Column(name = "feedback_score")
    private Integer feedbackScore;

    @OneToMany(mappedBy = "seller", cascade = CascadeType.ALL, fetch = FetchType.EAGER, orphanRemoval = true)
    private List<VehicleBasicInfoEntity> vehicles;

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        SellerEntity that = (SellerEntity) object;
        return Objects.equals(platform, that.platform) && Objects.equals(username, that.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(platform, username);
    }

    @Override
    public String toString() {
        return "SellerEntity{" +
                "platform='" + platform + '\'' +
                ", username='" + username + '\'' +
                ", feedbackPercentage='" + feedbackPercentage + '\'' +
                ", feedbackScore=" + feedbackScore +
                '}';
    }
}
