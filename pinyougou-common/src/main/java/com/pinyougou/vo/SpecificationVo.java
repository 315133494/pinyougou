package com.pinyougou.vo;

import com.pinyougou.pojo.Specification;
import com.pinyougou.pojo.SpecificationOption;

import java.io.Serializable;
import java.util.List;

/**
 * 规格组合
 */
public class SpecificationVo implements Serializable {
    private Specification specification;//规格
    private List<SpecificationOption> specificationOptionList;//规格选项

    public Specification getSpecification() {
        return specification;
    }

    public void setSpecification(Specification specification) {
        this.specification = specification;
    }

    public List<SpecificationOption> getSpecificationOptionList() {
        return specificationOptionList;
    }

    public void setSpecificationOptionList(List<SpecificationOption> specificationOptionList) {
        this.specificationOptionList = specificationOptionList;
    }

    @Override
    public String toString() {
        return "SpecificationVo{" +
                "specification=" + specification +
                ", specificationOptionList=" + specificationOptionList +
                '}';
    }
}
