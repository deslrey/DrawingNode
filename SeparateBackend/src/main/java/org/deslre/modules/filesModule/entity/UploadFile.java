package org.deslre.modules.filesModule.entity;

import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.Instant;

@Getter
@Setter
@Entity
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

    @Column(name = "create_time", nullable = true)
    private Instant createTime;

    @Column(name = "update_time", nullable = true)
    private Instant updateTime;

}