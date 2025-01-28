package com.sorryisme.fmarket.config;

import com.sorryisme.fmarket.common.AppConstants;
import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;
import org.springframework.transaction.support.TransactionSynchronizationManager;

public class ReplicationRoutingDataSource extends AbstractRoutingDataSource {

    @Override
    protected Object determineCurrentLookupKey() {
        return TransactionSynchronizationManager.isCurrentTransactionReadOnly() ? AppConstants.DATASOURCE_REPLICA : AppConstants.DATASOURCE_SOURCE;
    }
}
