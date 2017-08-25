package com.fpoon.letters.domain;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by fpoon on 25.08.2017.
 */

@Data
@Entity
public class Letter {

    @Id
    Character letter;

    Integer score;
}
