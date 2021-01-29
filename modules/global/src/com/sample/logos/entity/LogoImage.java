package com.sample.logos.entity;

import com.haulmont.chile.core.annotations.NamePattern;
import com.haulmont.cuba.core.entity.StandardEntity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Table(name = "LOGOS_LOGO_IMAGE")
@Entity(name = "logos_LogoImage")
@NamePattern("%s|fileName")
public class LogoImage extends StandardEntity {
    private static final long serialVersionUID = 826591073118432885L;

    @Column(name = "FILE_NAME")
    private String fileName;

    @Column(name = "CONTENT")
    private byte[] content;

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public byte[] getContent() {
        return content;
    }

    public void setContent(byte[] content) {
        this.content = content;
    }

}