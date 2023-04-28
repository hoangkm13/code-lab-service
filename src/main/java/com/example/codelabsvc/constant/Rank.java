package com.example.codelabsvc.constant;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Getter
public enum Rank {
    BEGINNER("BEGINNER", "Khởi động"),
    FRESHER("FRESHER", "Code đơn giản");

    private final String value;
    private final String reasonPhrase;

    Rank(String value, String reasonPhrase) {
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
    public static Rank resolve(String status) {
        Rank[] var1 = values();

        for (Rank repaymentStatus : var1) {
            if (Objects.equals(repaymentStatus.value, status)) {
                return repaymentStatus;
            }
        }

        return null;
    }
    }
