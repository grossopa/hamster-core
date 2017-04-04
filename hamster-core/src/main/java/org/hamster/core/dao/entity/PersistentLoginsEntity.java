/**
 * 
 */
package org.hamster.core.dao.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Size;

import org.hamster.core.dao.consts.EntityConsts;
import org.hibernate.annotations.Type;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * For Spring Security Logins Usage, it's for the replacement and hibernatise
 * the default JdbcTokenRepository:<br>
 * <p>
 * <tt>
 * create table persistent_logins (username varchar(64) not null, series varchar(64) primary key, 
            token varchar(64) not null, last_used timestamp not null)
 * </tt>
 * </p>
 * 
 * @see org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Entity
@Table(name = EntityConsts.DB_PREFIX + "persistent_logins")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersistentLoginsEntity {

    @Size(min = 1, max = 64)
    @Column(name = "username", length = 64, nullable = false)
    private String username;

    @Id
    @Size(min = 1, max = 64)
    @Column(name = "series", length = 64, nullable = false)
    private String series;

    @Size(min = 1, max = 64)
    @Column(name = "token", length = 64, nullable = false)
    private String token;

    @Type(type = "timestamp")
    @Column(name = "last_used", nullable = false)
    private Date lastUsed;
}
