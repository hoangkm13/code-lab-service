package com.example.codelabsvc.constant;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Getter
public enum Subdomain {
    INTRODUCTION("INTRODUCTION", "Giới thiệu"),
    CONDITIONALS_AND_LOOPS("CONDITIONALS_AND_LOOPS", "Điều kiện và vòng lặp"),
    ARRAYS_AND_STRINGS("ARRAYS_AND_STRINGS", "Chuỗi và chuỗi kỹ tự"),
    FUNCTIONS("FUNCTIONS", "hàm"),
    STRUCTS_AND_ENUMS("STRUCTS_AND_ENUMS", "Cấu trúc và enum");

    private final String value;
    private final String reasonPhrase;

    Subdomain(String value, String reasonPhrase) {
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
    public static Subdomain resolve(String status) {
        Subdomain[] var1 = values();

        for (Subdomain repaymentStatus : var1) {
            if (Objects.equals(repaymentStatus.value, status)) {
                return repaymentStatus;
            }
        }

        return null;
    }
}
