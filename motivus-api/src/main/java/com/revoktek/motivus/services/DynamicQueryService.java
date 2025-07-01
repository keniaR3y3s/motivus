package com.revoktek.motivus.services;

import java.util.List;
import java.util.Map;

public interface DynamicQueryService {

    public List<Map<String, Object>> executeDynamicQuery(String sql, Map<String, Object> params);
}
