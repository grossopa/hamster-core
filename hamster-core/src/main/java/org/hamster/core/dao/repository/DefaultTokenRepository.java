/**
 * 
 */
package org.hamster.core.dao.repository;

import java.util.Date;
import java.util.List;

import org.apache.commons.collections4.CollectionUtils;
import org.hamster.core.dao.entity.PersistentLoginsEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.stereotype.Repository;

/**
 * Default Hibernate-based token repository
 *
 * @see org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
@Repository
public class DefaultTokenRepository implements PersistentTokenRepository {

    private static final Logger log = LoggerFactory.getLogger(DefaultTokenRepository.class);

    @Autowired
    private PersistentLoginsRepository persistentLoginsRepository;

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.web.authentication.rememberme. PersistentTokenRepository#createNewToken(org.springframework.security.web .authentication.rememberme.PersistentRememberMeToken)
     */
    @Override
    public void createNewToken(PersistentRememberMeToken token) {
        persistentLoginsRepository.save(mapPersistentLoginsEntity(token));
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.web.authentication.rememberme. PersistentTokenRepository#updateToken(java.lang.String, java.lang.String, java.util.Date)
     */
    @Override
    public void updateToken(String series, String tokenValue, Date lastUsed) {
        PersistentLoginsEntity entity = persistentLoginsRepository.findOne(series);
        if (entity == null) {
            log.debug("Updating token for series '{}' returned no results.", series);
            return;
        }
        entity.setToken(tokenValue);
        entity.setLastUsed(lastUsed);
        persistentLoginsRepository.save(entity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.web.authentication.rememberme. PersistentTokenRepository#getTokenForSeries(java.lang.String)
     */
    @Override
    public PersistentRememberMeToken getTokenForSeries(String seriesId) {
        PersistentLoginsEntity entity = persistentLoginsRepository.findOne(seriesId);
        if (entity == null) {
            log.debug("Querying token for series '{}' returned no results.", seriesId );
            return null;
        }

        return mapPersistentRememberMeToken(entity);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.springframework.security.web.authentication.rememberme. PersistentTokenRepository#removeUserTokens(java.lang.String)
     */
    @Override
    public void removeUserTokens(String username) {
        List<PersistentLoginsEntity> entities = persistentLoginsRepository.findByUsername(username);
        if (CollectionUtils.isEmpty(entities)) {
            log.debug("Querying token for username '{}' returned no results.", username);
            return;
        }
        persistentLoginsRepository.delete(entities);
    }

    public PersistentRememberMeToken mapPersistentRememberMeToken(PersistentLoginsEntity entity) {
        return new PersistentRememberMeToken(entity.getUsername(), entity.getSeries(), entity.getToken(),
                entity.getLastUsed());
    }

    public PersistentLoginsEntity mapPersistentLoginsEntity(PersistentRememberMeToken token) {
        return new PersistentLoginsEntity(token.getUsername(), token.getSeries(), token.getTokenValue(),
                token.getDate());
    }

}
