package com.server.base.common.baseEntity;

import com.server.base.common.Enums.UserStatus;
import com.server.base.common.converter.UserStatusConverter;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;

@DynamicUpdate
@DynamicInsert
@Getter
@Embeddable
@AllArgsConstructor
@NoArgsConstructor
public class UserDateEntity {
    @ColumnDefault(value = "0")
    @Column(name = "status")
    @Convert(converter = UserStatusConverter.class)
    private UserStatus status;

    @Column(nullable = false, name = "reg_date")
    private LocalDateTime regDate;
    @Column(nullable = true, name = "last_login_date")
    private LocalDateTime lastLoginDate;
    @Column(nullable = true, name = "withdrawal_date")
    private LocalDateTime withdrawalDate;

    @PrePersist
    public void signUp(){
        this.regDate=LocalDateTime.now();
    }
    @PostPersist
    public void signIn(){
        this.lastLoginDate = LocalDateTime.now();
    }
    @PostUpdate
    public void withdrawal(){
        if(this.status.getStatus().equals(-1)){
            this.withdrawalDate=LocalDateTime.now();
        }
    }

}