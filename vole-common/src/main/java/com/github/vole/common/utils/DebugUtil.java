package com.github.vole.common.utils;

import static java.lang.System.out;

import java.math.BigDecimal;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;

/**
 * @description 用于debug提供输出工具以及执行时间统计
 */
@SuppressWarnings({ "rawtypes", "unchecked" })
public class DebugUtil {
	/**
	 * 用于计时存储map
	 */
	private static HashMap timeMap = new HashMap();
	private static ThreadLocal orderTime = new ThreadLocal();
	static {
		orderTime.remove();
	}

	/**
	 * @todo 打印集合数据
	 * @param obj
	 * @param appendStr
	 * @param newLine
	 */
	public static void printAry(Object obj, PrintHandler printHandler, String appendStr, boolean newLine) {
		if (obj == null) {
			if (newLine)
				out.println(obj);
			else
				out.print(obj + appendStr);
		} else {
			Object rowData;
			if (obj.getClass().isArray()) {
				Object[] tmp = CollectionUtil.convertArray(obj);
				for (int i = 0; i < tmp.length; i++) {
					rowData = tmp[i];
					if (rowData instanceof Collection || rowData instanceof Object[]) {
						out.println();
						printAry(rowData, printHandler, appendStr, false);
					} else {
						if (newLine)
							out.println(printHandler == null ? rowData : printHandler.process(rowData));
						else
							out.print((printHandler == null ? rowData : printHandler.process(rowData))
									+ ((i == tmp.length - 1) ? "" : appendStr));
					}
				}
			} else if (obj instanceof Collection) {
				Collection tmp = (Collection) obj;
				int index = 0;
				int size = tmp.size();
				for (Iterator iter = tmp.iterator(); iter.hasNext();) {
					index++;
					rowData = iter.next();
					if (rowData instanceof Collection || rowData instanceof Object[]) {
						out.println();
						printAry(rowData, printHandler, appendStr, false);
					} else {
						if (newLine)
							out.println(printHandler == null ? rowData : printHandler.process(rowData));
						else
							out.print((printHandler == null ? rowData : printHandler.process(rowData))
									+ (index == size ? "" : appendStr));
					}
				}
			} else if (obj instanceof Set) {
				Set tmp = (Set) obj;
				int index = 0;
				int size = tmp.size();
				for (Iterator iter = tmp.iterator(); iter.hasNext();) {
					index++;
					rowData = iter.next();
					if (rowData instanceof Collection || rowData instanceof Object[]) {
						out.println();
						printAry(rowData, printHandler, appendStr, false);
					} else {
						if (newLine) {
							out.println(printHandler == null ? rowData : printHandler.process(rowData));
						} else
							out.print((printHandler == null ? rowData : printHandler.process(rowData))
									+ (index == size ? "" : appendStr));
					}
				}
			} else
				out.println(obj);
		}
	}

	/**
	 * @todo 开始计时
	 * @param transactionId
	 */
	public static void beginTime(String transactionId) {
		long nowTime = System.nanoTime();
		if (transactionId == null) {
			orderTime.set(new Long(nowTime));
		} else
			timeMap.put(transactionId, nowTime);
	}

	/**
	 * @todo 截止计时并输出开始到截止之间的时长
	 * @param transactionId
	 */
	public static void endTime(String transactionId) {
		long endTime = System.nanoTime();
		long totalTime = 0;
		if (transactionId == null) {
			totalTime = endTime - (Long) orderTime.get();
			orderTime.remove();
		} else {
			Long preTime = (Long) timeMap.get(transactionId);
			if (preTime == null)
				preTime = new Long(System.nanoTime());
			totalTime = endTime - preTime;
			timeMap.remove(transactionId);
		}

		out.println("事务:".concat((transactionId == null ? "" : transactionId)).concat("花费时间为:")
				.concat(new BigDecimal(totalTime * 1.000 / 1000000000).toString()).concat("秒"));

	}
}
