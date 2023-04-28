package com.example.codelabsvc.constant;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Getter
public enum Difficulty {
    VERY_EASY("very_easy", "Rất dễ"),
    EASY("EASY", "Dễ"),
    NORMAL("NORMAL", "Bình thường"),
    HARD("HARD", "Khó"),
    VERY_HARD("VERY_HARD", "Rất khó");

    private final String value;
    private final String reasonPhrase;

    Difficulty(String value, String reasonPhrase) {
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
    public static Difficulty resolve(String status) {
        Difficulty[] var1 = values();

        for (Difficulty repaymentStatus : var1) {
            if (Objects.equals(repaymentStatus.value, status)) {
                return repaymentStatus;
            }
        }

        return null;
    }
    }
