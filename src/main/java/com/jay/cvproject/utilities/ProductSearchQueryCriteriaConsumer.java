package com.jay.cvproject.utilities;

import com.jay.cvproject.dtos.SearchCriteria;
import com.jay.cvproject.models.Product;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.function.Consumer;


@AllArgsConstructor
@Data
public class ProductSearchQueryCriteriaConsumer implements Consumer<SearchCriteria> {
    private Predicate predicate;
    private CriteriaBuilder builder;
    private Root<Product> r;

    @Override
    public void accept(SearchCriteria param) {
        if (param.getOperation().equalsIgnoreCase(">")) {
            predicate = builder.and(predicate, builder
                    .greaterThanOrEqualTo(r.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase("<")) {
            predicate = builder.and(predicate, builder.lessThanOrEqualTo(
                    r.get(param.getKey()), param.getValue().toString()));
        } else if (param.getOperation().equalsIgnoreCase(":")) {
            if (r.get(param.getKey()).getJavaType() == String.class) {
                predicate = builder.and(predicate, builder.like(
                        r.get(param.getKey()), "%" + param.getValue() + "%"));
            } else {
                predicate = builder.and(predicate, builder.equal(
                        r.get(param.getKey()), param.getValue()));
            }
        }


    }
}
