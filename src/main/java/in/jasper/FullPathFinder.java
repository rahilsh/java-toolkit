package in.jasper;

import java.util.ArrayList;
import java.util.List;

public class FullPathFinder {

  public static void main(String[] args) {
    // TODO Auto-generated method stub
    List<String> paths = getPaths();
    List<AuditLog> reports = getReports();
    List<String> result = new ArrayList<String>();
    for (String path : paths) {
      for (AuditLog log : reports) {
        /*if(path.contains("11_") && log.name.contains("11_")) {
        	System.out.println(log.name);
        	System.out.println(log.name.replace("(", "_").replace(")","_").replace(" ", "_"));
        	String reportName1 = path.split("/")[path.split("/").length - 1];
        	System.out.println(reportName1);
        	System.out.println(reportName1.equals(log.name.replace("(", "_").replace(")","_").replace(" ", "_")));
        }*/
        String reportName = path.split("/")[path.split("/").length - 1];
        if (reportName.equals(log.name.replace("(", "_").replace(")", "_").replace(" ", "_"))
            && !result.contains(path + "____" + log.name + "____" + log.owner)) {
          result.add(path + "____" + log.name + "____" + log.owner);
        }
      }
    }

    for (String response : result) {
      String[] response1 = response.split("__");
      System.out.println(response);
      // System.out.println(response1[0]+""+response1[1]+response1[1]);
    }
  }

