package com.revoktek.motivus.services.impl;

import com.revoktek.motivus.core.exceptions.BusinessException;
import com.revoktek.motivus.core.exceptions.InternalServerException;
import com.revoktek.motivus.core.i18n.MessageProvider;
import com.revoktek.motivus.core.utils.ApplicationUtil;
import com.revoktek.motivus.services.DynamicQueryService;
import jakarta.persistence.EntityManager;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.query.NativeQuery;
import org.hibernate.transform.AliasToEntityMapResultTransformer;
import org.springframework.stereotype.Service;
import org.hibernate.Session;

import java.util.List;
import java.util.Map;

@Slf4j
@Service
@AllArgsConstructor
public class DynamicQueryServiceImpl implements DynamicQueryService {

    private final EntityManager entityManager;
    private final MessageProvider messageProvider;
    private final ApplicationUtil applicationUtil;

    @Override
    public List<Map<String, Object>> executeDynamicQuery(String sql, Map<String, Object> params) {
        try{

            log.info("Datos front findAllWithCount.filterDTO : {}", applicationUtil.toJson(params));

            Session session = entityManager.unwrap(Session.class);
            NativeQuery<?> nativeQuery = session.createNativeQuery(sql);

            if (params != null) {
                params.forEach(nativeQuery::setParameter);
            }

            nativeQuery.setResultTransformer(AliasToEntityMapResultTransformer.INSTANCE);

            return (List<Map<String, Object>>) nativeQuery.list();
        } catch (BusinessException e) {
            log.warn(messageProvider.getBusinessWarningConsole(e.getMessage()));
            throw e;
        } catch (Exception e) {
            log.error(messageProvider.getUnexpectedErrorConsole(this.getClass().getSimpleName(), e.getMessage()), e);
            throw new InternalServerException(e);
        }
    }

}

