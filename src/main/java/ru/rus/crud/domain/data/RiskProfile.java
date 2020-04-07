package ru.rus.crud.domain.data;

public enum RiskProfile {
    LOW(1),
    NORMAL(2),
    HIGH(3);

    private Integer ordinal;

    RiskProfile(Integer ordinal) {
        this.ordinal = ordinal;
    }

    public Integer getOrdinal() {
        return ordinal;
    }
}
