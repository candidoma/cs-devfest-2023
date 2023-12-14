package com.candidoma.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Getter
@Setter
@Entity
@NoArgsConstructor
public class Calc {

    private @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY) Long id;

    private int digit;
    private String value;
    private long duration;
    public Calc(int digit, String value, Long duration) {
        this.id=1L;
        this.digit = digit;
        this.value = value;
        this.duration=duration;
    }

}
