package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysDictType;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 字典类型 Mapper
 */
public interface SysDictTypeMapper {

    @Select("<script>SELECT * FROM sys_dict_type WHERE 1=1 " +
            "<if test='dictName != null and dictName != \"\"'>AND dict_name LIKE CONCAT('%', #{dictName}, '%')</if> " +
            "<if test='dictType != null and dictType != \"\"'>AND dict_type = #{dictType}</if> " +
            "<if test='status != null and status != \"\"'>AND status = #{status}</if> " +
            "ORDER BY dict_id</script>")
    List<SysDictType> selectDictTypeList(SysDictType dictType);

    @Select("SELECT * FROM sys_dict_type WHERE dict_id = #{dictId}")
    SysDictType selectDictTypeById(Long dictId);

    @Insert("INSERT INTO sys_dict_type(dict_name, dict_type, status, create_time, remark) " +
            "VALUES(#{dictName}, #{dictType}, #{status}, now(), #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "dictId")
    int insertDictType(SysDictType dictType);

    @Update("UPDATE sys_dict_type SET dict_name=#{dictName}, dict_type=#{dictType}, status=#{status}, update_time=now(), remark=#{remark} WHERE dict_id=#{dictId}")
    int updateDictType(SysDictType dictType);

    @Delete("DELETE FROM sys_dict_type WHERE dict_id = #{dictId}")
    int deleteDictTypeById(Long dictId);
}
