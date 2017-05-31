package org.hamster.core.dao.test.repository;

import java.util.Date;

import org.hamster.core.dao.entity.PersistentLoginsEntity;
import org.hamster.core.dao.repository.DefaultTokenRepository;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.web.authentication.rememberme.PersistentRememberMeToken;

public class DefaultTokenRepositoryTest extends AbstractCoreDaoSpringTest {
    
    @Autowired
    private DefaultTokenRepository repository;
    
    @Test
    public void testCreateNewToken() throws Exception {
        PersistentRememberMeToken token = new PersistentRememberMeToken("username", "series1", "token value", new Date());
        repository.createNewToken(token);
        
        PersistentRememberMeToken savedToken = repository.getTokenForSeries("series1");
        
        Assert.assertEquals(savedToken.getSeries(), token.getSeries());
        Assert.assertEquals(savedToken.getTokenValue(), token.getTokenValue());
        Assert.assertEquals(savedToken.getUsername(), token.getUsername());
        Assert.assertEquals(savedToken.getDate(), token.getDate());
    }

    @Test
    public void testUpdateToken() throws Exception {
        String series = "series2";
        String tokenValue = "token value 2";
        Date lastUsed = new Date();

        repository.updateToken(series, tokenValue, lastUsed);
    }
    
    @Test
    public void testUpdateTokenNotExists() throws Exception {
        String series = "series2 not exists";
        String tokenValue = "token value 2";
        Date lastUsed = new Date();
        repository.updateToken(series, tokenValue, lastUsed);
    }


    @Test
    public void testGetTokenForSeries() throws Exception {
        String seriesId = "series2";
        PersistentRememberMeToken result;

        result = repository.getTokenForSeries(seriesId);
        
        Assert.assertNotNull(result);
        Assert.assertEquals("token value 233", result.getTokenValue());
    }

    @Test
    public void testRemoveUserTokens() throws Exception {
        String username = "username33";
        
        Assert.assertNotNull(repository.getTokenForSeries("series4"));

        repository.removeUserTokens(username);
        
        Assert.assertNull(repository.getTokenForSeries("series4"));
    }
    
    @Test
    public void testRemoveUserTokensNotExists() throws Exception {
        String username = "username33 Not exists";
        repository.removeUserTokens(username);
    }

    @Test
    public void testMapPersistentRememberMeToken() throws Exception {
        PersistentLoginsEntity entity = new PersistentLoginsEntity("username99", "series88", "token value 233", new Date());
        PersistentRememberMeToken result;

        result = repository.mapPersistentRememberMeToken(entity);
        
        Assert.assertEquals(result.getSeries(), entity.getSeries());
        Assert.assertEquals(result.getTokenValue(), entity.getToken());
        Assert.assertEquals(result.getUsername(), entity.getUsername());
        Assert.assertEquals(result.getDate(), entity.getLastUsed());
    }

    @Test
    public void testMapPersistentLoginsEntity() throws Exception {
        PersistentRememberMeToken token = new PersistentRememberMeToken("username99", "series88", "token value 233", new Date());;
        PersistentLoginsEntity result;

        result = repository.mapPersistentLoginsEntity(token);
        
        Assert.assertEquals(result.getSeries(), token.getSeries());
        Assert.assertEquals(result.getToken(), token.getTokenValue());
        Assert.assertEquals(result.getUsername(), token.getUsername());
        Assert.assertEquals(result.getLastUsed(), token.getDate());
    }

}