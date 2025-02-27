package com.emotionalcart.hellosearchapi.domain.entity;

import jakarta.persistence.MappedSuperclass;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;

@MappedSuperclass
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public abstract class BaseImageEntity extends BaseEntity {

    private String bucketName;

    private String originalFileName;

    private String filePath;

    private String fileType;

    private Long fileSize;

    private Integer fileOrder;

}
