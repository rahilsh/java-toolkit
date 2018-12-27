
package in.zc;

public class DemoDataUtil {}

/*package jp.skydesk.sales.util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Random;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.apache.commons.io.IOUtils;

public class DemoDataUtil {

	/**
	 * Generate a years worth of Motivator Demo data
	 *
	 * @param args
	 * @throws Exception
	 */
	/*public static void main(String[] args) throws Exception {
 		//Set the year you want to generate data
 		int dataYear = 2016;
 		//Set the number of Users in the Org
 		int orgUserCount = 4;
 		//Set records per employee range for each module
 		HashMap<String, Integer[]> userRecordCountMap = new HashMap<String, Integer[]>();
 		userRecordCountMap.put("Leads", new Integer[] { 3, 9 });
 		userRecordCountMap.put("Accounts", new Integer[] { 1, 3 });
 		userRecordCountMap.put("Potentials", new Integer[] { 1, 3 });
 		userRecordCountMap.put("Calls", new Integer[] { 3, 9 });
 		userRecordCountMap.put("Events", new Integer[] { 1, 3 });
 		//Potentials Amount
 		int minAmount = 25000;
 		int maxAmount = 75000;
 		int amountMultiplier = 5000;
 		//List of modules
 		String[] modules = { "Leads", "Accounts", "Potentials", "Calls",
 				"Events" };
 		//CSV Headers for each module
 		HashMap<String, String> csvHeaders = new HashMap<String, String>();
 		csvHeaders
 				.put("Leads",
 						"Lead Id,Last Name,Lead Owner Id,Created By Id,Modified By Id,Created Time,Modified Time,First Name,Email,Phone,Company,Lead Source,Industry");
 		csvHeaders
 				.put("Accounts",
 						"Account Id,Account Name,Account Owner Id,Created By Id,Modified By Id,Created Time,Modified Time");
 		csvHeaders
 				.put("Potentials",
 						"Potential Id,Potential Name,Potential Owner Id,Created By Id,Modified By Id,Created Time,Modified Time,Amount,Closing Date,Account Id,Type,Lead Source,Stage");
 		csvHeaders
 				.put("Stages",
 						"Potential Stage History Id,Potential Id,Amount,Stage,Probability (%),Expected Revenue,Close Date,Last Modified Id,Last Modified Time");
 		csvHeaders
 				.put("Calls",
 						"Call Id,Call Owner Id,Created By Id,Modified By Id,Created Time,Modified Time,Call Type,Call Purpose,Subject,Call Start Time,Call Duration");
 		csvHeaders
 				.put("Events",
 						"Event Id,Event Owner Id,Created By Id,Modified By Id,Created Time,Modified Time,Title,From,To");
 		//Picklist values
 		String[] leadSource = { "Advertisement", "Cold Call",
 				"Employee Referral", "External Referral", "OnlineStore",
 				"Partner", "Public Relations", "Sales Mail Alias",
 				"Seminar Partner", "Seminar-Internal", "Trade Show",
 				"Web Download", "Web Research", "Chat" };

 		String[] leadIndustry = { "ASP", "Data/Telecom OEM", "ERP",
 				"Government/Military", "Large Enterprise", "ManagementISV",
 				"MSP (Management Service Provider)",
 				"Network Equipment (Enterprise)", "Non-management ISV",
 				"Optical Networking", "Service Provider",
 				"Small/Medium Enterprise", "Storage Equipment",
 				"Storage Service Provider", "Systems Integrator",
 				"Wireless Industry" };

 		String[] potentialType = { "Existing Business", "New Business" };

 		String[] potentialStage = { "Qualification", "Needs Analysis",
 				"Value Proposition", "Id. Decision Makers",
 				"Proposal/Price Quote", "Negotiation/Review", "Closed Won",
 				"Closed Lost", "Closed Lost to Competition" };

 		int[] potentialStageProbability = { 10, 20, 40, 60, 75, 90, 100, 0, 0 };

 		String[] callType = { "InBound", "OutBound" };
 		String[] callPurpose = { "Prospecting", "Administrative",
 				"Negotiation", "Demo", "Project", "Support" };

 		String[] eventTitlePrefix = { "F2F", "Web", "Phone" };

 		String namesCSV = "us-500.csv";
 		int nameIndex = 0;

 		ArrayList<HashMap<String, String>> namesList = new ArrayList<HashMap<String, String>>();
 		//Read in names
 		try {
 			String namesCSVString = IOUtils.toString(DemoDataUtil.class
 					.getClassLoader().getResourceAsStream(namesCSV));
 			CSVParser parser = CSVParser.parse(namesCSVString, CSVFormat.EXCEL);
 			for (CSVRecord record : parser) {
 				if (record.getRecordNumber() > 1) {
 					HashMap<String, String> nameMap = new HashMap<String, String>();
 					nameMap.put("firstname", record.get(0));
 					nameMap.put("lastname", record.get(1));
 					nameMap.put("company", '"' + record.get(2) + '"');
 					nameMap.put("phone", record.get(8));
 					nameMap.put("email", record.get(10));
 					namesList.add(nameMap);
 				}
 			}
 		} catch (Exception e) {
 			e.printStackTrace();
 		}
 		int namesListSize = namesList.size();
 		SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
 		SimpleDateFormat dateTimeFormat = new SimpleDateFormat(
 				"MM/dd/yyyy HH:mm:ss");
 		Calendar start = Calendar.getInstance();
 		//Start from previous year's december 15th
 		start.set(Calendar.YEAR, dataYear - 1);
 		start.set(Calendar.MONTH, Calendar.DECEMBER);
 		start.set(Calendar.DATE, 15);
 		start.set(Calendar.HOUR_OF_DAY, 0);
 		start.set(Calendar.MINUTE, 0);
 		start.set(Calendar.SECOND, 0);
 		start.set(Calendar.MILLISECOND, 0);
 		Calendar end = (Calendar) start.clone();
 		end.add(Calendar.DATE, 1);
 		clean(dataYear);
 		long recordSuffix = 0;
 		for (int c = 0; c < 365; c++) {
 			//Account Ids to use in Potentials
 			List<String> accountIds = new ArrayList<String>();
 			List<String> accountNames = new ArrayList<String>();
 			StringBuilder stages = new StringBuilder();
 			if (c == 0) {
 				stages.append(csvHeaders.get("Stages")).append("\n");
 			}
 			for (String module : modules) {
 				StringBuilder records = new StringBuilder();
 				if (c == 0) {
 					records.append(csvHeaders.get(module)).append("\n");
 				}
 				boolean leads = false;
 				boolean potentials = false;
 				boolean accounts = false;
 				boolean calls = false;
 				boolean events = false;
 				if (module.equals("Leads")) {
 					leads = true;
 				} else if (module.equals("Accounts")) {
 					accounts = true;
 				} else if (module.equals("Potentials")) {
 					potentials = true;
 				} else if (module.equals("Calls")) {
 					calls = true;
 				} else if (module.equals("Events")) {
 					events = true;
 				}
 				Integer[] userRecordCount = userRecordCountMap.get(module);
 				long recordCount = random(userRecordCount[0],
 						userRecordCount[1]);
 				for (int i = 0; i < recordCount; i++) {
 					int owner = (int) random(1, orgUserCount);
 					//Common fields
 					//Unique record id
 					String recordId = module + "_" + dataYear + "_"
 							+ (recordSuffix++);
 					records.append(recordId).append(",");
 					String name, company, email, phone, firstname;
 					if (namesListSize == 0) {
 						//Use record id as Record name
 						name = recordId;
 						firstname = recordId;
 						company = recordId;
 						email = recordId + "@zoho.com";
 						phone = String.valueOf(random(1000000l, 9999999l));
 					} else {
 						HashMap<String, String> nameMap = namesList
 								.get(nameIndex++);
 						if (nameIndex == namesListSize) {
 							nameIndex = 0;
 						}
 						name = nameMap.get("lastname");
 						firstname = nameMap.get("firstname");
 						company = nameMap.get("company");
 						if (accounts || potentials) {
 							name = company;
 						}
 						phone = nameMap.get("phone");
 						email = nameMap.get("email");
 					}
 					if (accounts) {
 						accountIds.add(recordId);
 						accountNames.add(company);
 					}
 					String accountId = null;
 					if (potentials) {
 						//Set the same company name as accounts
 						int accountIdx = (int) random(0, accountIds.size() - 1);
 						accountId = accountIds.get(accountIdx);
 						name = accountNames.get(accountIdx);
 					}
 					if (!calls && !events) {
 						records.append(name).append(",");
 					}

 					//Owner, Created by, Modified by ids
 					records.append(owner).append(",").append(owner).append(",")
 							.append(owner).append(",");
 					Calendar recordStart = (Calendar) start.clone();
 					Calendar recordEnd = (Calendar) end.clone();
 					//Created & Modified Time between start and end of the period
 					Date recordDate = new Date(random(
 							recordStart.getTimeInMillis(),
 							recordEnd.getTimeInMillis()));
 					String recordDateString = dateTimeFormat.format(recordDate);
 					records.append(recordDateString).append(",")
 							.append(recordDateString).append(",");
 					//Module specific fields
 					if (leads) {
 						//First Name
 						records.append(firstname).append(",");
 						//Email
 						records.append(email).append(",");
 						//Phone
 						records.append(phone).append(",");
 						//Company
 						records.append(company).append(",");
 						//Lead source
 						records.append(random(leadSource)).append(",");
 						//Industry
 						records.append(random(leadIndustry)).append(",");
 					} else if (potentials) {
 						//Amount
 						int amount = (int) random(minAmount, maxAmount);
 						amount = amount - (amount % amountMultiplier);
 						records.append(amount).append(",");
 						//Set closing date ~15 days from created date
 						Calendar closeStart = (Calendar) recordStart.clone();
 						Calendar closeEnd = (Calendar) recordEnd.clone();
 						closeStart.add(Calendar.DATE, 15);
 						closeEnd.add(Calendar.DATE, 30);
 						Date closingDate = new Date(random(
 								closeStart.getTimeInMillis(),
 								closeEnd.getTimeInMillis()));
 						String closingDateString = dateFormat
 								.format(closingDate);
 						records.append(closingDateString).append(",");
 						//Account Id
 						records.append(accountId).append(",");
 						//Type
 						records.append(random(potentialType)).append(",");
 						//Lead Source
 						records.append(random(leadSource)).append(",");
 						//Stage
 						//Set Stage as Qualification by default
 						String stage = potentialStage[0];
 						boolean closedWon = false;
 						boolean closedLost = false;
 						if (i < recordCount * 0.25) {
 							//Set stage as closed won for the first 25%
 							stage = potentialStage[potentialStage.length - 3];
 							closedWon = true;
 						} else if (i < recordCount * 0.5) {
 							//Set stage as closed lost or closed lost to competition for the next 25%
 							stage = potentialStage[(int) random(
 									potentialStage.length - 2,
 									potentialStage.length - 1)];
 							closedLost = true;
 						}
 						records.append(stage).append(",");
 						//Create stage history for this potential
 						recordStart.setTime(recordDate);
 						for (int j = 0; j < potentialStage.length; j++) {
 							//Stage id
 							String stageId = "Stage_" + dataYear + "_"
 									+ recordSuffix + "_" + j;
 							stages.append(stageId).append(",");
 							//Potential id
 							stages.append(recordId).append(",");
 							//Amount
 							stages.append(amount).append(",");
 							//Stage
 							stages.append(potentialStage[j]).append(",");
 							//Probability
 							stages.append(potentialStageProbability[j]).append(
 									",");
 							//Expected Revenue
 							stages.append(
 									amount * potentialStageProbability[j] / 100)
 									.append(",");
 							//Close Date
 							stages.append(closingDateString).append(",");
 							//Last Modified Id
 							stages.append(owner).append(",");
 							//Last Modified Time
 							stages.append(
 									dateTimeFormat.format(recordStart.getTime()))
 									.append("\n");
 							//Set Modified on to a date between modifiedOn and end of month for next stage
 							recordStart.setTimeInMillis(random(
 									recordStart.getTimeInMillis(),
 									closeEnd.getTimeInMillis()));
 							if (closedWon) {
 								//Create history upto closed won
 								if (j == potentialStage.length - 3) {
 									break;
 								}
 							} else if (closedLost) {
 								if (j > potentialStage.length - 3) {
 									break;
 								}
 								//Change to Closed Lost at some random step or at the last open stage
 								if (flip(potentialStage.length - j - 4)
 										|| j == potentialStage.length - 4) {
 									//Skip to Closed Lost and break
 									if (stage.equals("Closed Lost")) {
 										j = potentialStage.length - 3;
 									} else {
 										j = potentialStage.length - 2;
 									}
 								}
 							} else {
 								//Add only Qualification stage for other records
 								break;
 							}
 						}

 					} else if (calls) {
 						//Call Type
 						records.append(random(callType)).append(",");
 						//Purpose
 						records.append(random(callPurpose)).append(",");
 						//Subject
 						records.append("Call with " + name).append(",");
 						//Call Start Time
 						records.append(recordDateString).append(",");
 						//Call Duration
 						records.append(random(1, 10) + ":" + random(1, 59));
 					} else if (events) {
 						//Title
 						records.append(
 								random(eventTitlePrefix) + " meeting with "
 										+ name).append(",");
 						//From
 						records.append(recordDateString).append(",");
 						//To
 						Calendar to = Calendar.getInstance();
 						to.setTime(recordDate);
 						to.add(Calendar.HOUR, 1);
 						records.append(dateTimeFormat.format(to.getTime()))
 								.append(",");
 					}
 					//Remove the last comma and append new line
 					records.deleteCharAt(records.length() - 1).append("\n");
 				}
 				write(module, dataYear, records.toString());
 				if (potentials) {
 					write("Stages", dataYear, stages.toString());
 				}
 			}
 			//Increment to next day
 			start.add(Calendar.DATE, 1);
 			end.add(Calendar.DATE, 1);
 		}

 	}

 	static Random random = new Random();

 	//Return a random value between min and max, including both
 	public static long random(long min, long max) {
 		return (Math.abs(random.nextLong()) % (max + 1 - min)) + min;
 	}

 	//Return a random value from the array
 	public static String random(String[] array) {
 		return array[(int) random(0, array.length - 1)];
 	}

 	//Return a random value from the list
 	public static String random(List<String> list) {
 		return list.get((int) random(0, list.size() - 1));
 	}

 	//Returns true or false randomly
 	public static boolean flip() {
 		return flip(2);
 	}

 	//Returns true or false randomly
 	public static boolean flip(int num) {
 		return random.nextLong() % num == 0;
 	}

 	public static void clean(int year) {
 		File dir = new File(String.valueOf(year));
 		if (dir.exists()) {
 			deleteDir(dir);
 		}
 		dir.mkdir();
 	}

 	public static void write(String module, int year, String contents)
 			throws IOException {
 		File dir = new File(String.valueOf(year));
 		File file = new File(dir, module + ".csv");
 		FileWriter writer;
 		writer = new FileWriter(file, true);
 		writer.write(contents);
 		writer.close();
 	}

 	private static void deleteDir(File dir) {
 		File[] files = dir.listFiles();
 		for (File file : files) {
 			if (file.isDirectory()) {
 				deleteDir(file);
 			} else {
 				file.delete();
 			}
 		}
 	}
 }*/
