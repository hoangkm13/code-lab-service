package com.example.codelabsvc.constant;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Getter
public enum Skill {
    BASIC("BASIC", "Cơ bản"),
    INTERMEDIATE("INTERMEDIATE", "Thành thục");

    private final String value;
    private final String reasonPhrase;

    Skill(String value, String reasonPhrase) {
        this.value = value;
        this.reasonPhrase = reasonPhrase;
    }

    public String value() {
        return this.value;
    }

    public String getReasonPhrase() {
        return this.reasonPhrase;
    }

    public String toString() {
        return this.value + " " + this.name();
    }

    @Nullable
    public static Skill resolve(String status) {
        Skill[] var1 = values();

        for (Skill repaymentStatus : var1) {
            if (Objects.equals(repaymentStatus.value, status)) {
                return repaymentStatus;
            }
        }

        return null;
    }
}