  private static List<AuditLog> getReports() {

    List<AuditLog> list = new ArrayList<AuditLog>();

    list.add(new AuditLog("Add_Funds_v3", "anooj@zeta.tech", "2017-11-17 5:35"));
    list.add(new AuditLog("Unclassified_Debits", "anooj@zeta.tech", "2017-12-07 13:01"));
    list.add(new AuditLog("UnclassifiedCredits", "anooj@zeta.tech", "2017-12-07 12:58"));
    list.add(new AuditLog("1.1_Spends_OnUsMerchants", "chirag.ch@zeta.in", "2017-10-16 10:46"));
    list.add(
        new AuditLog("1.2_Spends_SupercardMerchants", "chirag.ch@zeta.in", "2017-10-10 16:45"));
    list.add(
        new AuditLog(
            "11_Payments_to_Merchants_Settlement_via_imps_etc_(zeta and collect call)_ISOParams",
            "chirag.ch@zeta.in",
            "2017-11-27 12:29"));
    list.add(
        new AuditLog(
            "11_Payments_to_Merchants_Settlement_via_imps_etc_(zeta and collect call)_ISOParams",
            "chirag.ch@zeta.in",
            "2017-12-01 0:01"));
    list.add(new AuditLog("2.1_Funding_PaymentGateway", "chirag.ch@zeta.in", "2017-10-10 16:46"));
    list.add(new AuditLog("2.2_Funding_CorpOpsFundLoad", "chirag.ch@zeta.in", "2017-10-10 16:46"));
    list.add(new AuditLog("2.3_Funding_CorpIMPS-IN", "chirag.ch@zeta.in", "2017-10-10 16:46"));
    list.add(
        new AuditLog("2.4_Funding_Cardloads_corpusers", "chirag.ch@zeta.in", "2017-10-10 16:47"));
    list.add(
        new AuditLog(
            "3.1_Clearing_ClearedTransactions_MasterCard",
            "chirag.ch@zeta.in",
            "2017-10-10 16:48"));
    list.add(
        new AuditLog(
            "3.1_Clearing_ClearedTransactions_Rupay", "chirag.ch@zeta.in", "2017-10-16 12:22"));
    list.add(
        new AuditLog(
            "3.2_Clearing_UnsettledTransactions", "chirag.ch@zeta.in", "2017-10-16 12:23"));
    list.add(new AuditLog("4.1_Summary_balance_report", "chirag.ch@zeta.in", "2017-10-16 12:26"));
    list.add(new AuditLog("5.1_Users_BalanceReport", "chirag.ch@zeta.in", "2017-10-20 10:52"));
    list.add(new AuditLog("5.2_Merchant_BalanceReport", "chirag.ch@zeta.in", "2017-10-16 13:44"));
    list.add(new AuditLog("5.3_Corporate_BalanceReport", "chirag.ch@zeta.in", "2017-10-16 13:46"));
    list.add(
        new AuditLog(
            "7_Transfer_from_Voucher_to_CASH_on_Billupload_ISOParams",
            "chirag.ch@zeta.in",
            "2017-11-27 12:30"));
    list.add(
        new AuditLog(
            "7_Transfer_from_Voucher_to_CASH_on_Billupload_ISOParams",
            "chirag.ch@zeta.in",
            "2017-12-01 0:01"));
    list.add(
        new AuditLog(
            "8a_Payments_to_individuals_on_settlement_to_merchants_directly_from_wallet_ISOParams",
            "chirag.ch@zeta.in",
            "2017-11-27 12:30"));
    list.add(
        new AuditLog(
            "8a_Payments_to_individuals_on_settlement_to_merchants_directly_from_wallet_ISOParams",
            "chirag.ch@zeta.in",
            "2017-12-01 0:00"));
    list.add(
        new AuditLog(
            "8b_Payments_to_individuals_settlement_to_merchants_directly_from_wallet_ISOParams",
            "chirag.ch@zeta.in",
            "2017-11-27 12:30"));
    list.add(
        new AuditLog(
            "8b_Payments_to_individuals_settlement_to_merchants_directly_from_wallet_ISOParams",
            "chirag.ch@zeta.in",
            "2017-12-01 0:00"));
    list.add(
        new AuditLog(
            "9_Payment_to_Merchants_through_Individuals_ISOParams",
            "chirag.ch@zeta.in",
            "2017-12-01 0:00"));
    list.add(
        new AuditLog("Activation Report using CorpID", "chirag.ch@zeta.in", "2017-12-08 14:46"));
    list.add(
        new AuditLog(
            "Activation_Report_Using_Domain_Name", "chirag.ch@zeta.in", "2017-12-08 12:30"));
    list.add(new AuditLog("Add_Funds_v3", "chirag.ch@zeta.in", "2017-12-08 0:00"));
    list.add(new AuditLog("Add_Money_IMPS_IN", "chirag.ch@zeta.in", "2017-11-07 13:22"));
    list.add(
        new AuditLog("Add_Money_UPI_NetBanking_Card", "chirag.ch@zeta.in", "2017-11-07 10:33"));
    list.add(
        new AuditLog(
            "AddressResolution_MissedUserEmails_Statement",
            "chirag.ch@zeta.in",
            "2017-10-09 7:35"));
    list.add(new AuditLog("Agent Performance Report", "chirag.ch@zeta.in", "2017-12-08 4:00"));
    list.add(new AuditLog("Average Processing Time", "chirag.ch@zeta.in", "2017-12-08 4:00"));
    list.add(new AuditLog("BalanceSheet", "chirag.ch@zeta.in", "2017-10-10 16:49"));
    list.add(new AuditLog("Biller Report For Oyo rooms", "chirag.ch@zeta.in", "2017-12-08 10:31"));
    list.add(new AuditLog("Biller Report For Practo", "chirag.ch@zeta.in", "2017-12-08 10:31"));
    list.add(new AuditLog("Biller Report For Shop Clues", "chirag.ch@zeta.in", "2017-12-08 10:33"));
    list.add(new AuditLog("BillerAuditReport", "chirag.ch@zeta.in", "2017-12-08 0:00"));
    list.add(new AuditLog("Bills Processed Per Agent", "chirag.ch@zeta.in", "2017-12-08 4:00"));
    list.add(new AuditLog("CardKycData", "chirag.ch@zeta.in", "2017-10-10 16:50"));
    list.add(new AuditLog("Cards Added for a Bin", "chirag.ch@zeta.in", "2017-12-08 0:00"));
    list.add(new AuditLog("Cashcard Spent", "chirag.ch@zeta.in", "2017-11-28 8:08"));
    list.add(
        new AuditLog(
            "Cashless cafe Infinix Session Report - Dinner",
            "chirag.ch@zeta.in",
            "2017-12-04 7:31"));
    list.add(
        new AuditLog(
            "Cashless cafe Infinix Session Report - Lunch",
            "chirag.ch@zeta.in",
            "2017-12-01 3:52"));
    list.add(
        new AuditLog(
            "CashlessCafeteria_Corporate_Report", "chirag.ch@zeta.in", "2017-12-06 18:01"));
    list.add(
        new AuditLog("CashlessCafeteria_Vendor_Report", "chirag.ch@zeta.in", "2017-12-08 1:14"));
    list.add(new AuditLog("CashlessCafetriaReport_token", "chirag.ch@zeta.in", "2017-12-01 11:00"));
    list.add(new AuditLog("Cohort Analysis", "chirag.ch@zeta.in", "2017-10-09 11:32"));
    list.add(new AuditLog("CompaniesAndOrderDetails", "chirag.ch@zeta.in", "2017-12-03 0:00"));
    list.add(new AuditLog("CompanyDetails", "chirag.ch@zeta.in", "2017-12-03 0:00"));
    list.add(new AuditLog("CorpBen_IMV_Payouts", "chirag.ch@zeta.in", "2017-12-01 2:30"));
    list.add(new AuditLog("CorpBen_IMVCorp_Payouts", "chirag.ch@zeta.in", "2017-12-01 2:30"));
    list.add(new AuditLog("CorpBen_IMVSales_Payouts", "chirag.ch@zeta.in", "2017-12-01 2:30"));
    list.add(new AuditLog("CreditsReport", "chirag.ch@zeta.in", "2017-12-01 0:00"));
    list.add(new AuditLog("Critical_settlement_Alert", "chirag.ch@zeta.in", "2017-12-08 1:37"));
    list.add(new AuditLog("DailyUserStatusReport", "chirag.ch@zeta.in", "2017-12-07 14:02"));
    list.add(new AuditLog("Debits", "chirag.ch@zeta.in", "2017-12-01 0:49"));
    list.add(new AuditLog("EndOfDayBalance", "chirag.ch@zeta.in", "2017-10-10 16:50"));
    list.add(new AuditLog("Funding", "chirag.ch@zeta.in", "2017-10-10 16:51"));
    list.add(new AuditLog("IDFC Report", "chirag.ch@zeta.in", "2017-12-08 10:29"));
    list.add(new AuditLog("InstaGift_Debits_Report", "chirag.ch@zeta.in", "2017-11-13 9:04"));
    list.add(new AuditLog("Invoices", "chirag.ch@zeta.in", "2017-10-10 16:36"));
    list.add(new AuditLog("Kotak_Merchant_Settlements", "chirag.ch@zeta.in", "2017-12-08 11:30"));
    list.add(new AuditLog("MasterCardReport", "chirag.ch@zeta.in", "2017-11-09 10:00"));
    list.add(new AuditLog("MAU", "chirag.ch@zeta.in", "2017-11-09 11:58"));
    list.add(
        new AuditLog("Merchant Reconciliation Report", "chirag.ch@zeta.in", "2017-12-06 18:40"));
    list.add(new AuditLog("Merchant Transactions Report", "chirag.ch@zeta.in", "2017-12-08 3:00"));
    list.add(new AuditLog("Merchants_Master_Sheet", "chirag.ch@zeta.in", "2017-12-08 10:32"));
    list.add(new AuditLog("Money Withdrawn from cashcard", "chirag.ch@zeta.in", "2017-11-28 8:09"));
    list.add(new AuditLog("MonthlyPPISummary_Part1", "chirag.ch@zeta.in", "2017-10-10 16:49"));
    list.add(new AuditLog("MonthlyPPISummary_Part2", "chirag.ch@zeta.in", "2017-10-10 16:49"));
    list.add(new AuditLog("New Users Added", "chirag.ch@zeta.in", "2017-12-07 0:25"));
    list.add(new AuditLog("NonZetaPaymentCiscoBreakfast", "chirag.ch@zeta.in", "2017-10-10 14:51"));
    list.add(new AuditLog("NonZetaPaymentCiscoLunch", "chirag.ch@zeta.in", "2017-10-10 14:51"));
    list.add(new AuditLog("NonZetaPaymentCiscoSnacks", "chirag.ch@zeta.in", "2017-10-10 14:52"));
    list.add(new AuditLog("Out_Of_Band_Settlements", "chirag.ch@zeta.in", "2017-12-08 11:30"));
    list.add(new AuditLog("PayoutsSummary", "chirag.ch@zeta.in", "2017-12-08 1:27"));
    list.add(new AuditLog("Pending Bills", "chirag.ch@zeta.in", "2017-12-08 4:00"));
    list.add(
        new AuditLog("PendingAndFailedPayoutsStatement", "chirag.ch@zeta.in", "2017-12-08 1:12"));
    list.add(
        new AuditLog(
            "Physical Mastercard Supercard Users", "chirag.ch@zeta.in", "2017-12-06 14:17"));
    list.add(new AuditLog("PmoDailyPpiMetrics", "chirag.ch@zeta.in", "2017-10-20 13:04"));
    list.add(new AuditLog("Prepaid_Recharge_Report", "chirag.ch@zeta.in", "2017-12-08 10:30"));
    list.add(
        new AuditLog(
            "PrepaidRechargeReport_WithUsersPhoneNumber", "chirag.ch@zeta.in", "2017-12-08 0:00"));
    list.add(new AuditLog("Processed_Bills", "chirag.ch@zeta.in", "2017-12-08 4:00"));
    list.add(
        new AuditLog("Redcarpet_AllTransaction_Report", "chirag.ch@zeta.in", "2017-12-08 2:55"));
    list.add(new AuditLog("Redcarpet_User_Report", "chirag.ch@zeta.in", "2017-12-08 2:34"));
    list.add(
        new AuditLog("ReimbursementMasterCarMaintenance", "chirag.ch@zeta.in", "2017-11-28 12:45"));
    list.add(new AuditLog("RupaySpendsReport", "chirag.ch@zeta.in", "2017-12-08 0:00"));
    list.add(
        new AuditLog("Sales AreaManagers Contribution", "chirag.ch@zeta.in", "2017-12-07 3:00"));
    list.add(new AuditLog("Sales VS settlement", "chirag.ch@zeta.in", "2017-12-08 0:00"));
    list.add(new AuditLog("SalesSettlementReports", "chirag.ch@zeta.in", "2017-12-08 11:29"));
    list.add(new AuditLog("sodexo-logsync", "chirag.ch@zeta.in", "2017-12-08 4:12"));
    list.add(new AuditLog("SodexoReport_2017-10-04", "chirag.ch@zeta.in", "2017-10-20 9:31"));
    list.add(new AuditLog("Spending", "chirag.ch@zeta.in", "2017-10-10 16:52"));
    list.add(
        new AuditLog("SpendsReportUsingCardprogramID", "chirag.ch@zeta.in", "2017-11-17 6:54"));
    list.add(
        new AuditLog("uncurated_but_Transacted_Stores", "chirag.ch@zeta.in", "2017-12-08 0:10"));
    list.add(new AuditLog("Unique_Curation_Till_Date", "chirag.ch@zeta.in", "2017-12-08 0:35"));
    list.add(new AuditLog("Unsettled_Transactions", "chirag.ch@zeta.in", "2017-12-06 10:23"));
    list.add(new AuditLog("User_Matrix", "chirag.ch@zeta.in", "2017-12-08 0:00"));
    list.add(
        new AuditLog(
            "UsersAddingMoneyToCashCardHighLevel", "chirag.ch@zeta.in", "2017-11-27 14:20"));
    list.add(new AuditLog("UserStatusReport", "chirag.ch@zeta.in", "2017-12-08 1:01"));
    list.add(new AuditLog("UserSummary", "chirag.ch@zeta.in", "2017-10-25 14:44"));
    list.add(new AuditLog("Vendor_Settlement_Report", "chirag.ch@zeta.in", "2017-12-01 6:00"));
    list.add(new AuditLog("ZetaPaymentCiscoBreakfast", "chirag.ch@zeta.in", "2017-10-10 14:52"));
    list.add(new AuditLog("ZetaPaymentCiscoLunch", "chirag.ch@zeta.in", "2017-10-10 14:52"));
    list.add(new AuditLog("ZetaPaymentCiscoSnacks", "chirag.ch@zeta.in", "2017-10-10 14:52"));
    list.add(new AuditLog("ZSR", "chirag.ch@zeta.in", "2017-12-08 3:00"));
    list.add(new AuditLog("ZTE", "chirag.ch@zeta.in", "2017-12-08 3:00"));
    list.add(new AuditLog("ZTE_GIFT", "chirag.ch@zeta.in", "2017-12-08 3:00"));
    list.add(new AuditLog("ZTE_GIFT_Since_Inception", "chirag.ch@zeta.in", "2017-11-16 18:13"));
    list.add(new AuditLog("ZUSR", "chirag.ch@zeta.in", "2017-12-08 3:02"));
    list.add(
        new AuditLog(
            "ClosedCardPayoutStatement", "corpbenreportconsumer@zeta.tech", "2017-11-09 11:11"));
    list.add(
        new AuditLog(
            "ClosedCardPayoutStatementTemp",
            "corpbenreportconsumer@zeta.tech",
            "2017-11-09 11:08"));
    list.add(new AuditLog("1_onus_unsettled_kotak(temp)", "datascience-misc1", "2017-11-30 12:48"));
    list.add(new AuditLog("4.1_Summary_balance_report", "datascience-misc1", "2017-12-08 6:44"));
    list.add(
        new AuditLog("AutomationTemplateMonthlyReport", "datascience-misc1", "2017-12-06 15:18"));
    list.add(new AuditLog("idfc_sbr(temp)", "datascience-misc1", "2017-12-08 6:00"));
    list.add(new AuditLog("ZetaBusinessMetrics", "datascience-misc1", "2017-12-08 9:40"));
    list.add(
        new AuditLog("ZetaBusinessMetricsLedgerBiller", "datascience-misc1", "2017-12-08 13:28"));
    list.add(new AuditLog("1_onus_unsettled", "gunjan@zeta.tech", "2017-10-09 11:28"));
    list.add(new AuditLog("1_onus_unsettled(temp)", "gunjan@zeta.tech", "2017-10-09 11:28"));
    list.add(new AuditLog("1.1_Spends_OnUsMerchants", "gunjan@zeta.tech", "2017-12-08 10:29"));
    list.add(new AuditLog("1.2_Spends_SupercardMerchants", "gunjan@zeta.tech", "2017-12-08 10:29"));
    list.add(new AuditLog("2.1_Funding_PaymentGateway", "gunjan@zeta.tech", "2017-12-08 10:29"));
    list.add(new AuditLog("2.2_Funding_CorpOpsFundLoad", "gunjan@zeta.tech", "2017-12-08 10:29"));
    list.add(new AuditLog("2.3_Funding_CorpIMPS-IN", "gunjan@zeta.tech", "2017-12-08 10:29"));
    list.add(
        new AuditLog("2.4_Funding_Cardloads_corpusers", "gunjan@zeta.tech", "2017-10-23 10:06"));
    list.add(
        new AuditLog("2.4_Funding_Cardloads_corpusers", "gunjan@zeta.tech", "2017-10-25 13:56"));
    list.add(
        new AuditLog(
            "3.1_Clearing_ClearedTransactions_Rupay", "gunjan@zeta.tech", "2017-12-08 10:29"));
    list.add(
        new AuditLog("3.2_Clearing_UnsettledTransactions", "gunjan@zeta.tech", "2017-12-08 10:29"));
    list.add(new AuditLog("4.1_Summary_balance_report", "gunjan@zeta.tech", "2017-10-23 10:29"));
    list.add(new AuditLog("5.1_Users_BalanceReport", "gunjan@zeta.tech", "2017-10-25 13:56"));
    list.add(new AuditLog("5.2_Merchant_BalanceReport", "gunjan@zeta.tech", "2017-10-23 10:30"));
    list.add(new AuditLog("5.3_Corporate_BalanceReport", "gunjan@zeta.tech", "2017-10-25 13:56"));
    list.add(new AuditLog("7.1_TransactionsAML_Kotak", "gunjan@zeta.tech", "2017-11-28 14:30"));
    list.add(
        new AuditLog("7.1_TransactionsAML_Kotak_daily", "gunjan@zeta.tech", "2017-11-22 18:13"));
    list.add(
        new AuditLog(
            "8a_Payments_to_individuals_on_settlement_to_merchants_directly_from_wallet_ISOParams",
            "gunjan@zeta.tech",
            "2017-10-20 9:55"));
    list.add(
        new AuditLog(
            "Activation_Report_Using_Domain_Name", "gunjan@zeta.tech", "2017-11-13 13:02"));
    list.add(
        new AuditLog(
            "AddressResolution_MissedUserEmails_Statement",
            "gunjan@zeta.tech",
            "2017-12-08 10:29"));
    list.add(new AuditLog("Attribution", "gunjan@zeta.tech", "2017-12-04 0:30"));
    list.add(new AuditLog("BalanceSheet", "gunjan@zeta.tech", "2017-12-08 2:00"));
    list.add(
        new AuditLog("BusinessMetrics_forGivenPeriod", "gunjan@zeta.tech", "2017-10-20 11:40"));
    list.add(new AuditLog("BusinessMetrics_P1vsP2", "gunjan@zeta.tech", "2017-10-20 11:43"));
    list.add(new AuditLog("CardKycData", "gunjan@zeta.tech", "2017-12-08 1:15"));
    list.add(
        new AuditLog(
            "CardProgramAggregateFinancialsPerUser", "gunjan@zeta.tech", "2017-10-20 10:54"));
    list.add(new AuditLog("cardProgramSpends", "gunjan@zeta.tech", "2017-10-23 13:45"));
    list.add(
        new AuditLog("CardProgramSummaryReportSamsung", "gunjan@zeta.tech", "2017-11-01 9:00"));
    list.add(new AuditLog("CardProgramYearEndReport", "gunjan@zeta.tech", "2017-10-23 12:33"));
    list.add(new AuditLog("Cards Added for a Bin", "gunjan@zeta.tech", "2017-10-25 13:49"));
    list.add(
        new AuditLog("CashlessCafeteria_Corporate_Report", "gunjan@zeta.tech", "2017-11-27 5:20"));
    list.add(
        new AuditLog("CashlessCafeteria_Vendor_Report", "gunjan@zeta.tech", "2017-11-27 5:20"));
    list.add(new AuditLog("CashlessCafetriaReport_token", "gunjan@zeta.tech", "2017-11-27 5:21"));
    list.add(
        new AuditLog(
            "CashlessCafteria_CorporateReport_WithProductParam",
            "gunjan@zeta.tech",
            "2017-10-20 12:14"));
    list.add(new AuditLog("ClosedCardwithCardIDs", "gunjan@zeta.tech", "2017-10-23 12:31"));
    list.add(
        new AuditLog(
            "Corporate - Add Funds detailed report", "gunjan@zeta.tech", "2017-10-20 7:28"));
    list.add(new AuditLog("CountUsers_balmorethan1lac", "gunjan@zeta.tech", "2017-10-25 13:52"));
    list.add(new AuditLog("DepositAndRevokeStatement", "gunjan@zeta.tech", "2017-10-20 11:49"));
    list.add(
        new AuditLog(
            "DepositAndRevokeStatement_AllCardPrograms", "gunjan@zeta.tech", "2017-10-20 11:48"));
    list.add(new AuditLog("DepositsAndRevokesToUsers", "gunjan@zeta.tech", "2017-11-10 12:02"));
    list.add(new AuditLog("DepositSummary", "gunjan@zeta.tech", "2017-12-04 0:31"));
    list.add(new AuditLog("DetailedProgramStatement", "gunjan@zeta.tech", "2017-10-20 11:58"));
    list.add(new AuditLog("DetailedProgramStatement_1", "gunjan@zeta.tech", "2017-11-23 10:21"));
    list.add(
        new AuditLog("Disbursed_Spent_Data_by_businessID", "gunjan@zeta.tech", "2017-11-10 12:05"));
    list.add(new AuditLog("EndOfDayBalance", "gunjan@zeta.tech", "2017-12-08 1:30"));
    list.add(new AuditLog("FinancialMetrics_Current", "gunjan@zeta.tech", "2017-10-20 11:36"));
    list.add(new AuditLog("Funding", "gunjan@zeta.tech", "2017-12-08 1:45"));
    list.add(new AuditLog("FundingAccountActivitySummary", "gunjan@zeta.tech", "2017-11-23 8:36"));
    list.add(new AuditLog("IDFC Report", "gunjan@zeta.tech", "2017-11-10 12:00"));
    list.add(new AuditLog("IfiDailyAggregates", "gunjan@zeta.tech", "2017-12-08 10:30"));
    list.add(new AuditLog("Invoices", "gunjan@zeta.tech", "2017-12-01 6:16"));
    list.add(
        new AuditLog("Kotak_PPI_Metrics_Table2_P2M_Split", "gunjan@zeta.tech", "2017-12-07 9:43"));
    list.add(new AuditLog("KYC_Tools_User_Data_Health", "gunjan@zeta.tech", "2017-12-08 2:25"));
    list.add(new AuditLog("KycUsersCount", "gunjan@zeta.tech", "2017-12-01 13:15"));
    list.add(new AuditLog("MasterCardReport", "gunjan@zeta.tech", "2017-11-23 10:18"));
    list.add(
        new AuditLog(
            "MasterkeyReport_5Columns (Ledger DB)", "gunjan@zeta.tech", "2017-11-28 12:38"));
    list.add(
        new AuditLog(
            "MasterkeyReport_5Columns (Reporting DWH DB)", "gunjan@zeta.tech", "2017-11-29 5:56"));
    list.add(
        new AuditLog(
            "MerchantActivityMetrics_forGivenPeriod", "gunjan@zeta.tech", "2017-10-20 11:59"));
    list.add(
        new AuditLog(
            "MerchantActivityMetrics_forGivenPeriod", "gunjan@zeta.tech", "2017-11-23 10:02"));
    list.add(new AuditLog("MonthlyPPISummary_Part1", "gunjan@zeta.tech", "2017-12-01 0:30"));
    list.add(new AuditLog("MonthlyPPISummary_Part2", "gunjan@zeta.tech", "2017-12-01 0:49"));
    list.add(
        new AuditLog(
            "MonthlyPPISummary_WithKycSplit_Part1", "gunjan@zeta.tech", "2017-12-01 12:00"));
    list.add(
        new AuditLog(
            "MonthlyPPISummary_WithKycSplit_Part2", "gunjan@zeta.tech", "2017-12-01 12:10"));
    list.add(new AuditLog("NewUsersAdded", "gunjan@zeta.tech", "2017-11-10 12:01"));
    list.add(
        new AuditLog("PayoutsReportUsingCardprogramIDs", "gunjan@zeta.tech", "2017-11-01 9:01"));
    list.add(new AuditLog("PayoutsSummary", "gunjan@zeta.tech", "2017-12-08 1:25"));
    list.add(
        new AuditLog("PendingAndFailedPayoutsStatement", "gunjan@zeta.tech", "2017-12-08 1:30"));
    list.add(new AuditLog("PmoDailyPpiMetrics", "gunjan@zeta.tech", "2017-12-08 1:30"));
    list.add(new AuditLog("PotentialKycIssueUsers", "gunjan@zeta.tech", "2017-12-08 2:30"));
    list.add(new AuditLog("Prepaid_Recharge_Report", "gunjan@zeta.tech", "2017-10-25 17:47"));
    list.add(new AuditLog("RBICyberSecQuarterlyReturn", "gunjan@zeta.tech", "2017-10-25 13:55"));
    list.add(new AuditLog("RBL_PPI_Metrics_Loads2", "gunjan@zeta.tech", "2017-12-08 3:54"));
    list.add(
        new AuditLog(
            "RBL_PPI_Metrics_OffUsMerchantLiability", "gunjan@zeta.tech", "2017-12-07 7:15"));
    list.add(
        new AuditLog(
            "RBL_PPI_Metrics_OnUsMerchantLiability", "gunjan@zeta.tech", "2017-12-07 6:52"));
    list.add(
        new AuditLog("RBL_PPI_Metrics_Table2_P2M_Split", "gunjan@zeta.tech", "2017-12-07 10:15"));
    list.add(new AuditLog("RBL_PPIMetrics", "gunjan@zeta.tech", "2017-12-07 19:43"));
    list.add(new AuditLog("RBL_PPIMetrics_Nov17", "gunjan@zeta.tech", "2017-12-07 19:43"));
    list.add(
        new AuditLog("Redcarpet_AllTransaction_Report", "gunjan@zeta.tech", "2017-11-23 10:04"));
    list.add(new AuditLog("Redcarpet_User_Report", "gunjan@zeta.tech", "2017-11-23 10:05"));
    list.add(new AuditLog("ReimbursementStatement", "gunjan@zeta.tech", "2017-10-20 11:59"));
    list.add(new AuditLog("ReimbursementStatement_1", "gunjan@zeta.tech", "2017-10-20 11:53"));
    list.add(new AuditLog("RupaySpendsReport", "gunjan@zeta.tech", "2017-10-20 9:17"));
    list.add(new AuditLog("Spending", "gunjan@zeta.tech", "2017-12-08 1:55"));
    list.add(new AuditLog("SpendsHistogram", "gunjan@zeta.tech", "2017-10-20 11:59"));
    list.add(
        new AuditLog("SpendsReportwithTransactionType", "gunjan@zeta.tech", "2017-10-24 16:25"));
    list.add(new AuditLog("SpendStatement", "gunjan@zeta.tech", "2017-10-20 12:00"));
    list.add(new AuditLog("SpendStatement_1", "gunjan@zeta.tech", "2017-10-20 11:53"));
    list.add(new AuditLog("TestBucardo1", "gunjan@zeta.tech", "2017-10-15 20:03"));
    list.add(new AuditLog("TransactionStatementForMids", "gunjan@zeta.tech", "2017-11-10 11:49"));
    list.add(new AuditLog("TransactionStatementForTids", "gunjan@zeta.tech", "2017-11-10 11:49"));
    list.add(new AuditLog("Unclassified_Debits", "gunjan@zeta.tech", "2017-11-09 13:59"));
    list.add(new AuditLog("UnclassifiedCredits", "gunjan@zeta.tech", "2017-11-09 14:03"));
    list.add(
        new AuditLog("User_Entity_Getting_Stale_Alert", "gunjan@zeta.tech", "2017-12-08 14:30"));
    list.add(new AuditLog("User_Matrix", "gunjan@zeta.tech", "2017-11-23 10:07"));
    list.add(
        new AuditLog(
            "Users Spending Atleast Once from Cardprogram", "gunjan@zeta.tech", "2017-10-23 7:44"));
    list.add(new AuditLog("UsersPerIIN", "gunjan@zeta.tech", "2017-12-05 13:04"));
    list.add(new AuditLog("UserSummary", "gunjan@zeta.tech", "2017-12-08 2:05"));
    list.add(new AuditLog("UserTransactionHistory", "gunjan@zeta.tech", "2017-11-23 10:10"));
    list.add(new AuditLog("ZSR", "gunjan@zeta.tech", "2017-11-14 10:52"));
    list.add(new AuditLog("ZSR_2017-10-04", "gunjan@zeta.tech", "2017-10-20 9:29"));
    list.add(new AuditLog("ZTE", "gunjan@zeta.tech", "2017-11-14 10:53"));
    list.add(new AuditLog("ZTE_GIFT", "gunjan@zeta.tech", "2017-11-14 10:55"));
    list.add(new AuditLog("ZUSR", "gunjan@zeta.tech", "2017-10-25 13:30"));
    list.add(new AuditLog("ZUSR_2017-10-04", "gunjan@zeta.tech", "2017-10-20 9:29"));
    list.add(
        new AuditLog(
            "CashlessCafeteria_Corporate_Report", "indumathiv@zeta.tech", "2017-12-06 14:23"));
    list.add(
        new AuditLog(
            "CashlessCafeteria_Vendor_Report", "indumathiv@zeta.tech", "2017-11-14 11:46"));
    list.add(
        new AuditLog("CashlessCafetriaReport_token", "indumathiv@zeta.tech", "2017-11-14 12:12"));
    list.add(
        new AuditLog("EliorZetaPaymentCiscoBreakfast", "indumathiv@zeta.tech", "2017-11-21 11:06"));
    list.add(
        new AuditLog("EliorZetaPaymentCiscoSnacks", "indumathiv@zeta.tech", "2017-11-21 11:05"));
    list.add(new AuditLog("NonZetaPaymentCiscoLunch", "indumathiv@zeta.tech", "2017-10-25 9:36"));
    list.add(new AuditLog("ZetaPaymentCiscoLunch", "indumathiv@zeta.tech", "2017-10-25 9:35"));
    list.add(new AuditLog("1_onus_unsettled(temp)", "jasperadmin", "2017-10-25 18:43"));
    list.add(
        new AuditLog(
            "8d_Funds_into_CashCard_via_Gateway_ISOParams", "jasperadmin", "2017-12-01 5:33"));
    list.add(
        new AuditLog(
            "9_Payment_to_Merchants_through_Individuals_ISOParams",
            "jasperadmin",
            "2017-12-01 4:00"));
    list.add(new AuditLog("AccenturePayoutTransactionNull", "jasperadmin", "2017-11-03 11:27"));
    list.add(
        new AuditLog("Activation_Report_Using_Domain_Name", "jasperadmin", "2017-10-11 19:05"));
    list.add(new AuditLog("BalanceSheet", "jasperadmin", "2017-10-25 13:58"));
    list.add(new AuditLog("CardKycData", "jasperadmin", "2017-10-25 13:58"));
    list.add(new AuditLog("CardProgramSummaryReportSamsung", "jasperadmin", "2017-11-01 8:53"));
    list.add(new AuditLog("Cards Added for a Bin", "jasperadmin", "2017-10-25 13:51"));
    list.add(new AuditLog("CashlessCafeteria_Vendor_Report", "jasperadmin", "2017-10-26 10:44"));
    list.add(new AuditLog("CashlessCafetriaReport_token", "jasperadmin", "2017-10-26 10:43"));
    list.add(new AuditLog("ClosedCardledgerSubquery", "jasperadmin", "2017-11-24 23:05"));
    list.add(new AuditLog("ClosedCardPayoutStatement", "jasperadmin", "2017-11-09 11:07"));
    list.add(new AuditLog("ClosedCardPayoutStatementTemp", "jasperadmin", "2017-11-09 11:09"));
    list.add(new AuditLog("CorporateCardTypeTransactionsData", "jasperadmin", "2017-11-19 11:08"));
    list.add(new AuditLog("CountUsers_balmorethan1lac", "jasperadmin", "2017-10-25 13:52"));
    list.add(new AuditLog("Debits", "jasperadmin", "2017-11-08 14:49"));
    list.add(new AuditLog("InstaGift_Debits_Report", "jasperadmin", "2017-11-13 9:04"));
    list.add(new AuditLog("InstaGift_Invoices_Report", "jasperadmin", "2017-11-13 9:07"));
    list.add(new AuditLog("KycUsersCount", "jasperadmin", "2017-10-25 13:54"));
    list.add(new AuditLog("MasterCardReport", "jasperadmin", "2017-10-09 13:55"));
    list.add(
        new AuditLog("New Users Current Month - Daily Report", "jasperadmin", "2017-10-30 9:07"));
    list.add(new AuditLog("NonZetaPaymentCiscoBreakfast", "jasperadmin", "2017-10-09 6:18"));
    list.add(new AuditLog("NonZetaPaymentCiscoLunch", "jasperadmin", "2017-10-09 6:19"));
    list.add(new AuditLog("NonZetaPaymentCiscoSnacks", "jasperadmin", "2017-10-09 6:19"));
    list.add(new AuditLog("PayoutPerCardProgramPerMonthYear", "jasperadmin", "2017-11-29 14:28"));
    list.add(new AuditLog("PmoDailyPpiMetrics", "jasperadmin", "2017-10-25 14:12"));
    list.add(new AuditLog("QuickshopDB test report", "jasperadmin", "2017-10-13 14:02"));
    list.add(new AuditLog("ReimbursementMasterLTA", "jasperadmin", "2017-11-15 15:01"));
    list.add(new AuditLog("ReimbursementMasterReportNew", "jasperadmin", "2017-11-27 14:27"));
    list.add(new AuditLog("SpendsReportUsingCardprogramID", "jasperadmin", "2017-12-06 6:11"));
    list.add(new AuditLog("SpendsReportwithTransactionType", "jasperadmin", "2017-10-25 5:07"));
    list.add(
        new AuditLog(
            "Users Spending Atleast Once from Cardprogram", "jasperadmin", "2017-10-25 5:07"));
    list.add(new AuditLog("UserSummary", "jasperadmin", "2017-10-25 13:59"));
    list.add(new AuditLog("Vendor_Settlement_Report", "jasperadmin", "2017-10-26 10:43"));
    list.add(new AuditLog("ZetaPaymentCiscoBreakfast", "jasperadmin", "2017-10-09 6:22"));
    list.add(new AuditLog("ZetaPaymentCiscoLunch", "jasperadmin", "2017-10-09 6:20"));
    list.add(new AuditLog("ZetaPaymentCiscoSnacks", "jasperadmin", "2017-10-09 6:20"));
    list.add(new AuditLog("ZSR", "jasperadmin", "2017-10-27 13:55"));
    list.add(new AuditLog("ZTE", "jasperadmin", "2017-10-27 13:55"));
    list.add(new AuditLog("ZUSR", "jasperadmin", "2017-10-27 13:55"));
    list.add(new AuditLog("cardProgramSpends", "jindal@zeta.tech", "2017-11-16 7:23"));
    list.add(new AuditLog("cardProgramSpends", "jindal@zeta.tech", "2017-12-08 11:15"));
    list.add(
        new AuditLog("CardProgramSummaryReportSamsung", "jindal@zeta.tech", "2017-11-09 17:06"));
    list.add(
        new AuditLog("CardProgramSummaryReportSamsung", "jindal@zeta.tech", "2017-12-05 6:40"));
    list.add(new AuditLog("CardProgramYearEndReport", "jindal@zeta.tech", "2017-11-15 9:54"));
    list.add(new AuditLog("CardProgramYearEndReport", "jindal@zeta.tech", "2017-11-16 10:21"));
    list.add(new AuditLog("CardProgramYearEndReport", "jindal@zeta.tech", "2017-12-04 5:30"));
    list.add(new AuditLog("CardProgramYearEndReport", "jindal@zeta.tech", "2017-12-07 12:29"));
    list.add(new AuditLog("ClosedCardPayoutStatement", "jindal@zeta.tech", "2017-11-16 16:03"));
    list.add(new AuditLog("ClosedCardPayoutStatement", "jindal@zeta.tech", "2017-12-07 12:33"));
    list.add(new AuditLog("ClosedCardPayoutStatementTemp", "jindal@zeta.tech", "2017-10-25 9:52"));
    list.add(new AuditLog("ClosedCardStatusByOrderID", "jindal@zeta.tech", "2017-11-17 6:13"));
    list.add(new AuditLog("ClosedCardStatusByOrderID", "jindal@zeta.tech", "2017-12-07 12:25"));
    list.add(new AuditLog("ClosedCardwithCardIDs", "jindal@zeta.tech", "2017-10-27 8:25"));
    list.add(new AuditLog("ClosedCardwithCardIDs", "jindal@zeta.tech", "2017-11-17 9:06"));
    list.add(
        new AuditLog(
            "ClosedCardwithCardIDsforAllowanceProducts", "jindal@zeta.tech", "2017-11-17 6:25"));
    list.add(
        new AuditLog(
            "ClosedCardwithCardIDsforAllowanceProducts", "jindal@zeta.tech", "2017-12-07 12:27"));
    list.add(new AuditLog("EmployeeSpends", "jindal@zeta.tech", "2017-11-17 8:37"));
    list.add(new AuditLog("FundingAccountDeposits", "jindal@zeta.tech", "2017-11-17 5:52"));
    list.add(new AuditLog("FundingAccountDeposits", "jindal@zeta.tech", "2017-12-08 6:55"));
    list.add(new AuditLog("FundingAccountFundMovement", "jindal@zeta.tech", "2017-11-16 14:40"));
    list.add(new AuditLog("FundingAccountFundMovement", "jindal@zeta.tech", "2017-12-08 5:44"));
    list.add(new AuditLog("FundingAccountTransferDetail", "jindal@zeta.tech", "2017-11-17 5:53"));
    list.add(new AuditLog("FundingAccountTransferDetail", "jindal@zeta.tech", "2017-12-08 5:50"));
    list.add(new AuditLog("Payouts", "jindal@zeta.tech", "2017-11-15 12:32"));
    list.add(new AuditLog("Payouts", "jindal@zeta.tech", "2017-12-06 11:30"));
    list.add(
        new AuditLog("PayoutsReportUsingCardprogramIDs", "jindal@zeta.tech", "2017-11-15 9:33"));
    list.add(
        new AuditLog("PayoutsReportUsingCardprogramIDs", "jindal@zeta.tech", "2017-12-01 10:05"));
    list.add(
        new AuditLog("ReimbursementMasterCarMaintenance", "jindal@zeta.tech", "2017-11-15 15:20"));
    list.add(
        new AuditLog("ReimbursementMasterCarMaintenance", "jindal@zeta.tech", "2017-11-30 9:16"));
    list.add(new AuditLog("ReimbursementMasterLTA", "jindal@zeta.tech", "2017-11-16 9:02"));
    list.add(new AuditLog("ReimbursementMasterLTA", "jindal@zeta.tech", "2017-11-30 7:17"));
    list.add(new AuditLog("ReimbursementMasterReport", "jindal@zeta.tech", "2017-11-17 10:38"));
    list.add(new AuditLog("ReimbursementMasterReport", "jindal@zeta.tech", "2017-12-08 10:27"));
    list.add(new AuditLog("ReimbursementMasterReportNew", "jindal@zeta.tech", "2017-11-30 7:09"));
    list.add(new AuditLog("SpendsReportUsingCardprogramID", "jindal@zeta.tech", "2017-11-15 9:28"));
    list.add(
        new AuditLog("SpendsReportUsingCardprogramID", "jindal@zeta.tech", "2017-12-01 10:03"));
    list.add(
        new AuditLog(
            "11_Payments_to_Merchants_Settlement_via_imps_etc_(zeta and collect call)_ISOParams",
            "madhav.g@zeta.tech",
            "2017-10-11 14:02"));
    list.add(
        new AuditLog(
            "11_Payments_to_Merchants_Settlement_via_imps_etc_(zeta and collect call)_ISOParams",
            "madhav.g@zeta.tech",
            "2017-12-01 5:35"));
    list.add(new AuditLog("4.1_Summary_balance_report", "madhav.g@zeta.tech", "2017-12-05 7:05"));
    list.add(
        new AuditLog(
            "7_Transfer_from_Voucher_to_CASH_on_Billupload_ISOParams",
            "madhav.g@zeta.tech",
            "2017-10-09 7:11"));
    list.add(
        new AuditLog(
            "7_Transfer_from_Voucher_to_CASH_on_Billupload_ISOParams",
            "madhav.g@zeta.tech",
            "2017-12-01 4:00"));
    list.add(
        new AuditLog(
            "8a_Payments_to_individuals_on_settlement_to_merchants_directly_from_wallet_ISOParams",
            "madhav.g@zeta.tech",
            "2017-12-01 3:00"));
    list.add(
        new AuditLog(
            "8b_Payments_to_individuals_settlement_to_merchants_directly_from_wallet_ISOParams",
            "madhav.g@zeta.tech",
            "2017-11-08 14:31"));
    list.add(
        new AuditLog(
            "8b_Payments_to_individuals_settlement_to_merchants_directly_from_wallet_ISOParams",
            "madhav.g@zeta.tech",
            "2017-12-01 3:00"));
    list.add(new AuditLog("8c_Imps_NEFT_in_CashCard", "madhav.g@zeta.tech", "2017-12-01 5:35"));
    list.add(
        new AuditLog(
            "8d_Funds_into_CashCard_via_Gateway_ISOParams",
            "madhav.g@zeta.tech",
            "2017-11-13 10:52"));
    list.add(
        new AuditLog(
            "9_Payment_to_Merchants_through_Individuals_ISOParams",
            "madhav.g@zeta.tech",
            "2017-11-13 9:28"));
    list.add(
        new AuditLog(
            "9_Payment_to_Merchants_through_Individuals_ISOParams",
            "madhav.g@zeta.tech",
            "2017-11-13 15:36"));
    list.add(
        new AuditLog("AccentureMerchantTransactionType", "madhav.g@zeta.tech", "2017-11-03 16:00"));
    list.add(
        new AuditLog("AccenturePayoutTransactionNull", "madhav.g@zeta.tech", "2017-11-03 15:49"));
    list.add(
        new AuditLog("Activation Report using CorpID", "madhav.g@zeta.tech", "2017-10-27 14:07"));
    list.add(
        new AuditLog(
            "Activation_Report_Using_Domain_Name", "madhav.g@zeta.tech", "2017-12-08 1:30"));
    list.add(new AuditLog("Add_Funds_v3", "madhav.g@zeta.tech", "2017-12-06 8:09"));
    list.add(new AuditLog("AmazonPayoutStatement", "madhav.g@zeta.tech", "2017-12-07 3:00"));
    list.add(new AuditLog("AttributionReport", "madhav.g@zeta.tech", "2017-12-01 6:06"));
    list.add(
        new AuditLog("AutomationTemplateMonthlyReport", "madhav.g@zeta.tech", "2017-12-06 15:18"));
    list.add(new AuditLog("BalanceSheet", "madhav.g@zeta.tech", "2017-10-16 14:01"));
    list.add(new AuditLog("BillerAuditReport", "madhav.g@zeta.tech", "2017-11-21 10:11"));
    list.add(new AuditLog("CardKycData", "madhav.g@zeta.tech", "2017-10-16 14:03"));
    list.add(new AuditLog("CardprogramReportSamsung", "madhav.g@zeta.tech", "2017-10-26 14:23"));
    list.add(new AuditLog("cardProgramSpends", "madhav.g@zeta.tech", "2017-11-13 7:47"));
    list.add(
        new AuditLog("CardProgramSummaryReportSamsung", "madhav.g@zeta.tech", "2017-11-24 8:57"));
    list.add(
        new AuditLog("CardProgramSummaryReportSamsung", "madhav.g@zeta.tech", "2017-11-24 9:05"));
    list.add(new AuditLog("CardProgramYearEndReport", "madhav.g@zeta.tech", "2017-11-17 9:23"));
    list.add(
        new AuditLog(
            "CashlessCafeteria_Corporate_Report", "madhav.g@zeta.tech", "2017-10-26 11:28"));
    list.add(
        new AuditLog("CashlessCafeteria_Vendor_Report", "madhav.g@zeta.tech", "2017-10-26 11:20"));
    list.add(new AuditLog("ClosedCardledgerSubquery", "madhav.g@zeta.tech", "2017-11-24 12:48"));
    list.add(new AuditLog("ClosedCardPayoutStatement", "madhav.g@zeta.tech", "2017-11-17 9:13"));
    list.add(new AuditLog("ClosedCardStatusByOrderID", "madhav.g@zeta.tech", "2017-11-17 9:12"));
    list.add(new AuditLog("ClosedCardwithCardIDs", "madhav.g@zeta.tech", "2017-11-17 10:12"));
    list.add(new AuditLog("ClosedCardwithCardIDs", "madhav.g@zeta.tech", "2017-11-17 10:57"));
    list.add(
        new AuditLog(
            "ClosedCardwithCardIDsforAllowanceProducts", "madhav.g@zeta.tech", "2017-11-17 10:04"));
    list.add(
        new AuditLog(
            "ClosedCardwithCardIDsforAllowanceProducts", "madhav.g@zeta.tech", "2017-11-23 12:07"));
    list.add(new AuditLog("CorpBen_IMV_Payouts", "madhav.g@zeta.tech", "2017-11-06 12:34"));
    list.add(
        new AuditLog("Corporate Order Report - Monthly", "madhav.g@zeta.tech", "2017-12-01 0:01"));
    list.add(
        new AuditLog(
            "Corporate Sign Up Request - Daily", "madhav.g@zeta.tech", "2017-12-08 14:00"));
    list.add(
        new AuditLog(
            "CorporateCardTypeTransactionsData", "madhav.g@zeta.tech", "2017-11-19 11:05"));
    list.add(new AuditLog("CreditsReport", "madhav.g@zeta.tech", "2017-11-22 15:40"));
    list.add(new AuditLog("Daily Order Summary Report", "madhav.g@zeta.tech", "2017-12-08 14:00"));
    list.add(new AuditLog("DailyPendingBillsSummary", "madhav.g@zeta.tech", "2017-12-08 10:29"));
    list.add(new AuditLog("Debits", "madhav.g@zeta.tech", "2017-11-23 8:50"));
    list.add(
        new AuditLog(
            "EliorNonZetaPaymentCiscoBreakfast", "madhav.g@zeta.tech", "2017-12-08 10:30"));
    list.add(
        new AuditLog("EliorNonZetaPaymentCiscoDinner", "madhav.g@zeta.tech", "2017-12-08 1:30"));
    list.add(
        new AuditLog("EliorNonZetaPaymentCiscoLunch", "madhav.g@zeta.tech", "2017-12-08 10:33"));
    list.add(
        new AuditLog("EliorNonZetaPaymentCiscoSnacks", "madhav.g@zeta.tech", "2017-12-07 15:30"));
    list.add(
        new AuditLog("EliorZetaPaymentCiscoBreakfast", "madhav.g@zeta.tech", "2017-12-08 10:30"));
    list.add(new AuditLog("EliorZetaPaymentCiscoDinner", "madhav.g@zeta.tech", "2017-12-08 1:30"));
    list.add(new AuditLog("EliorZetaPaymentCiscoLunch", "madhav.g@zeta.tech", "2017-12-08 10:33"));
    list.add(new AuditLog("EliorZetaPaymentCiscoSnacks", "madhav.g@zeta.tech", "2017-12-07 15:30"));
    list.add(new AuditLog("EndOfDayBalance", "madhav.g@zeta.tech", "2017-10-16 14:07"));
    list.add(
        new AuditLog(
            "Fund Request Summary- Daily Report", "madhav.g@zeta.tech", "2017-12-07 16:00"));
    list.add(new AuditLog("Funding", "madhav.g@zeta.tech", "2017-10-16 14:10"));
    list.add(new AuditLog("FundingAccountDeposits", "madhav.g@zeta.tech", "2017-11-21 12:31"));
    list.add(new AuditLog("FundingAccountFundMovement", "madhav.g@zeta.tech", "2017-11-29 10:01"));
    list.add(new AuditLog("FundingAccountTransferDetail", "madhav.g@zeta.tech", "2017-11-02 8:17"));
    list.add(
        new AuditLog(
            "GlobalItemLevelCiscoMerchantEvening", "madhav.g@zeta.tech", "2017-12-07 15:00"));
    list.add(
        new AuditLog(
            "GlobalItemLevelCiscoMerchantMorning", "madhav.g@zeta.tech", "2017-12-08 0:30"));
    list.add(
        new AuditLog("GlobalNonZetaPaymentCiscoEvening", "madhav.g@zeta.tech", "2017-12-07 15:00"));
    list.add(
        new AuditLog("GlobalNonZetaPaymentCiscoMorning", "madhav.g@zeta.tech", "2017-12-08 0:30"));
    list.add(
        new AuditLog("GlobalZetaPaymentCiscoEvening", "madhav.g@zeta.tech", "2017-12-07 15:00"));
    list.add(
        new AuditLog("GlobalZetaPaymentCiscoMorning", "madhav.g@zeta.tech", "2017-12-08 0:30"));
    list.add(new AuditLog("InstaGift_Debits_Report", "madhav.g@zeta.tech", "2017-12-01 3:00"));
    list.add(new AuditLog("InstaGift_Invoices_Report", "madhav.g@zeta.tech", "2017-12-01 5:00"));
    list.add(
        new AuditLog("InterchangeEarnedAfterRefunds", "madhav.g@zeta.tech", "2017-11-06 11:57"));
    list.add(
        new AuditLog(
            "InterchangeEarnedPerProductTypeHighLevel", "madhav.g@zeta.tech", "2017-11-01 20:21"));
    list.add(new AuditLog("Invoices", "madhav.g@zeta.tech", "2017-11-22 15:45"));
    list.add(
        new AuditLog("ItemLevelCiscoMerchantMidnight", "madhav.g@zeta.tech", "2017-11-11 12:33"));
    list.add(
        new AuditLog(
            "ItemLevelReportCiscoMerchantEvening", "madhav.g@zeta.tech", "2017-11-11 12:33"));
    list.add(
        new AuditLog(
            "Meal Voucher Spend Report Jewelex", "madhav.g@zeta.tech", "2017-12-05 14:01"));
    list.add(
        new AuditLog(
            "New Users Current Month - Daily Report", "madhav.g@zeta.tech", "2017-10-30 8:15"));
    list.add(new AuditLog("NewUsersAdded", "madhav.g@zeta.tech", "2017-10-12 13:25"));
    list.add(new AuditLog("NewUsersAddedMonthlyReport", "madhav.g@zeta.tech", "2017-10-09 14:27"));
    list.add(
        new AuditLog(
            "NewUsersAddedMonthlyReport - NonSodexo", "madhav.g@zeta.tech", "2017-12-01 4:40"));
    list.add(
        new AuditLog(
            "NewUsersAddedMonthlyReport - Sodexo", "madhav.g@zeta.tech", "2017-12-01 4:40"));
    list.add(
        new AuditLog(
            "NewUsersMonthtillDate_Daily Report", "madhav.g@zeta.tech", "2017-10-12 13:25"));
    list.add(
        new AuditLog(
            "NewUsersMonthtillDate_Daily Report - NonSodexo",
            "madhav.g@zeta.tech",
            "2017-12-08 3:30"));
    list.add(
        new AuditLog(
            "NewUsersMonthtillDate_Daily Report - Sodexo",
            "madhav.g@zeta.tech",
            "2017-12-08 3:30"));
    list.add(new AuditLog("NonZetaMonthlyStatement", "madhav.g@zeta.tech", "2017-12-06 7:11"));
    list.add(new AuditLog("NonZetaMonthlyStatement", "madhav.g@zeta.tech", "2017-12-06 7:18"));
    list.add(
        new AuditLog("NonZetaPaymentCiscoBreakfast", "madhav.g@zeta.tech", "2017-11-11 12:34"));
    list.add(new AuditLog("NonZetaPaymentCiscoDinner", "madhav.g@zeta.tech", "2017-11-11 12:34"));
    list.add(new AuditLog("NonZetaPaymentCiscoLunch", "madhav.g@zeta.tech", "2017-11-11 12:34"));
    list.add(new AuditLog("NonZetaPaymentCiscoSnacks", "madhav.g@zeta.tech", "2017-11-11 12:35"));
    list.add(
        new AuditLog(
            "Orders Completed Last Month - Daily Report",
            "madhav.g@zeta.tech",
            "2017-12-07 16:00"));
    list.add(
        new AuditLog("PayoutPerCardProgramPerMonthYear", "madhav.g@zeta.tech", "2017-11-29 14:01"));
    list.add(new AuditLog("Payouts", "madhav.g@zeta.tech", "2017-11-17 9:17"));
    list.add(
        new AuditLog("PayoutsReportUsingCardprogramIDs", "madhav.g@zeta.tech", "2017-10-26 14:05"));
    list.add(
        new AuditLog("PayoutsReportUsingCardprogramIDs", "madhav.g@zeta.tech", "2017-11-17 9:16"));
    list.add(
        new AuditLog("ProductWiseSpendMonthHighLevel", "madhav.g@zeta.tech", "2017-11-06 12:26"));
    list.add(new AuditLog("QuickshopDB test report", "madhav.g@zeta.tech", "2017-10-13 14:02"));
    list.add(
        new AuditLog(
            "ReimbursementMasterCarMaintenance", "madhav.g@zeta.tech", "2017-11-08 15:17"));
    list.add(new AuditLog("ReimbursementMasterLTA", "madhav.g@zeta.tech", "2017-11-16 15:24"));
    list.add(new AuditLog("ReimbursementMasterLTA", "madhav.g@zeta.tech", "2017-11-28 12:39"));
    list.add(new AuditLog("ReimbursementMasterReport", "madhav.g@zeta.tech", "2017-10-26 10:04"));
    list.add(new AuditLog("ReimbursementMasterReport", "madhav.g@zeta.tech", "2017-11-28 6:43"));
    list.add(new AuditLog("ReimbursementMasterReport_v1", "madhav.g@zeta.tech", "2017-10-26 9:56"));
    list.add(
        new AuditLog("ReimbursementMasterReportNew", "madhav.g@zeta.tech", "2017-11-27 14:12"));
    list.add(new AuditLog("SalesSettlementReports", "madhav.g@zeta.tech", "2017-12-08 10:33"));
    list.add(new AuditLog("Spending", "madhav.g@zeta.tech", "2017-10-16 14:12"));
    list.add(
        new AuditLog("SpendReportWithVerifiedEmails", "madhav.g@zeta.tech", "2017-10-17 13:37"));
    list.add(
        new AuditLog("SpendsReportUsingCardprogramID", "madhav.g@zeta.tech", "2017-11-24 9:04"));
    list.add(
        new AuditLog("SpendsReportUsingCardprogramID", "madhav.g@zeta.tech", "2017-12-06 7:42"));
    list.add(
        new AuditLog(
            "SpendsReportUsingCardprogramIDSamsung", "madhav.g@zeta.tech", "2017-10-31 10:54"));
    list.add(
        new AuditLog(
            "SpendsReportWithoutVerifiedEmails", "madhav.g@zeta.tech", "2017-10-17 12:36"));
    list.add(
        new AuditLog("SpendsReportwithTransactionType", "madhav.g@zeta.tech", "2017-12-05 11:14"));
    list.add(new AuditLog("TransactionNotTried", "madhav.g@zeta.tech", "2017-11-08 12:29"));
    list.add(new AuditLog("TransactionSuccessful", "madhav.g@zeta.tech", "2017-11-04 17:36"));
    list.add(
        new AuditLog("Unique Users - Monthly Report", "madhav.g@zeta.tech", "2017-12-01 6:00"));
    list.add(
        new AuditLog(
            "Users Spending Atleast Once from Cardprogram",
            "madhav.g@zeta.tech",
            "2017-10-18 11:00"));
    list.add(
        new AuditLog(
            "Users Spending Atleast Once from Cardprogram",
            "madhav.g@zeta.tech",
            "2017-12-08 10:30"));
    list.add(
        new AuditLog(
            "UsersAddingMoneyToCashCardHighLevel", "madhav.g@zeta.tech", "2017-11-01 19:16"));
    list.add(new AuditLog("UserSummary", "madhav.g@zeta.tech", "2017-10-16 14:15"));
    list.add(
        new AuditLog("UsersWithdrawingtoBankHighLevel", "madhav.g@zeta.tech", "2017-10-31 13:20"));
    list.add(new AuditLog("ZetaBusinessMetrics", "madhav.g@zeta.tech", "2017-12-08 9:27"));
    list.add(
        new AuditLog("ZetaBusinessMetricsLedgerBiller", "madhav.g@zeta.tech", "2017-12-08 14:01"));
    list.add(new AuditLog("ZetaMonthlyStatement", "madhav.g@zeta.tech", "2017-12-06 7:18"));
    list.add(new AuditLog("ZetaMonthlyStatement", "madhav.g@zeta.tech", "2017-12-06 7:19"));
    list.add(new AuditLog("ZetaPaymentCiscoBreakfast", "madhav.g@zeta.tech", "2017-11-11 12:35"));
    list.add(new AuditLog("ZetaPaymentCiscoDinner", "madhav.g@zeta.tech", "2017-11-11 12:34"));
    list.add(new AuditLog("ZetaPaymentCiscoLunch", "madhav.g@zeta.tech", "2017-11-11 12:35"));
    list.add(new AuditLog("ZetaPaymentCiscoSnacks", "madhav.g@zeta.tech", "2017-11-11 12:35"));
    list.add(new AuditLog("cardProgramSpends", "manish.shu@zeta.tech", "2017-10-23 13:42"));
    list.add(new AuditLog("cardProgramSpends", "manish.shu@zeta.tech", "2017-11-17 9:36"));
    list.add(
        new AuditLog(
            "CardProgramSummaryReportSamsung", "manish.shu@zeta.tech", "2017-11-01 13:12"));
    list.add(new AuditLog("CardProgramYearEndReport", "manish.shu@zeta.tech", "2017-12-01 10:43"));
    list.add(new AuditLog("ClosedCardPayoutStatement", "manish.shu@zeta.tech", "2017-11-06 7:17"));
    list.add(
        new AuditLog("ClosedCardPayoutStatementTemp", "manish.shu@zeta.tech", "2017-10-25 10:02"));
    list.add(new AuditLog("ClosedCardStatusByOrderID", "manish.shu@zeta.tech", "2017-11-17 6:07"));
    list.add(
        new AuditLog(
            "ClosedCardwithCardIDsforAllowanceProducts",
            "manish.shu@zeta.tech",
            "2017-10-27 17:43"));
    list.add(
        new AuditLog(
            "ClosedCardwithCardIDsforAllowanceProducts",
            "manish.shu@zeta.tech",
            "2017-11-17 11:46"));
    list.add(new AuditLog("FundingAccountFundMovement", "manish.shu@zeta.tech", "2017-10-30 8:36"));
    list.add(
        new AuditLog("FundingAccountTransferDetail", "manish.shu@zeta.tech", "2017-10-30 8:37"));
    list.add(new AuditLog("MerchantActivity_Each", "manish.shu@zeta.tech", "2017-10-23 13:34"));
    list.add(
        new AuditLog(
            "PayoutsReportUsingCardprogramIDs", "manish.shu@zeta.tech", "2017-11-17 9:51"));
    list.add(
        new AuditLog(
            "PayoutsReportUsingCardprogramIDs", "manish.shu@zeta.tech", "2017-11-17 9:56"));
    list.add(
        new AuditLog(
            "ReimbursementMasterCarMaintenance", "manish.shu@zeta.tech", "2017-11-13 11:32"));
    list.add(
        new AuditLog(
            "ReimbursementMasterCarMaintenance", "manish.shu@zeta.tech", "2017-11-28 11:58"));
    list.add(new AuditLog("ReimbursementMasterLTA", "manish.shu@zeta.tech", "2017-11-15 14:59"));
    list.add(new AuditLog("ReimbursementMasterLTA", "manish.shu@zeta.tech", "2017-11-28 12:51"));
    list.add(
        new AuditLog("ReimbursementMasterReportNew", "manish.shu@zeta.tech", "2017-11-28 13:48"));
    list.add(
        new AuditLog("SpendsReportUsingCardprogramID", "manish.shu@zeta.tech", "2017-11-17 7:10"));
    list.add(
        new AuditLog("SpendsReportUsingCardprogramID", "manish.shu@zeta.tech", "2017-11-17 9:58"));
    list.add(
        new AuditLog("CashlessCafeteria_Corporate_Report", "rohil.g@zeta.in", "2017-12-08 1:10"));
    list.add(new AuditLog("Fresh_Settlements", "rohil.g@zeta.in", "2017-12-08 10:29"));
    list.add(
        new AuditLog("PendingAndFailedPayoutsStatement", "rohil.g@zeta.in", "2017-12-08 4:59"));
    list.add(
        new AuditLog(
            "Report_CorpBen_CorpOrderSummary_AttributionData_TillDate",
            "rohil.g@zeta.in",
            "2017-12-08 2:30"));
    list.add(
        new AuditLog(
            "Report_CorpBen_Payout_withStatus_Scheduled_ISOParams",
            "rohil.g@zeta.in",
            "2017-12-08 2:30"));
    list.add(new AuditLog("Settlement_Report_Backlog", "rohil.g@zeta.in", "2017-12-08 13:30"));
    list.add(new AuditLog("Unsettled_Transactions", "rohil.g@zeta.in", "2017-12-08 2:30"));
    list.add(new AuditLog("1_a_testingtzz", "thumbnil.b@zeta.tech", "2017-10-10 12:55"));
    list.add(new AuditLog("1_atestingtz", "thumbnil.b@zeta.tech", "2017-10-10 11:02"));
    list.add(new AuditLog("1_atestingtz_1", "thumbnil.b@zeta.tech", "2017-10-10 12:04"));
    list.add(new AuditLog("1_atestingtz_2", "thumbnil.b@zeta.tech", "2017-10-10 13:36"));
    list.add(
        new AuditLog("1_onus_unsettled_kotak(temp)", "thumbnil.b@zeta.tech", "2017-11-30 12:20"));
    list.add(new AuditLog("1_onus_unsettled(temp)", "thumbnil.b@zeta.tech", "2017-10-27 14:12"));
    list.add(new AuditLog("1.0_onus_unsettled_kotak", "thumbnil.b@zeta.tech", "2017-10-26 11:21"));
    list.add(new AuditLog("1.1_Spends_OnUsMerchants", "thumbnil.b@zeta.tech", "2017-11-22 8:45"));
    list.add(
        new AuditLog("1.2_Spends_SupercardMerchants", "thumbnil.b@zeta.tech", "2017-11-22 8:46"));
    list.add(new AuditLog("10.1_merchant_txn_dump", "thumbnil.b@zeta.tech", "2017-12-08 5:09"));
    list.add(new AuditLog("2.1_Funding_PaymentGateway", "thumbnil.b@zeta.tech", "2017-10-26 7:48"));
    list.add(
        new AuditLog("2.2_Funding_CorpOpsFundLoad", "thumbnil.b@zeta.tech", "2017-10-26 7:48"));
    list.add(new AuditLog("2.3_Funding_CorpIMPS-IN", "thumbnil.b@zeta.tech", "2017-10-26 7:49"));
    list.add(
        new AuditLog("2.4_Funding_Cardloads_corpusers", "thumbnil.b@zeta.tech", "2017-12-08 1:10"));
    list.add(
        new AuditLog(
            "3.1_Clearing_ClearedTransactions_Rupay", "thumbnil.b@zeta.tech", "2017-12-08 1:10"));
    list.add(
        new AuditLog(
            "3.2_Clearing_UnsettledTransactions", "thumbnil.b@zeta.tech", "2017-12-08 1:12"));
    list.add(
        new AuditLog("4.1_Summary_balance_report", "thumbnil.b@zeta.tech", "2017-12-08 10:29"));
    list.add(new AuditLog("5.1_Users_BalanceReport", "thumbnil.b@zeta.tech", "2017-12-08 1:16"));
    list.add(new AuditLog("5.2_Merchant_BalanceReport", "thumbnil.b@zeta.tech", "2017-12-08 1:18"));
    list.add(
        new AuditLog("5.3_Corporate_BalanceReport", "thumbnil.b@zeta.tech", "2017-12-08 1:20"));
    list.add(new AuditLog("accenture_usage_details", "thumbnil.b@zeta.tech", "2017-10-20 13:04"));
    list.add(new AuditLog("accenture_usage_details_1", "thumbnil.b@zeta.tech", "2017-10-23 9:47"));
    list.add(new AuditLog("BalanceSheet", "thumbnil.b@zeta.tech", "2017-10-25 14:28"));
    list.add(new AuditLog("Card_activation", "thumbnil.b@zeta.tech", "2017-10-23 13:56"));
    list.add(new AuditLog("CardKYCdata", "thumbnil.b@zeta.tech", "2017-10-16 9:41"));
    list.add(new AuditLog("CardKycData", "thumbnil.b@zeta.tech", "2017-10-25 14:28"));
    list.add(new AuditLog("CardLoads", "thumbnil.b@zeta.tech", "2017-10-16 9:49"));
    list.add(
        new AuditLog("ClosedCardPayoutStatementTemp", "thumbnil.b@zeta.tech", "2017-10-25 10:01"));
    list.add(
        new AuditLog("CountUsers_balmorethan1lac", "thumbnil.b@zeta.tech", "2017-12-08 14:06"));
    list.add(new AuditLog("EndOfDayBalance", "thumbnil.b@zeta.tech", "2017-11-06 9:53"));
    list.add(new AuditLog("EOD_balance", "thumbnil.b@zeta.tech", "2017-10-17 17:26"));
    list.add(new AuditLog("Funding", "thumbnil.b@zeta.tech", "2017-10-23 10:10"));
    list.add(new AuditLog("idfc_sbr(temp)", "thumbnil.b@zeta.tech", "2017-11-28 9:19"));
    list.add(
        new AuditLog("Kotak_Merchant_Settlements", "thumbnil.b@zeta.tech", "2017-11-03 11:15"));
    list.add(new AuditLog("kotak_sbr", "thumbnil.b@zeta.tech", "2017-10-10 4:03"));
    list.add(new AuditLog("kotak_sbr(temp)", "thumbnil.b@zeta.tech", "2017-11-06 14:28"));
    list.add(
        new AuditLog("MasterkeyReport_5Columns_Ledger", "thumbnil.b@zeta.tech", "2017-11-27 7:53"));
    list.add(
        new AuditLog(
            "MasterkeyReport_5Columns_Reporting", "thumbnil.b@zeta.tech", "2017-11-27 7:48"));
    list.add(new AuditLog("merchant_dump_kotak", "thumbnil.b@zeta.tech", "2017-11-08 14:19"));
    list.add(new AuditLog("Merchant_SettlementDone", "thumbnil.b@zeta.tech", "2017-11-06 12:04"));
    list.add(new AuditLog("PmoDailyPpiMetrics", "thumbnil.b@zeta.tech", "2017-10-17 9:03"));
    list.add(new AuditLog("refund", "thumbnil.b@zeta.tech", "2017-10-16 9:48"));
    list.add(new AuditLog("RupaySpendsReport", "thumbnil.b@zeta.tech", "2017-11-15 10:21"));
    list.add(new AuditLog("Spending", "thumbnil.b@zeta.tech", "2017-11-15 10:27"));
    list.add(
        new AuditLog(
            "SpendsReportwithTransactionType", "thumbnil.b@zeta.tech", "2017-10-20 10:47"));
    list.add(new AuditLog("Txn_dump", "thumbnil.b@zeta.tech", "2017-10-16 9:45"));
    list.add(new AuditLog("UsersPerIIN", "thumbnil.b@zeta.tech", "2017-11-17 6:47"));
    list.add(new AuditLog("UserSummary", "thumbnil.b@zeta.tech", "2017-11-15 10:22"));
    list.add(
        new AuditLog(
            "Volume_Value_ofSupercard_637513", "thumbnil.b@zeta.tech", "2017-12-08 10:30"));

    // for(AuditLog log:list) {
    // System.out.println(log.name+log.owner+log.time);
    // }
    return list;
  }

