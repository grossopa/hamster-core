/**
 * 
 */
package org.hamster.core.dao.test.util;

import java.util.Date;

import org.hamster.core.api.consts.StatusType;
import org.hamster.core.api.model.base.ManageableIfc;
import org.hamster.core.dao.util.EntityUtils;
import org.junit.Assert;
import org.junit.Test;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

/**
 * @author <a href="mailto:grossopaforever@gmail.com">Jack Yin</a>
 * @since 1.0
 */
public class EntityUtilsTest {

    @Test
    public void testUpdateStatus() {
        TestEntity entity = new TestEntity();
        Assert.assertNull(entity.getStatus());
        EntityUtils.updateStatus(entity);
        Assert.assertEquals(StatusType.ACTIVE, entity.getStatus());
    }
    
    @Test
    public void testUpdateStatusNotNull() {
        TestEntity entity = new TestEntity();
        entity.setStatus("Not Active");
        EntityUtils.updateStatus(entity);
        Assert.assertEquals("Not Active", entity.getStatus());
    }
    
    @Test
    public void testUpdateModifyInfoNewEntity() {
        TestEntity entity = new TestEntity();
        EntityUtils.updateModifyInfo(entity, "simpleUser");
        
        Assert.assertNotNull(entity.getCreatedOn());
        Assert.assertNotNull(entity.getUpdatedOn());
        Assert.assertEquals(entity.getCreatedOn(), entity.getUpdatedOn());
        Assert.assertEquals("simpleUser", entity.getCreatedBy());
        Assert.assertEquals("simpleUser", entity.getUpdatedBy());
        Assert.assertEquals(StatusType.ACTIVE, entity.getStatus());
    }
    
    @Test
    public void testUpdateModifyInfoExistingEntity() {
        TestEntity entity = new TestEntity();
        entity.setCreatedBy("Jone Doe");
        entity.setCreatedOn(new Date(0));
        entity.setStatus("InActive");
        
        EntityUtils.updateModifyInfo(entity, "simpleUser");
        
        Assert.assertNotNull(entity.getUpdatedOn());
        Assert.assertNotEquals(entity.getCreatedOn(), entity.getUpdatedOn());
        Assert.assertEquals("Jone Doe", entity.getCreatedBy());
        Assert.assertEquals("simpleUser", entity.getUpdatedBy());
        Assert.assertEquals("InActive", entity.getStatus());
    }
    
    @Test
    public void testUpdateModifyInfoBrokenData() {
        TestEntity entity = new TestEntity();
        entity.setCreatedBy("Jone Doe");
        // createdOn is missing
        entity.setStatus("InActive");
        
        EntityUtils.updateModifyInfo(entity, "simpleUser");
        
        Assert.assertNotNull(entity.getUpdatedOn());
        Assert.assertEquals(entity.getCreatedOn(), entity.getUpdatedOn());
        Assert.assertEquals("simpleUser", entity.getCreatedBy());
        Assert.assertEquals("simpleUser", entity.getUpdatedBy());
        Assert.assertEquals("InActive", entity.getStatus());
    }
    
    @Test
    public void testUpdateModifyInfoBrokenData2() {
        TestEntity entity = new TestEntity();
        // createdBy is missing
        entity.setCreatedOn(new Date(0));
        entity.setStatus("InActive");
        
        EntityUtils.updateModifyInfo(entity, "simpleUser");
        
        Assert.assertNotNull(entity.getUpdatedOn());
        Assert.assertEquals(entity.getCreatedOn(), entity.getUpdatedOn());
        Assert.assertEquals("simpleUser", entity.getCreatedBy());
        Assert.assertEquals("simpleUser", entity.getUpdatedBy());
        Assert.assertEquals("InActive", entity.getStatus());
    }
    
    @Test
    public void testCopyModifyInfo() {
        TestEntity entity1 = new TestEntity();
        
        TestEntity entity2 = new TestEntity();
        entity2.setCreatedBy("UVW");
        entity2.setCreatedOn(new Date(111111111111l));
        entity2.setUpdatedBy("vvc");
        entity2.setUpdatedOn(new Date(222222222222l));
        entity2.setStatus("aaa");
        EntityUtils.copyModifyInfo(entity1, entity2);
        
        Assert.assertEquals(entity1, entity2);
    }
    
    @Test
    public void testCopyModifyInfoLeftNull() {
        TestEntity entity2 = new TestEntity();
        EntityUtils.copyModifyInfo(null, entity2);
    }
    
    @Test
    public void testCopyModifyInfoRightNull() {
        TestEntity entity2 = new TestEntity();
        EntityUtils.copyModifyInfo(entity2, null);
    }
}

@Getter
@Setter
@EqualsAndHashCode
class TestEntity implements ManageableIfc<Long> {
    private String createdBy;
    private Date createdOn;
    private String updatedBy;
    private Date updatedOn;
    private String status;
    private Long id;

}
