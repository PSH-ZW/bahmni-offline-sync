# bahmni-offline-sync
Omod required for syncing events to PWA.

## Release 2 Updates:
Added `NoPatientSyncStratergy` which will only sync entries from `event_record` of category `offline-concepts`, `forms`
and `addressHierarchy`. 

This class is selected by setting the value of the global property `bahmniOfflineSync.strategy` in OpenMRS.

## Buliding the omod.
Run `mvn clean install -DskipTests`

The omod should to built to `omod/target/`. This needs to be placed in /opt/openmrs/modules folder and OpenMRS needs to be restarted for loading it.
