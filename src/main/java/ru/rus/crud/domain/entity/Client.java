package ru.rus.crud.domain.entity;

import ru.rus.crud.domain.data.RiskProfile;

import javax.persistence.*;

@Entity
@Table(name = "clients", schema = "clients")
public class Client {
    private Long id;
    private RiskProfile riskProfile;

    public Client(RiskProfile riskProfile) {
        this.riskProfile = riskProfile;
    }

    public Client() { }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "risk_profile", nullable = false)
    @Enumerated(EnumType.STRING)
    public RiskProfile getRiskProfile() {
        return riskProfile;
    }

    public void setRiskProfile(RiskProfile riskProfile) {
        this.riskProfile = riskProfile;
    }
}
