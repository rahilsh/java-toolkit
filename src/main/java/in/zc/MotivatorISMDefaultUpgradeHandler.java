// $Id$
package in.zc;

public class MotivatorISMDefaultUpgradeHandler {}

/*package jp.skydesk.sales.util;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp.skydesk.sales.cache.ThreadCache;
import jp.skydesk.sales.job.NotificationJob.NotificationType;
import jp.skydesk.sales.store.SasStore;

import com.adventnet.connection.api.RelationalAPI;
import com.adventnet.ds.query.Column;
import com.adventnet.ds.query.Criteria;
import com.adventnet.ds.query.DataSet;
import com.adventnet.ds.query.DeleteQuery;
import com.adventnet.ds.query.DeleteQueryImpl;
import com.adventnet.ds.query.GroupByClause;
import com.adventnet.ds.query.QueryConstants;
import com.adventnet.ds.query.Range;
import com.adventnet.ds.query.SelectQuery;
import com.adventnet.ds.query.SelectQueryImpl;
import com.adventnet.ds.query.SortColumn;
import com.adventnet.ds.query.Table;
import com.adventnet.mfw.bean.BeanUtil;
import com.adventnet.motivator.MOTIVATORALERTS;
import com.adventnet.motivator.MOTIVATORNOTIFICATIONS;
import com.adventnet.persistence.Persistence;
import com.adventnet.sas.ds.SASThreadLocal;
import com.adventnet.sas.upgrade.isu.UpgradeHandler;

public class MotivatorISMDefaultUpgradeHandler extends UpgradeHandler {
	static boolean initialized = false;
	private Persistence persistence;
	static {

	}

	private static final Logger LOGGER = Logger.getLogger(MotivatorISMDefaultUpgradeHandler.class.getName());

	public void handleTableUpdates(long oldVersion, boolean isReverting) throws Exception {
		//
		//
		//		For DB specific Operations
		//
		//
	}

	public void handleCustomerDataUpdates(long oldVersion, boolean isReverting) throws Exception {
		//
		//
		//		For User specific operations.
		//
		//
		Object sasThreadLocal = SASThreadLocal.getThreadLocal();

		if (isReverting) {
			try {
				LOGGER.log(Level.INFO, " Revert Migration started for org ====================>" + sasThreadLocal);
				//start migration
				// Delete cannot be reverted.
				//end migration
				ThreadCache.clear();
				LOGGER.log(Level.INFO, " Revert Migration Completed for org ====================>" + sasThreadLocal);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, " Revert Migration Exception: ", e);
			}
		} else {
			try {
				persistence = (Persistence) BeanUtil.lookup(SasStore.PERSISTANCE_BEAN_NAME);
				LOGGER.log(Level.INFO, " Migration started for org ====================>" + sasThreadLocal);
				//start migration
				clearDuplicateNotifications();
				clearDuplicateAlerts(NotificationType.TARGET_TIMEUP);
				clearDuplicateAlerts(NotificationType.TARGET_TIME_NEARLY_UP);
				//end migration
				ThreadCache.clear();
				LOGGER.log(Level.INFO, " Migration Completed for org ====================>" + sasThreadLocal);
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, " Exception when migrating org: ", e);
			}
		}
	}

	public void clearDuplicateNotifications() throws Exception {
		boolean dsempty = false;
		do {
			dsempty = true;
			SelectQuery sq = new SelectQueryImpl(new Table(MOTIVATORNOTIFICATIONS.TABLE));
			sq.addSelectColumn(new Column(MOTIVATORNOTIFICATIONS.TABLE, MOTIVATORNOTIFICATIONS.ID).maximum());
			sq.addSelectColumn(new Column(MOTIVATORNOTIFICATIONS.TABLE, MOTIVATORNOTIFICATIONS.TARGETID));
			sq.addSelectColumn(new Column(MOTIVATORNOTIFICATIONS.TABLE, MOTIVATORNOTIFICATIONS.PARTICIPANTID));
			sq.addSelectColumn(new Column(MOTIVATORNOTIFICATIONS.TABLE, MOTIVATORNOTIFICATIONS.PARTICIPANTTYPE));
			sq.addSelectColumn(new Column(MOTIVATORNOTIFICATIONS.TABLE, MOTIVATORNOTIFICATIONS.TYPE));
			Column countColumn = new Column(MOTIVATORNOTIFICATIONS.TABLE, MOTIVATORNOTIFICATIONS.ID).count();
			countColumn.setColumnAlias("c");
			sq.addSelectColumn(countColumn);

			Criteria c = new Criteria(new Column(MOTIVATORNOTIFICATIONS.TABLE, MOTIVATORNOTIFICATIONS.TARGETID), null,
					QueryConstants.NOT_EQUAL);
			sq.addSortColumn(new SortColumn(countColumn, false));
			sq.setCriteria(c);
			sq.setRange(new Range(0, 500));
			List<Column> list = new ArrayList<Column>();
			Column targetIdColumn = new Column(MOTIVATORNOTIFICATIONS.TABLE, MOTIVATORNOTIFICATIONS.TARGETID);
			Column participantIdColumn = new Column(MOTIVATORNOTIFICATIONS.TABLE, MOTIVATORNOTIFICATIONS.PARTICIPANTID);
			Column participantTypeColumn = new Column(MOTIVATORNOTIFICATIONS.TABLE,
					MOTIVATORNOTIFICATIONS.PARTICIPANTTYPE);
			Column typeColumn = new Column(MOTIVATORNOTIFICATIONS.TABLE, MOTIVATORNOTIFICATIONS.TYPE);
			list.add(targetIdColumn);
			list.add(participantIdColumn);
			list.add(participantTypeColumn);
			list.add(typeColumn);
			GroupByClause groupBy = new GroupByClause(list, new Criteria(countColumn, 1, QueryConstants.GREATER_THAN));
			sq.setGroupByClause(groupBy);
			Connection con = null;
			DataSet ds = null;
			try {
				con = RelationalAPI.getInstance().getConnection();
				ds = RelationalAPI.getInstance().executeQuery(sq, con);
				while (ds.next()) {
					dsempty = false;
					Long id = (Long) ds.getValue(1);
					Long targetId = (Long) ds.getValue(2);
					Long participantId = (Long) ds.getValue(3);
					Long participantType = (Long) ds.getValue(4);
					String type = (String) ds.getValue(5);
					int rowCount = (Integer) ds.getValue(6);
					int count = 0;
					do {
						DeleteQuery dq = new DeleteQueryImpl(MOTIVATORNOTIFICATIONS.TABLE);
						Column col = new Column(MOTIVATORNOTIFICATIONS.TABLE, MOTIVATORNOTIFICATIONS.ID);
						Criteria cr = new Criteria(col, id, QueryConstants.NOT_EQUAL);
						cr = cr.and(new Criteria(new Column(MOTIVATORNOTIFICATIONS.TABLE,
								MOTIVATORNOTIFICATIONS.TARGETID), targetId, QueryConstants.EQUAL));
						cr = cr.and(new Criteria(new Column(MOTIVATORNOTIFICATIONS.TABLE,
								MOTIVATORNOTIFICATIONS.PARTICIPANTID), participantId, QueryConstants.EQUAL));
						cr = cr.and(new Criteria(new Column(MOTIVATORNOTIFICATIONS.TABLE,
								MOTIVATORNOTIFICATIONS.PARTICIPANTTYPE), participantType, QueryConstants.EQUAL));
						cr = cr.and(new Criteria(new Column(MOTIVATORNOTIFICATIONS.TABLE, MOTIVATORNOTIFICATIONS.TYPE),
								type, QueryConstants.EQUAL));
						dq.setCriteria(cr);
						dq.setLimit(10000);
						count = persistence.delete(dq);
						LOGGER.log(Level.INFO, "No of Notifications deleted=" + count
								+ "| targetId/participantId/participantType/type/Actual count=" + targetId + "/"
								+ participantId + "/" + participantType + "/" + type + "/" + rowCount);
					} while (count == 10000);
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, " Exception clearDuplicateNotifications: ", e);
				throw e;
			} finally {
				con.close();
				ds.close();
			}
		} while (!dsempty);
	}

	public void clearDuplicateAlerts(NotificationType notifyType) throws Exception {
		boolean dsempty = false;
		do {
			dsempty = true;
			SelectQuery sq = new SelectQueryImpl(new Table(MOTIVATORALERTS.TABLE));
			sq.addSelectColumn(new Column(MOTIVATORALERTS.TABLE, MOTIVATORALERTS.ID).maximum());
			sq.addSelectColumn(new Column(MOTIVATORALERTS.TABLE, MOTIVATORALERTS.TARGETID));
			sq.addSelectColumn(new Column(MOTIVATORALERTS.TABLE, MOTIVATORALERTS.USERID));
			Column countColumn = new Column(MOTIVATORALERTS.TABLE, MOTIVATORALERTS.ID).count();
			countColumn.setColumnAlias("c");
			sq.addSelectColumn(countColumn);
			Criteria c = new Criteria(new Column(MOTIVATORALERTS.TABLE, MOTIVATORALERTS.TYPE), notifyType.toString(),
					QueryConstants.EQUAL);
			sq.addSortColumn(new SortColumn(countColumn, false));
			sq.setRange(new Range(0, 500));
			sq.setCriteria(c);
			List<Column> list = new ArrayList<Column>();
			Column targetIdColumn = new Column(MOTIVATORALERTS.TABLE, MOTIVATORALERTS.TARGETID);
			Column userIdColumn = new Column(MOTIVATORALERTS.TABLE, MOTIVATORALERTS.USERID);

			list.add(targetIdColumn);
			list.add(userIdColumn);
			GroupByClause groupBy = new GroupByClause(list, new Criteria(new Column(MOTIVATORALERTS.TABLE,
					MOTIVATORALERTS.ID).count(), 1, QueryConstants.GREATER_THAN));
			sq.setGroupByClause(groupBy);
			Connection con = null;
			DataSet ds = null;
			try {
				con = RelationalAPI.getInstance().getConnection();
				ds = RelationalAPI.getInstance().executeQuery(sq, con);
				while (ds.next()) {
					dsempty = false;
					Long id = (Long) ds.getValue(1);
					Long targetId = (Long) ds.getValue(2);
					Long userId = (Long) ds.getValue(3);
					int rowCount = (Integer) ds.getValue(4);
					int count = 0;
					do {
						DeleteQuery dq = new DeleteQueryImpl(MOTIVATORALERTS.TABLE);
						Column col = new Column(MOTIVATORALERTS.TABLE, MOTIVATORALERTS.ID);
						Criteria cr = new Criteria(col, id, QueryConstants.NOT_EQUAL);
						cr = cr.and(new Criteria(new Column(MOTIVATORALERTS.TABLE, MOTIVATORALERTS.TARGETID), targetId,
								QueryConstants.EQUAL));
						cr = cr.and(new Criteria(new Column(MOTIVATORALERTS.TABLE, MOTIVATORALERTS.USERID), userId,
								QueryConstants.EQUAL));
						cr = cr.and(new Criteria(new Column(MOTIVATORALERTS.TABLE, MOTIVATORALERTS.TYPE), notifyType
								.toString(), QueryConstants.EQUAL));
						dq.setCriteria(cr);
						dq.setLimit(10000);
						count = persistence.delete(dq);
						LOGGER.log(Level.INFO, "No of Alerts deleted=" + count
								+ "| targetId/userId/type/Actual count=" + targetId + "/" + userId + "/" + notifyType
								+ "/" + rowCount);
					} while (count == 10000);
				}
			} catch (Exception e) {
				LOGGER.log(Level.SEVERE, " Exception when clearDuplicateAlerts: ", e);
				throw e;
			} finally {
				con.close();
				ds.close();
			}
		} while (!dsempty);
	}
}*/
