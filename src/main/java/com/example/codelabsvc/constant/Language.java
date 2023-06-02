package com.example.codelabsvc.constant;

import lombok.Getter;
import org.springframework.lang.Nullable;

import java.util.Objects;

@Getter
public enum Language {
    JAVA("JAVA", "Ngôn ngữ java"),
    PYTHON("PYTHON", "Ngôn ngữ python"),
    CPP("CPP", "Ngôn ngữ cpp"),
    CS("CS", "Ngôn ngữ cs"),
    C("C", "Ngôn ngữ c"),
    RUBY("RUBY", "Ngôn ngữ ruby");

    private final String value;
    private final String reasonPhrase;

    Language(String value, String reasonPhrase) {
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
    public static Language resolve(String status) {
        Language[] var1 = values();

        for (Language repaymentStatus : var1) {
            if (Objects.equals(repaymentStatus.value, status)) {
                return repaymentStatus;
            }
        }

        return null;
    }
}
