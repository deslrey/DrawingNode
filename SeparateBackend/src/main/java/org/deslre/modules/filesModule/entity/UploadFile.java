package org.deslre.modules.filesModule.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.time.Instant;
import java.time.LocalDateTime;

@Getter
@Setter
@Entity
@ToString
@DynamicUpdate
@Table(name = "upload_files")
public class UploadFile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private Integer id;

    @Column(name = "file_name", nullable = false, length = 64)
    private String fileName;

    @Column(name = "relative_path", length = 128)
    private String relativePath;

    @Column(name = "create_time", updatable = false)
    private LocalDateTime createTime;

    @Column(name = "update_time", updatable = false)
    private LocalDateTime updateTime;

    @Column(name = "exits")
    private Boolean exits;
}
