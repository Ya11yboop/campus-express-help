package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysDictData;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 字典数据 Mapper
 */
public interface SysDictDataMapper {

    @Select("<script>SELECT * FROM sys_dict_data WHERE 1=1 " +
            "<if test='dictLabel != null and dictLabel != \"\"'>AND dict_label LIKE CONCAT('%', #{dictLabel}, '%')</if> " +
            "<if test='dictType != null and dictType != \"\"'>AND dict_type = #{dictType}</if> " +
            "<if test='status != null and status != \"\"'>AND status = #{status}</if> " +
            "ORDER BY dict_sort</script>")
    List<SysDictData> selectDictDataList(SysDictData dictData);

    @Select("SELECT * FROM sys_dict_data WHERE dict_type = #{dictType} AND status = '0' ORDER BY dict_sort")
    List<SysDictData> selectDictDataByType(String dictType);

    @Select("SELECT * FROM sys_dict_data WHERE dict_code = #{dictCode}")
    SysDictData selectDictDataById(Long dictCode);

    @Insert("INSERT INTO sys_dict_data(dict_sort, dict_label, dict_value, dict_type, status, create_time, remark) " +
            "VALUES(#{dictSort}, #{dictLabel}, #{dictValue}, #{dictType}, #{status}, now(), #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "dictCode")
    int insertDictData(SysDictData dictData);

    @Update("UPDATE sys_dict_data SET dict_sort=#{dictSort}, dict_label=#{dictLabel}, dict_value=#{dictValue}, " +
            "dict_type=#{dictType}, status=#{status}, update_time=now(), remark=#{remark} WHERE dict_code=#{dictCode}")
    int updateDictData(SysDictData dictData);

    @Delete("DELETE FROM sys_dict_data WHERE dict_code = #{dictCode}")
    int deleteDictDataById(Long dictCode);
}
