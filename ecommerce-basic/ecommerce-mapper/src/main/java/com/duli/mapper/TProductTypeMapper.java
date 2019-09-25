package com.duli.mapper;

import com.duli.entity.TProductType;
import com.dulli.commom.base.IBaseDao;
import java.util.List;

public interface TProductTypeMapper extends IBaseDao<TProductType> {
    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_product_type
     *
     * @mbg.generated Tue Aug 13 16:42:31 CST 2019
     */
    int deleteByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_product_type
     *
     * @mbg.generated Tue Aug 13 16:42:31 CST 2019
     */
    int insert(TProductType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_product_type
     *
     * @mbg.generated Tue Aug 13 16:42:31 CST 2019
     */
    int insertSelective(TProductType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_product_type
     *
     * @mbg.generated Tue Aug 13 16:42:31 CST 2019
     */
    TProductType selectByPrimaryKey(Long id);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_product_type
     *
     * @mbg.generated Tue Aug 13 16:42:31 CST 2019
     */
    int updateByPrimaryKeySelective(TProductType record);

    /**
     * This method was generated by MyBatis Generator.
     * This method corresponds to the database table t_product_type
     *
     * @mbg.generated Tue Aug 13 16:42:31 CST 2019
     */
    int updateByPrimaryKey(TProductType record);

    List<TProductType> list();
}