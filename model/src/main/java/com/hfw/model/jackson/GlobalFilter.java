package com.hfw.model.jackson;

import com.fasterxml.jackson.annotation.JsonFilter;

@JsonFilter(GlobalFilter.FILTER)
public interface GlobalFilter {
    String FILTER = "GLOBAL_FILTER";
}
