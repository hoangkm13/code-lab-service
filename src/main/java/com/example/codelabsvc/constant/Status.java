package com.example.codelabsvc.constant;

import lombok.*;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Getter
public enum Status {
    SOLVED("solved", "Đã giải"),
    UNSOLVED("unsolved", "Chưa giải");

    private final String value;
    private final String reasonPhrase;

    Status(String value, String reasonPhrase) {
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
    public static Status resolve(String status) {
        Status[] var1 = values();

        for (Status repaymentStatus : var1) {
            if (Objects.equals(repaymentStatus.value, status)) {
                return repaymentStatus;
            }
        }

        return null;
    }
}
