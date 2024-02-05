package com.company.core.model;

import com.company.exception.DataValueException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

/**
 * Medication parameters:
 * <li>name (allowed only letters, numbers, ‘-‘, ‘_’);</li>
 * <li>weight;</li>
 * <li>code (allowed only upper case letters, underscore and numbers);</li>
 * <li>image (picture of the medication case).</li>
 */

@Table("medication")
public class Medication {
    private static final Logger log = LoggerFactory.getLogger(Medication.class);
    @Id
    @Column("id")
    private UUID id;
    @Column("name")
    private String name;
    @Column("weight")
    private Double weight;
    @Column("code")
    private String code;
    @Column("image")
    private byte[] image;


    public Medication() {
    }

    public Medication(UUID id, String name, Double weight, String code) {
        this.id = id;
        this.name = name;
        this.weight = weight;
        this.code = code;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        for (int i = 0; i < name.length() - 1; i++) {
            String str = name.substring(i, i + 1);
            if (!(str.matches("^[0-9a-zA-Z_-]"))) {
                log.error("Name contains forbidden symbols: {}", str);
                throw new DataValueException(new Exception());
            }
        }
        this.name = name;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        for (int i = 0; i < code.length() - 1; i++) {
            String str = code.substring(i, i + 1);
            if (!(str.matches("^[0-9A-Z_-]"))) {
                log.error("Code contains forbidden symbols: {}", str);
                throw new DataValueException(new Exception());
            }
        }
        this.code = code;
    }

    public byte[] getImage() {
        return image;
    }

    public void setImage(byte[] image) {
        this.image = image;
    }
}