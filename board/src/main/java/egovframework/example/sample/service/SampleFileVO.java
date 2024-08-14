package egovframework.example.sample.service;



import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SampleFileVO {
	private String fileId;
    private String sampleId;
    private String fileName;
    private String filePath;
    private long fileSize;
    private String fileType;
    private LocalDateTime uploadDate;
}
