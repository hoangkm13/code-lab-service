package com.example.codelabsvc.constant;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Getter
public enum Subdomain {
    INTRODUCTION("introduction", "Giới thiệu"),
    CONDITIONALS_AND_LOOPS("conditionals_and_loops", "Điều kiện và vòng lặp"),
    ARRAYS_AND_STRINGS("arrays_and_strings", "Chuỗi và chuỗi kỹ tự"),
    FUNCTIONS("functions", "hàm"),
    STRUCTS_AND_ENUMS("structs and enums", "Cấu trúc và enum");

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
