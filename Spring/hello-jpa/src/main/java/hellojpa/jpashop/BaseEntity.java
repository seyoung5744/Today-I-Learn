package hellojpa.jpashop;

import java.time.LocalDateTime;
import javax.persistence.MappedSuperclass;

@MappedSuperclass
public abstract class BaseEntity {

    private String createBy;

    private LocalDateTime createdDate;

    private String LastModifiedBy;

    private LocalDateTime lastModifiedDate;
}
