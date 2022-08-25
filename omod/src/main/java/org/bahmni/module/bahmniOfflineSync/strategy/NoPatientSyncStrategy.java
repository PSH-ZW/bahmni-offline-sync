package org.bahmni.module.bahmniOfflineSync.strategy;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.bahmni.module.bahmniOfflineSync.eventLog.EventLog;
import org.hibernate.SessionFactory;
import org.ict4h.atomfeed.server.domain.EventRecord;
import org.openmrs.api.ConceptService;
import org.openmrs.api.context.Context;
import org.openmrs.module.idgen.service.IdentifierSourceService;

import java.util.*;

public class NoPatientSyncStrategy extends AbstractOfflineSyncStrategy {    private static Logger logger = LogManager.getLogger(NoPatientSyncStrategy.class);

    private ConceptService conceptService;

    private IdentifierSourceService identifierSourceService;

    private final String FORMS = "forms";
    private final String OFFLINE_CONCEPTS = "offline-concepts";
    private final String ADDRESS_HIERARCHY = "addressHierarchy";

    private SessionFactory sessionFactory;

    public NoPatientSyncStrategy() {
        this.conceptService = Context.getConceptService();
        this.identifierSourceService = Context.getService(IdentifierSourceService.class);
        List<SessionFactory> sessionFactories = Context.getRegisteredComponents(SessionFactory.class);
        if(sessionFactories != null && sessionFactories.size() > 0)
            this.sessionFactory = sessionFactories.get(0);
    }

    protected String evaluateFilterForPatient(String uuid) {
        return null;
    }

    public Map<String, List<String>> getFilterForDevice(String providerUuid, String addressUuid, String loginLocationUuid) {
        Map<String, List<String>> categoryFilterMap = new HashMap();

        categoryFilterMap.put(ADDRESS_HIERARCHY, new ArrayList<String>());
        categoryFilterMap.put(OFFLINE_CONCEPTS, new ArrayList<String>());
        categoryFilterMap.put(FORMS, new ArrayList<>());

        return categoryFilterMap;
    }

    @Override
    public List<String> getEventCategoriesList() {
        List<String> eventCategoryList = new ArrayList();

        eventCategoryList.add(ADDRESS_HIERARCHY);
        eventCategoryList.add(OFFLINE_CONCEPTS);
        eventCategoryList.add(FORMS);

        return eventCategoryList;
    }

    @Override
    public List<EventLog> getEventLogsFromEventRecords(List<EventRecord> eventRecords) {
        List<EventLog> eventLogs = new ArrayList<>();
        Set<String> requiredCategories = new HashSet<>();
        requiredCategories.add(FORMS);
        requiredCategories.add(ADDRESS_HIERARCHY);
        requiredCategories.add(OFFLINE_CONCEPTS);

        for (EventRecord er : eventRecords) {
            EventLog eventLog = new EventLog(er.getUuid(), er.getCategory(), er.getTimeStamp(), er.getContents(), null, er.getUuid());
            String category = er.getCategory();
            eventLog.setFilter(null);
            if(requiredCategories.contains(category)) {
                eventLogs.add(eventLog);
            }
        }

        return eventLogs;
    }
}