  private static List<String> getPaths() {
    // TODO Auto-generated method stub

    List<String> list = new ArrayList<String>();
    list.add("/Reports/prod/Audit/Corp_Data_Recent");
    list.add("/Reports/prod/Audit/Ledger_Data_Recent");
    list.add("/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/AllCorp_Data");
    list.add("/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/AllCorp_Source_Data_Audit");
    list.add("/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/Corp_AllPayouts_Fuel_Practo");
    list.add(
        "/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/CorpBen_AllPayouts_Practo_Asset");
    list.add("/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/CorpBen_AllPayouts_Practo_Meal");
    list.add(
        "/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/CorpBen_AllPayouts_Practo_Medical");
    list.add(
        "/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/CorpBen_AllPayouts_WarRoom_Recent");
    list.add(
        "/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/Issuance_Details_Ledger_WarRoom");
    list.add("/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/Ledger_AllPayouts_Practo_Asset");
    list.add("/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/Ledger_AllPayouts_Practo_Fuel");
    list.add("/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/Ledger_AllPayouts_Practo_Meal");
    list.add(
        "/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/Ledger_AllPayouts_Practo_Medical");
    list.add(
        "/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/Ledger_AllPayouts_WarRoom_Recent");
    list.add("/Reports/prod/Audit/Nov16_Corp_Ledger_Reconciliation/PostReconcilation_ComapanyData");
    list.add("/Reports/prod/biller/CardProgram/CardProgramYearEndReport");
    list.add("/Reports/prod/biller/CardProgram/CloseCardReportBiller");
    list.add("/Reports/prod/biller/CardProgram/ReimbursementMasterCarMaintenance");
    list.add("/Reports/prod/biller/CardProgram/ReimbursementMasterLTA");
    list.add("/Reports/prod/biller/CardProgram/ReimbursementMasterReport");
    list.add("/Reports/prod/biller/CardProgram/ReimbursementMasterReport_v1");
    list.add("/Reports/prod/Corp_Benefits/FundingAccount/FundingAccountDeposits");
    list.add("/Reports/prod/Corp_Benefits/FundingAccount/FundingAccountFundMovement");
    list.add("/Reports/prod/Corp_Benefits/FundingAccount/FundingAccountTransferDetail");
    list.add("/Reports/prod/Corp_Benefits/IMV/Scheduled_Reports/CorpBen_IMV_Payouts");
    list.add("/Reports/prod/Corp_Benefits/IMV/Scheduled_Reports/IMV_Payout_Weekly");
    list.add("/Reports/prod/Corp_Benefits/IMV_Corp/Scheduled_Reports/CorpBen_IMVCorp_Payouts");
    list.add("/Reports/prod/Corp_Benefits/IMV_Corp/Scheduled_Reports/IMV_Corp_Payout_Weekily");
    list.add("/Reports/prod/Corp_Benefits/IMV_Sales/Scheduled_Reports/CorpBen_IMVSales_Payouts");
    list.add("/Reports/prod/Corp_Benefits/IMV_Sales/Scheduled_Reports/IMV_Sales_Payout_Weekly");
    list.add("/Reports/prod/Corp_Benefits/Instagift/Payouts");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/AllOrders_Av1");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/AttributionData_SOND");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/ClosedCardPayoutStatement");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/ClosedCardPayoutStatementTemp");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/ClosedCardStatusByOrderID");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/CorpBen_AllPayoutsOfGivenCompany");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/CorpBen_AreaSalesMangerDetails");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/CorpBen_AttributionData_ISOParam");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/CorpBen_CompanyPayout_GivenTimePeriod_CompanyID");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/CorpBen_CompanyPayout_GivenTimePeriod_CompanyID_v1");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/CorpBen_CompanyPayouts_GivenTImePeriod_CompanyID_IncludesIssued");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/CorpBen_CompanyPayouts_GivenTImePeriod_CompanyID_v2");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/CorpBen_Invoices");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/CorpBen_OrderPlacedYesNo_Report");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/CorpBen_PayoutDetails_v1");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/CorpBen_TestCompanies");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/Employee");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/NewUser_NonRecurrimg");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/NewUsers");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/Report_CorpBen_CorpOrderSummary_AttributionData_Customized");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/Report_CorpBen_IssuanceDetails_Company_TimePeriod");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/Report_CorpBen_MealMoney_CompanyTransaction_v1");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/Report_CorpBen_MM_Credits");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/Report_CorpBen_Payout_withStatus_Scheduled");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/Report_Zensar_Periodic_Assessment_ProductWise");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/Samsung_Reports/PayoutsReportUsingCardprogramIDs");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/Smriti_Data_New");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/Stalled_Order");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/On_Request_Reports/Total_Payouts");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Amazon/AmazonPayoutStatement");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/AREA");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/AttributionData_tillDate");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/AttributionReport");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/CompaniesAndOrderDetails");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/CompanyDetails");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/CorpBen_Credits");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Corporate_Order_Report___Monthly");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/CORPORATEACCOUNTS");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/CorpOrderDetails_V2");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/NewUsersAddedMonthlyReport");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/NewUsersAddedMonthlyReport___NonSodexo");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/NewUsersAddedMonthlyReport___Sodexo");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/NewUsersMonthtillDate_Daily_Report");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/NewUsersMonthtillDate_Daily_Report___NonSodexo");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/NewUsersMonthtillDate_Daily_Report___Sodexo");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/PayoutsSummary");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/PayoutStatement");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/PendingAndFailedPayoutsStatement");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Post_Sales_Reports/Corporate_Sign_Up_Request___Daily");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Post_Sales_Reports/Daily_Order_Summary_Report");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Post_Sales_Reports/Fund_Request_Summary__Daily_Report");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Post_Sales_Reports/Orders_Completed_Last_Month___Daily_Report");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Report_CorpBen_AddFunds_Weekly");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Report_CorpBen_CorpOrderSummary_AttributionData_Daily");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Report_CorpBen_CorpOrderSummary_AttributionData_TillDate");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Report_CorpBen_Credits_Weekly");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Report_CorpBen_Invoices_Weekly");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Report_CorpBen_Payout_withStatus_Scheduled");
    list.add(
        "/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Sales_AreaManagers_Contribution_");
    list.add("/Reports/prod/Corp_Benefits/MealMoney/Weekly_Reports/Unique_Users___Monthly_Report");
    list.add("/Reports/prod/Corp_Benefits/ReportingDB/CardprogramReportSamsung");
    list.add("/Reports/prod/Corp_Benefits/ReportingDB/ClosedCardledgerSubquery");
    list.add("/Reports/prod/Corp_Benefits/ReportingDB/ClosedCardwithCardIDs");
    list.add("/Reports/prod/Corp_Benefits/ReportingDB/ClosedCardwithCardIDsforAllowanceProducts");
    list.add("/Reports/prod/Corp_Benefits/ReportingDB/DailyPendingBillsSummary");
    list.add("/Reports/prod/Corp_Benefits/ReportingDB/SpendsReportUsingCardprogramIDSamsung");
    list.add(
        "/Reports/prod/Corp_Benefits/ReportingDB/Users_Spending_Atleast_Once_from_Cardprogram");
    list.add("/Reports/prod/IFI/Common/Auth_Report");
    list.add("/Reports/prod/IFI/Common/Corporate___Add_Funds_detailed_report");
    list.add("/Reports/prod/IFI/Common/Corporate_Data_Detailed_Report");
    list.add("/Reports/prod/IFI/Common/CountUsers_balmorethan1lac");
    list.add("/Reports/prod/IFI/Common/DSC___IMPSorNEFT_OUT_older_than_8_days");
    list.add("/Reports/prod/IFI/Common/FundMovement/1.1_Spends_OnUsMerchants");
    list.add("/Reports/prod/IFI/Common/FundMovement/1.2_Spends_SupercardMerchants");
    list.add("/Reports/prod/IFI/Common/FundMovement/2.1_Funding_PaymentGateway");
    list.add("/Reports/prod/IFI/Common/FundMovement/2.2_Funding_CorpOpsFundLoad");
    list.add("/Reports/prod/IFI/Common/FundMovement/2.3_Funding_CorpIMPS_IN");
    list.add("/Reports/prod/IFI/Common/FundMovement/2.4_Funding_Cardloads_corpusers");
    list.add("/Reports/prod/IFI/Common/FundMovement/3.1_Clearing_ClearedTransactions_MasterCard");
    list.add("/Reports/prod/IFI/Common/FundMovement/3.1_Clearing_ClearedTransactions_Rupay");
    list.add("/Reports/prod/IFI/Common/FundMovement/3.2_Clearing_UnsettledTransactions");
    list.add("/Reports/prod/IFI/Common/IfiDailyAggregates");
    list.add("/Reports/prod/IFI/Common/KycUsersCount");
    list.add("/Reports/prod/IFI/Common/MonthlyPPISummary_Part1");
    list.add("/Reports/prod/IFI/Common/MonthlyPPISummary_Part2");
    list.add("/Reports/prod/IFI/Common/MonthlyPPISummary_WithKycSplit_Part1");
    list.add("/Reports/prod/IFI/Common/MonthlyPPISummary_WithKycSplit_Part2");
    list.add("/Reports/prod/IFI/Common/Outstanding_Detailed_Report_");
    list.add("/Reports/prod/IFI/Common/RBICyberSecQuarterlyReturn");
    list.add("/Reports/prod/IFI/Common/Statement/10.1_merchant_txn_dump");
    list.add("/Reports/prod/IFI/Common/Statement/4.1_Summary_balance_report");
    list.add("/Reports/prod/IFI/Common/Statement/5.1_Users_BalanceReport");
    list.add("/Reports/prod/IFI/Common/Statement/5.2_Merchant_BalanceReport");
    list.add("/Reports/prod/IFI/Common/Statement/5.3_Corporate_BalanceReport");
    list.add("/Reports/prod/IFI/Common/SuperCardStats");
    list.add("/Reports/prod/IFI/Common/UsersPerIIN");
    list.add("/Reports/prod/IFI/Common/W2A_detailed");
    list.add("/Reports/prod/IFI/IDFC/1_onus_unsettled");
    list.add("/Reports/prod/IFI/IDFC/1_onus_unsettled_kotak");
    list.add("/Reports/prod/IFI/IDFC/idfc_sbr");
    list.add("/Reports/prod/IFI/IDFC/kotak_sbr");
    list.add("/Reports/prod/IFI/IDFC/RupaySpendsReport");
    list.add("/Reports/prod/IFI/RBL/Card_activation");
    list.add("/Reports/prod/IFI/RBL/CardKYCdata");
    list.add("/Reports/prod/IFI/RBL/CardLoads");
    list.add("/Reports/prod/IFI/RBL/EOD_balance");
    list.add("/Reports/prod/IFI/RBL/refund");
    list.add("/Reports/prod/IFI/RBL/Txn_dump");
    list.add("/Reports/prod/IFI/Sodexo/archive/SodexoReport");
    list.add("/Reports/prod/IFI/Sodexo/archive/ZSR");
    list.add("/Reports/prod/IFI/Sodexo/archive/ZUSR");
    list.add("/Reports/prod/IFI/Sodexo/sodexo_logsync");
    list.add("/Reports/prod/IFI/Sodexo/Volume_Value_ofSupercard_637513");
    list.add("/Reports/prod/IFI/Sodexo/ZSR_v1");
    list.add("/Reports/prod/IFI/Sodexo/ZTE");
    list.add("/Reports/prod/IFI/Sodexo/ZTE_GIFT");
    list.add("/Reports/prod/IFI/Sodexo/ZUSR_v1");
    list.add(
        "/Reports/prod/Ledger/Accounts/11_Payments_to_Merchants_Settlement_via_imps_etc__zeta_and_collect_call__ISOParams");
    list.add(
        "/Reports/prod/Ledger/Accounts/7_Transfer_from_Voucher_to_CASH_on_Billupload_ISOParams");
    list.add(
        "/Reports/prod/Ledger/Accounts/8a_Payments_to_individuals_on_settlement_to_merchants_directly_from_wallet_ISOParams");
    list.add(
        "/Reports/prod/Ledger/Accounts/8b_Payments_to_individuals_settlement_to_merchants_directly_from_wallet_ISOParams");
    list.add("/Reports/prod/Ledger/Accounts/8c_Imps_NEFT_in_CashCard");
    list.add("/Reports/prod/Ledger/Accounts/8d_Funds_into_CashCard_via_Gateway_ISOParams");
    list.add("/Reports/prod/Ledger/Accounts/9_Payment_to_Merchants_through_Individuals_ISOParams");
    list.add("/Reports/prod/Ledger/Accounts/Add_Funds_v3");
    list.add("/Reports/prod/Ledger/Accounts/CreditsReport");
    list.add("/Reports/prod/Ledger/Accounts/InstaGift_Debits_Report");
    list.add("/Reports/prod/Ledger/Accounts/InstaGift_Invoices_Report");
    list.add("/Reports/prod/Ledger/Accounts/Invoices");
    list.add("/Reports/prod/Ledger/Accounts/Invoices_v3");
    list.add("/Reports/prod/Ledger/Accounts/SalesSettlementReports");
    list.add("/Reports/prod/Ledger/Biller/Agent_Performance_Report");
    list.add("/Reports/prod/Ledger/Biller/Average_Processing_Time");
    list.add("/Reports/prod/Ledger/Biller/BillerAuditReport");
    list.add("/Reports/prod/Ledger/Biller/Bills_Processed_Per_Agent");
    list.add("/Reports/prod/Ledger/Biller/Pending_Bills_");
    list.add("/Reports/prod/Ledger/Biller/Processed_Bills");
    list.add("/Reports/prod/Ledger/CardProgram/CardProgramAggregateFinancials");
    list.add("/Reports/prod/Ledger/CardProgram/CardProgramAggregateFinancialsPerUser");
    list.add("/Reports/prod/Ledger/CardProgram/cardProgramSpends");
    list.add("/Reports/prod/Ledger/CardProgram/CardProgramSummaryReportSamsung");
    list.add("/Reports/prod/Ledger/CardProgram/CardProgramYearEndReport");
    list.add("/Reports/prod/Ledger/CardProgram/ConsolidatedMetrics_excelOut");
    list.add("/Reports/prod/Ledger/CardProgram/ConsolidatedStatements_excelOut");
    list.add("/Reports/prod/Ledger/CardProgram/FinancialMetrics_Current");
    list.add("/Reports/prod/Ledger/CardProgram/FundingAccountTransactions_Ledger_LedgerID");
    list.add("/Reports/prod/Ledger/CardProgram/MerchantActivity_Each");
    list.add("/Reports/prod/Ledger/CardProgram/MerchantActivity_TEST");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByPrevNDays/BusinessMetrics_forGivenPeriod");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByPrevNDays/BusinessMetrics_P1vsP2");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByPrevNDays/ConsumptionPattern");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByPrevNDays/DepositAndRevokeStatement");
    list.add(
        "/Reports/prod/Ledger/CardProgram/ReportsByPrevNDays/DepositAndRevokeStatement_AllCardPrograms");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByPrevNDays/DetailedProgramStatement_1");
    list.add(
        "/Reports/prod/Ledger/CardProgram/ReportsByPrevNDays/MerchantActivityMetrics_forGivenPeriod");
    list.add(
        "/Reports/prod/Ledger/CardProgram/ReportsByPrevNDays/ReconciliationCheckForAllCorporates");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByPrevNDays/ReimbursementStatement_1");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByPrevNDays/SpendsHistogram_1");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByPrevNDays/SpendStatement_1");
    list.add(
        "/Reports/prod/Ledger/CardProgram/ReportsByStartAndEndDates/BusinessMetrics_forGivenPeriod");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByStartAndEndDates/BusinessMetrics_P1vsP2_1");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByStartAndEndDates/DetailedProgramStatement");
    list.add(
        "/Reports/prod/Ledger/CardProgram/ReportsByStartAndEndDates/MerchantActivityMetrics_forGivenPeriod");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByStartAndEndDates/ReimbursementStatement");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByStartAndEndDates/SpendsHistogram");
    list.add("/Reports/prod/Ledger/CardProgram/ReportsByStartAndEndDates/SpendStatement");
    list.add("/Reports/prod/Ledger/CardProgram/SpendsReportUsingCardprogramID");
    list.add("/Reports/prod/Ledger/CardProgram/SpendsReportwithTransactionType");
    list.add("/Reports/prod/Ledger/CardProgram/Users_Spending_Atleast_Once_from_Cardprogram");
    list.add("/Reports/prod/Ledger/CashlessCafe/Cashless_cafe_Infinix_Session_Report___Dinner");
    list.add("/Reports/prod/Ledger/CashlessCafe/Cashless_cafe_Infinix_Session_Report___Lunch");
    list.add("/Reports/prod/Ledger/CashlessCafe/Cashless_Cafeteria_Corporate_Report_Latest");
    list.add("/Reports/prod/Ledger/CashlessCafe/Cashless_Cafeteria_Vendor_Report_Latest");
    list.add("/Reports/prod/Ledger/CashlessCafe/CashlessCafetriaReport_token");
    list.add("/Reports/prod/Ledger/CashlessCafe/CashlessCafteria_CorporateReport_Shift");
    list.add("/Reports/prod/Ledger/CashlessCafe/CashlessCafteria_CorporateReport_WithProductParam");
    list.add("/Reports/prod/Ledger/CashlessCafe/Vendor_Settlement_Report");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/CardKycData");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/DailyReports_BalanceSheet");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/DailyReports_CardKycData");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/DailyReports_EndOfDayBalance");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/DailyReports_Funding");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/DailyReports_Spending");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/DailyReports_UserSummary");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/EndOfDayBalance");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/Funding");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/Funding_1");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/Spending");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/Spending_1");
    list.add("/Reports/prod/Ledger/ledger_rbl/archive/UserSummary");
    list.add("/Reports/prod/Ledger/ledger_rbl/BalanceSheet");
    list.add("/Reports/prod/Ledger/ledger_rbl/CardKycData");
    list.add("/Reports/prod/Ledger/ledger_rbl/EndOfDayBalance");
    list.add("/Reports/prod/Ledger/ledger_rbl/Funding");
    list.add("/Reports/prod/Ledger/ledger_rbl/PmoDailyPpiMetrics");
    list.add("/Reports/prod/Ledger/ledger_rbl/Spending");
    list.add("/Reports/prod/Ledger/ledger_rbl/UserSummary");
    list.add("/Reports/prod/Ledger/MerchantFinancial/Merchant_Transactions_Report");
    list.add("/Reports/prod/Ledger/MerchantFinancial/Merchants_Master_Sheet");
    list.add("/Reports/prod/Ledger/MerchantFinancial/Prepaid_Recharge_Report");
    list.add("/Reports/prod/Ledger/MerchantFinancial/PrepaidRechargeReport_WithUsersPhoneNumber");
    list.add("/Reports/prod/Ledger/MerchantFinancial/Total_Curated_Stores");
    list.add("/Reports/prod/Ledger/MerchantFinancial/TransactionStatementForMids");
    list.add("/Reports/prod/Ledger/MerchantFinancial/TransactionStatementForTids");
    list.add("/Reports/prod/Ledger/MerchantFinancial/uncurated_but_Transacted_Stores");
    list.add("/Reports/prod/Ledger/MerchantFinancial/Unique_Curation_Till_Date");
    list.add("/Reports/prod/Ledger/Sales/Attribution");
    list.add("/Reports/prod/Ledger/Sales/DailyUserStatusReport");
    list.add("/Reports/prod/Ledger/Sales/DepositSummary");
    list.add("/Reports/prod/Ledger/Sales/IDFC_Report");
    list.add("/Reports/prod/Ledger/Sales/NewUsersAdded");
    list.add("/Reports/prod/Ledger/Sales/Redcarpet_AllTransaction_Report");
    list.add("/Reports/prod/Ledger/Sales/Redcarpet_User_Report");
    list.add("/Reports/prod/Ledger/Sales/UserStatusReport");
    list.add("/Reports/prod/Ledger/Support/DepositsAndRevokesToUsers");
    list.add("/Reports/prod/Ledger/Support/FundingAccountActivitySummary");
    list.add("/Reports/prod/Ledger/Support/PractoUnclaimedBalance");
    list.add("/Reports/prod/Ledger/Support/Unclassified_Debits");
    list.add("/Reports/prod/Ledger/Support/UnclassifiedCredits");
    list.add("/Reports/prod/Ledger/Support/UserTransactionHistory");
    list.add("/Reports/prod/Ledger/User_Entity/Activation_Report_using_CorpID");
    list.add("/Reports/prod/Ledger/User_Entity/Activation_Report_Using_Domain_Name");
    list.add(
        "/Reports/prod/Ledger/User_Entity/Output_Backup/Report_UserEntity_Employee_Card_Tag_ActivationStatus_Altimetrik");
    list.add("/Reports/prod/Ledger/UserFinancial/Biller_Report_For_Oyo_rooms");
    list.add("/Reports/prod/Ledger/UserFinancial/Biller_Report_For_Practo");
    list.add("/Reports/prod/Ledger/UserFinancial/Biller_Report_For_Shop_Clues");
    list.add("/Reports/prod/Ledger/UserFinancial/CloseCardReportLedger");
    list.add("/Reports/prod/Ledger/UserFinancial/Disbursed_Spent_Data_by_businessID");
    list.add("/Reports/prod/Ledger/UserFinancial/MV_User_Never_Used_SupercardOnline");
    list.add("/Reports/prod/Ledger/UserFinancial/MV_User_Never_Used_SupercardOnline_LastMonth");
    list.add("/Reports/prod/Ledger/UserFinancial/P2P_using_Shopid");
    list.add("/Reports/prod/Ledger/UserFinancial/PotenialKYCIssue_Merchant_ShopID");
    list.add("/Reports/prod/Ledger/UserFinancial/PotentialKycIssueUsers");
    list.add("/Reports/prod/Ledger/UserFinancial/PushPayment_to_merchants");
    list.add("/Reports/prod/Ledger/UserFinancial/Spend_More_Than_Given");
    list.add("/Reports/prod/Ledger/UserFinancial/User_Matrix");
    list.add("/Reports/prod/Ledger/UserFinancial/User_Transacton_Report");
    list.add("/Reports/prod/Ledger/UserFinancial/User_Usage_Report_GroceryStore");
    list.add("/Reports/prod/Ledger/UserFinancial/User_Usage_Report_ONLINE");
    list.add("/Reports/prod/Ledger/UserFinancial/User_Usage_Report_POS");
    list.add("/Reports/prod/Ledger/UserFinancial/UserTransactionReport");
    list.add("/Reports/prod/merchant_settlements/Archive/Fresh_Settlements_CURRENTLYUSED");
    list.add("/Reports/prod/merchant_settlements/Archive/Fresh_Settlements_JASPERNEW");
    list.add(
        "/Reports/prod/merchant_settlements/Archive/Report_1st_Backlog_Settlement_1_PM_Updated");
    list.add(
        "/Reports/prod/merchant_settlements/Archive/Report_2nd_Backlog_Settlement_4_PM_Updated");
    list.add(
        "/Reports/prod/merchant_settlements/Archive/Report_3rd_Backlog_Settlement_7_PM_Updated");
    list.add("/Reports/prod/merchant_settlements/Archive/Report_Fresh_Settlements_Daily_Updated");
    list.add(
        "/Reports/prod/merchant_settlements/Archive/Report_Unsettled_Transactions_2daysback_8_AM_Updated");
    list.add(
        "/Reports/prod/merchant_settlements/Archive/Report_Unsettled_Transactions_3daysback_8_AM_Updated");
    list.add("/Reports/prod/merchant_settlements/Archive/Settlement_Report_Backlog_CURRENTLYUSED");
    list.add("/Reports/prod/merchant_settlements/Archive/Settlement_Report_Backlog_JASPERNEW");
    list.add("/Reports/prod/merchant_settlements/Archive/Settlement_Reports_Backlog");
    list.add("/Reports/prod/merchant_settlements/Archive/Unsettled_Transactions");
    list.add("/Reports/prod/merchant_settlements/Archive/Unsettled_Transactions_CURRENTLYUSED");
    list.add("/Reports/prod/merchant_settlements/Archive/Unsettled_Transactions_JASPERNEW");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Elior/EliorNonZetaPaymentCiscoBreakfast");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Elior/EliorNonZetaPaymentCiscoDinner");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Elior/EliorNonZetaPaymentCiscoLunch");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Elior/EliorNonZetaPaymentCiscoSnacks");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Elior/EliorZetaPaymentCiscoBreakfast");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Elior/EliorZetaPaymentCiscoDinner");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Elior/EliorZetaPaymentCiscoLunch");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Elior/EliorZetaPaymentCiscoSnacks");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Elior/NonZetaMonthlyStatement");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Elior/ZetaMonthlyStatement");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Global/GlobalItemLevelCiscoMerchantEvening");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Global/GlobalItemLevelCiscoMerchantMorning");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Global/GlobalNonZetaPaymentCiscoEvening");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Global/GlobalNonZetaPaymentCiscoMorning");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Global/GlobalZetaPaymentCiscoEvening");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Global/GlobalZetaPaymentCiscoMorning");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Global/NonZetaMonthlyStatement");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/Global/ZetaMonthlyStatement");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/ItemLevelCiscoMerchantMidnight");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/ItemLevelReportCiscoMerchantEvening");
    list.add(
        "/Reports/prod/merchant_settlements/CiscoExpressSettlement/NonZetaPaymentCiscoBreakfast");
    list.add("/Reports/prod/merchant_settlements/CiscoExpressSettlement/NonZetaPaymentCiscoDinner");
    list.add("/Reports/prod/merchant_settlements/CiscoExpressSettlement/NonZetaPaymentCiscoLunch");
    list.add("/Reports/prod/merchant_settlements/CiscoExpressSettlement/NonZetaPaymentCiscoSnacks");
    list.add("/Reports/prod/merchant_settlements/CiscoExpressSettlement/ZetaPaymentCiscoBreakfast");
    list.add("/Reports/prod/merchant_settlements/CiscoExpressSettlement/ZetaPaymentCiscoDinner");
    list.add("/Reports/prod/merchant_settlements/CiscoExpressSettlement/ZetaPaymentCiscoLunch");
    list.add("/Reports/prod/merchant_settlements/CiscoExpressSettlement/ZetaPaymentCiscoSnacks");
    list.add("/Reports/prod/merchant_settlements/Critical_settlement_Alert");
    list.add("/Reports/prod/merchant_settlements/Fresh_Settlements_NOW");
    list.add("/Reports/prod/merchant_settlements/Kotak_Merchant_Settlements");
    list.add("/Reports/prod/merchant_settlements/MasterCardReport");
    list.add("/Reports/prod/merchant_settlements/MasterCardReversal_v2");
    list.add("/Reports/prod/merchant_settlements/Merchant_Reconciliation_Report");
    list.add("/Reports/prod/merchant_settlements/Merchant_SettlementDone");
    list.add("/Reports/prod/merchant_settlements/Out_Of_Band_Settlements");
    list.add("/Reports/prod/merchant_settlements/Sales_VS_settlement");
    list.add("/Reports/prod/merchant_settlements/Settlement_Report_Backlog_NOW");
    list.add("/Reports/prod/merchant_settlements/Unsettled_Transactions_NOW");
    list.add("/Reports/prod/ReportCenter/prod/biller/CardProgram/CardProgramYearEndReport");
    list.add(
        "/Reports/prod/ReportCenter/prod/biller/CardProgram/ReimbursementMasterCarMaintenance");
    list.add("/Reports/prod/ReportCenter/prod/biller/CardProgram/ReimbursementMasterLTA");
    list.add("/Reports/prod/ReportCenter/prod/biller/CardProgram/ReimbursementMasterReport");
    list.add("/Reports/prod/ReportCenter/prod/biller/CardProgram/ReimbursementMasterReportNew");
    list.add("/Reports/prod/ReportCenter/prod/Corp_Benefits/FundingAccount/FundingAccountDeposits");
    list.add(
        "/Reports/prod/ReportCenter/prod/Corp_Benefits/FundingAccount/FundingAccountFundMovement");
    list.add(
        "/Reports/prod/ReportCenter/prod/Corp_Benefits/FundingAccount/FundingAccountTransferDetail");
    list.add("/Reports/prod/ReportCenter/prod/Corp_Benefits/Instagift/Payouts");
    list.add(
        "/Reports/prod/ReportCenter/prod/Corp_Benefits/MealMoney/On_Request_Reports/ClosedCardPayoutStatement");
    list.add(
        "/Reports/prod/ReportCenter/prod/Corp_Benefits/MealMoney/On_Request_Reports/ClosedCardStatusByOrderID");
    list.add(
        "/Reports/prod/ReportCenter/prod/Corp_Benefits/MealMoney/On_Request_Reports/Samsung_Reports/PayoutsReportUsingCardprogramIDs");
    list.add("/Reports/prod/ReportCenter/prod/Corp_Benefits/ReportingDB/ClosedCardwithCardIDs");
    list.add(
        "/Reports/prod/ReportCenter/prod/Corp_Benefits/ReportingDB/ClosedCardwithCardIDsforAllowanceProducts");
    list.add("/Reports/prod/ReportCenter/prod/Ledger/CardProgram/cardProgramSpends");
    list.add("/Reports/prod/ReportCenter/prod/Ledger/CardProgram/CardProgramSummaryReportSamsung");
    list.add("/Reports/prod/ReportCenter/prod/Ledger/CardProgram/CardProgramYearEndReport");
    list.add("/Reports/prod/ReportCenter/prod/Ledger/CardProgram/EmployeeSpends");
    list.add("/Reports/prod/ReportCenter/prod/Ledger/CardProgram/SpendsReportUsingCardprogramID");
    list.add("/Reports/prod/User/AddressResolution_MissedUserEmails_Statement");
    list.add("/Reports/prod/User/KYC_Tools_User_Data_Health");
    list.add("/Reports/prod/User/User_Entity_Getting_Stale_Alert");
    return list;
  }
}
