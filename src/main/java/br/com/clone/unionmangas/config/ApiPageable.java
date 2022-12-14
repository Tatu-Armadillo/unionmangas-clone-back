package br.com.clone.unionmangas.config;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ ElementType.METHOD, ElementType.ANNOTATION_TYPE, ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@ApiImplicitParams({
        @ApiImplicitParam(name = "pageNumber", dataType = "int", paramType = "query", defaultValue = "0", value = "Page to search (0..N)"),
        @ApiImplicitParam(name = "pageSize", dataType = "int", paramType = "query", defaultValue = "20", value = "Number of records per page."),
        @ApiImplicitParam(name = "order", allowMultiple = true, dataType = "string", paramType = "query", value = "Orders the query in the format: property(asc|desc). The default ordering is ascending. Can be added multiple times.") })
public @interface ApiPageable { }
