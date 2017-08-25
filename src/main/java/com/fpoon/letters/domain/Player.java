package com.fpoon.letters.domain;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * Created by fpoon on 13.08.2017.
 */

@Data
@Entity
public class Player {

    @Id
    Long id;

    @Column(unique = true)
    String name;
}
