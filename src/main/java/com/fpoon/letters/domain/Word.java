package com.fpoon.letters.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by fpoon on 25.08.2017.
 */

@Data
@Entity
public class Word {

    @Id
    Long id;

    @Column(unique = true, nullable = false)
    String word;

    @Column(length = 255, nullable = true)
    String source;
}
